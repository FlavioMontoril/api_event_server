package com.events.api.domain.dto.coupon;

import java.time.LocalDateTime;

public record CouponRequestDTO(String code, Integer discount, LocalDateTime valid) {

}
