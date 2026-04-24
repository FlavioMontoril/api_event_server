package com.events.api.service;

import org.springframework.stereotype.Service;

import com.events.api.domain.Address;
import com.events.api.domain.Event;
import com.events.api.domain.dto.event.EventRequestDTO;
import com.events.api.repositories.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address createAddress(EventRequestDTO data, Event event) {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.uf());
        address.setEvent(event);

        return addressRepository.save(address);
    }

}
