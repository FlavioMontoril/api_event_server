package com.events.api.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.events.api.domain.Coupon;
import com.events.api.domain.Event;
import com.events.api.domain.dto.coupon.CouponRequestDTO;
import com.events.api.domain.dto.coupon.CouponResponseDTO;
import com.events.api.domain.dto.coupon.CouponWithEventResponseDTO;
import com.events.api.domain.dto.event.EventResponseDTO;
import com.events.api.repositories.CouponRepository;
import com.events.api.repositories.EventRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;

    public Coupon createCoupon(UUID id, CouponRequestDTO data) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Coupon coupon = new Coupon();
        coupon.setCode(data.code());
        coupon.setDiscount(data.discount());
        coupon.setValid(data.valid());
        coupon.setEvent(event);

        return couponRepository.save(coupon);
    }

    public CouponResponseDTO findByIdCoupon(UUID id) {
        Coupon coupon = this.couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));

        return new CouponResponseDTO(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscount(),
                coupon.getValid());
    }

    @Transactional(readOnly = true) // MÉTODO 2: Cupom com Evento @ManyToOne(fetch = FetchType.LAZY)
    public CouponWithEventResponseDTO findByIdWithEvent(UUID id) {
        Coupon coupon = couponRepository.findByIdWithEvent(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found"));

        // 2. Você NÃO pode passar coupon.getEvent() direto se o DTO espera um
        // EventResponseDTO.
        // Você precisa criar o EventResponseDTO manualmente:
        Event eventEntity = coupon.getEvent();

        EventResponseDTO eventDTO = new EventResponseDTO(
                eventEntity.getId(),
                eventEntity.getTitle(),
                eventEntity.getDescription(),
                eventEntity.getDate(),
                eventEntity.getRemote(),
                eventEntity.getEventUrl(),
                eventEntity.getImgUrl());

        return new CouponWithEventResponseDTO(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDiscount(),
                coupon.getValid(),
                eventDTO); // O Hibernate busca o evento aqui
    }

}
