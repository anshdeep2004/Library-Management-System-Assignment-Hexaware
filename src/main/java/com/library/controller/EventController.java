package com.library.controller;

import com.library.dto.EventRegistrationDto;
import com.library.model.EventRegistration;
import com.library.service.EventService;

import java.util.List;

public class EventController {
    EventService eventService = new EventService();
    public List<EventRegistration> findRegistrationsForEvent(long eventId) {
        return eventService.findRegistrationsForEvent(eventId);
    }

    public List<EventRegistrationDto> findRegistrationsForEventWithDto(long eventId) {
        return eventService.findRegistrationsForEventWithDto(eventId);
    }
}
