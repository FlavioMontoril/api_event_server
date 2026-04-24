package com.events.api.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.api.domain.Coupon;
import com.events.api.domain.dto.coupon.CouponRequestDTO;
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
        Coupon coupon = couponService.execute(id, data);
        return ResponseEntity.status(201).body(coupon);
    }

}
