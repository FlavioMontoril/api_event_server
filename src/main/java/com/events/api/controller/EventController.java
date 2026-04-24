package com.events.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.events.api.domain.dto.event.EventRequestDTO;
import com.events.api.domain.dto.event.EventResponseDTO;
import com.events.api.service.EventService;
import com.events.api.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "Endpoints para criação e listagem de eventos")
public class EventController {

    private final EventService eventService;
    private final FileService fileService;

    @Operation(summary = "Criar novo evento", description = "Cria um evento e salva a imagem no disco")
    @ApiResponse(responseCode = "201", description = "Evento criado com sucesso")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDTO> create(@ModelAttribute @Valid EventRequestDTO body) {
        EventResponseDTO newEvent = this.eventService.createEvent(body);
        return ResponseEntity.status(201).body(newEvent);
    }

    @Operation(summary = "Listar todos os eventos", description = "Retorna uma lista paginada de eventos")
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<EventResponseDTO> allEvents = this.eventService.allEventsPagination(page, size);
        return ResponseEntity.ok(allEvents);
    }

    @GetMapping("/held")
    public ResponseEntity<List<EventResponseDTO>> getAllEventsHeld(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<EventResponseDTO> allEvents = this.eventService.eventsHeldPagination(page, size);
        return ResponseEntity.ok(allEvents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> findByIdEvent(@PathVariable UUID id) {
        EventResponseDTO event = this.eventService.findById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        // O Service agora retorna o arquivo e o tipo já validados
        FileService.FileResponse file = fileService.getFile(fileName);

        return ResponseEntity.ok()
                // Define o cabeçalho 'Content-Type' para o navegador saber como processar os
                // bytes
                .contentType(MediaType.parseMediaType(file.contentType()))
                // 'inline' indica que o navegador deve tentar exibir o arquivo na própria aba
                // 'filename' sugere um nome caso o usuário decida salvar manualmente
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.resource().getFilename() + "\"")
                .body(file.resource());

    }

}
