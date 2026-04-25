package com.events.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.events.api.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    // Query 2: Busca customizada para trazer o evento junto em um único JOIN
    @Query("SELECT c FROM Coupon c JOIN FETCH c.event WHERE c.id = :id")
    Optional<Coupon> findByIdWithEvent(@Param("id") UUID id);

}
