package com.events.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.events.api.domain.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}
