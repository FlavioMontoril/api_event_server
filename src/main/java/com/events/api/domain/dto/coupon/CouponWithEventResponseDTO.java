package com.events.api.domain.dto.coupon;

import java.time.LocalDateTime;
import java.util.UUID;

import com.events.api.domain.dto.event.EventResponseDTO;

public record CouponWithEventResponseDTO(
        UUID id,
        String code,
        Integer discount,
        LocalDateTime valid,
        EventResponseDTO event) {
}
