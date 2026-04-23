package com.events.api.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.events.api.domain.Coupon;
import com.events.api.domain.Event;
import com.events.api.domain.dto.coupon.CouponRequestDTO;
import com.events.api.repositories.CouponRepository;
import com.events.api.repositories.EventRepository;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    EventRepository eventRepository;

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
