package com.events.api.domain.dto.event;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EventRequestDTO(
        @NotBlank(message = "O título é obrigatório") 
        String title,
        @NotBlank(message = "A descrição é obrigatório") 
        String description,
        @NotNull(message = "A data do evento é obrigatória") 
        LocalDateTime date,
        @NotBlank(message = "A cidade é obrigatória") 
        String city,
        @NotBlank(message = "A UF é obrigatória")
        @Size(min = 2, max = 2, message = "A UF deve ter exatamente 2 caracteres")
        String uf,
        @NotNull 
        Boolean remote,
        @NotBlank(message = "A URL do evento é obrigatória") 
        String eventUrl,
        MultipartFile image

) {

}
