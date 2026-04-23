package com.events.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.events.api.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

}
