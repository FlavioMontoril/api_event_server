package com.events.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.events.api.domain.Event;
import com.events.api.domain.dto.event.EventRequestDTO;
import com.events.api.domain.dto.event.EventResponseDTO;
import com.events.api.exceptions.ResourceNotFoundException;
import com.events.api.repositories.EventRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AddressService addressService;
    private final UploadService uploadService;

    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO data) {
        String imgUrl = null;
        if (data.image() != null && !data.image().isEmpty()) {
            imgUrl = this.uploadService.uploadImg(data.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(data.title());
        newEvent.setDescription(data.description());
        newEvent.setEventUrl(data.eventUrl());
        newEvent.setDate(data.date());
        newEvent.setRemote(data.remote());
        newEvent.setImgUrl(imgUrl);

        eventRepository.save(newEvent);

        if (!data.remote()) {
            this.addressService.createAddress(data, newEvent);
        }

        // Retornamos o DTO em vez da Entidade
        return new EventResponseDTO(
                newEvent.getId(),
                newEvent.getTitle(),
                newEvent.getDescription(),
                newEvent.getDate(),
                newEvent.getRemote(),
                newEvent.getEventUrl(),
                newEvent.getImgUrl());

    }

    public List<EventResponseDTO> allEventsPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findAll(pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl())).stream().toList();
    }

    public List<EventResponseDTO> eventsHeldPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findAllHeld(LocalDateTime.now(), pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl())).stream().toList();
    }

    public EventResponseDTO findById(UUID id) {
        Event event = this.eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));
        return new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getRemote(),
                event.getEventUrl(),
                event.getImgUrl());
    }

}
