package com.events.api.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.events.api.domain.Coupon;
import com.events.api.domain.Event;
import com.events.api.domain.dto.coupon.CouponRequestDTO;
import com.events.api.repositories.CouponRepository;
import com.events.api.repositories.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;

    public Coupon execute(UUID id, CouponRequestDTO data) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(data.code());
        coupon.setDiscount(data.discount());
        coupon.setValid(data.valid());
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }

}
