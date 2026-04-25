package com.events.api.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.api.domain.Coupon;
import com.events.api.domain.dto.coupon.CouponRequestDTO;
import com.events.api.domain.dto.coupon.CouponResponseDTO;
import com.events.api.domain.dto.coupon.CouponWithEventResponseDTO;
import com.events.api.service.CouponService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/event/{id}")
    public ResponseEntity<Coupon> create(@PathVariable UUID id, @RequestBody @Valid CouponRequestDTO data) {
        Coupon coupon = couponService.createCoupon(id, data);
        return ResponseEntity.status(201).body(coupon);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable UUID id) {
        CouponResponseDTO coupon = this.couponService.findByIdCoupon(id);
        return ResponseEntity.ok().body(coupon);
    }

    @GetMapping("/{id}/details-event")
    public ResponseEntity<CouponWithEventResponseDTO> getCouponWithEvent(@PathVariable UUID id) {
        return ResponseEntity.ok(couponService.findByIdWithEvent(id));
    }
}
