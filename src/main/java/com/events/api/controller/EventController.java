package com.events.api.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.events.api.domain.Event;
import com.events.api.domain.dto.event.EventRequestDTO;
import com.events.api.domain.dto.event.EventResponseDTO;
import com.events.api.service.EventService;
import com.events.api.service.FileService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Event> create(@ModelAttribute EventRequestDTO body) {
        Event newEvent = this.eventService.creaEvent(body);
        return ResponseEntity.status(201).body(newEvent);
    }

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

    @GetMapping("/image/**")
    public ResponseEntity<Resource> viewFile(HttpServletRequest request) {
        try {
            // Pega a parte da URL após "/image/"
            String fullPath = request.getRequestURI().split("/api/event/image/")[1];
            // Decodifica a URL (Ex: transforma %20 de volta em espaço " ")
            String fileName = java.net.URLDecoder.decode(fullPath, java.nio.charset.StandardCharsets.UTF_8);

            // Log para você confirmar no console se o nome chegou certo:
            System.out.println("Buscando arquivo: " + fileName);

            // 1. Chama o Service para localizar o arquivo físico e validar a segurança
            // (Path Traversal)
            Resource file = fileService.getFile(fileName);
            // 2. Tenta descobrir o tipo do arquivo (MIME type) baseado na extensão (ex:
            // image/png, application/pdf)
            // Isso é o que diz ao navegador: "Isto é uma imagem" ou "Isto é um documento"
            String contentType = Files.probeContentType(Paths.get(file.getURI()));

            // 3. Caso o sistema não consiga identificar o tipo, define um tipo genérico
            // (binário)
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    // 4. Define o cabeçalho 'Content-Type' para o navegador saber como processar os
                    // bytes
                    .contentType(MediaType.parseMediaType(contentType))
                    // 5. 'inline' indica que o navegador deve tentar exibir o arquivo na própria
                    // aba
                    // 'filename' sugere um nome caso o usuário decida salvar manualmente
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                    // 6. Envia o corpo da resposta com o fluxo de bytes do arquivo
                    .body(file);

        } catch (Exception e) {
            // 7. Caso o arquivo não exista ou ocorra erro de acesso, retorna o status HTTP
            // 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

}
