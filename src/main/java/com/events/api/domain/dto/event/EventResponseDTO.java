package com.events.api.domain.dto.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponseDTO(UUID id, String title, String description, LocalDateTime date,
        Boolean remote, String eventUrl, String imgUrl) {

}
