package com.library.service;

import com.library.dao.EventDao;
import com.library.dao.impl.EventDaoImpl;
import com.library.dto.EventRegistrationDto;
import com.library.mapper.EventRegistrationMapper;
import com.library.model.EventRegistration;
import com.library.model.LibraryEvent;

import java.util.ArrayList;
import java.util.List;

public class EventService {
    EventDao eventDao = new EventDaoImpl();
    EventRegistrationMapper eventRegistrationMapper = new EventRegistrationMapper();

    public List<EventRegistration> findRegistrationsForEvent(long eventId) {
        LibraryEvent event = eventDao.getEventById(eventId);
        if(event == null) {
            throw new RuntimeException("No event with event ID=" + eventId);
        }
        else {
            return eventDao.findRegistrationsForEvent(event);
        }
    }

    public List<EventRegistrationDto> findRegistrationsForEventWithDto(long eventId) {
        LibraryEvent event = eventDao.getEventById(eventId);
        if(event == null) {
            throw new RuntimeException("No event with event ID=" + eventId);
        }
        else {
            List<EventRegistration> eventRegistrations = eventDao.findRegistrationsForEvent(event);
            List<EventRegistrationDto> eventRegistrationDtos = new ArrayList<>();
            eventRegistrations.forEach(
                    eventRegistration -> {
                        EventRegistrationDto dto = eventRegistrationMapper.mapEntityToDto(eventRegistration);
                        eventRegistrationDtos.add(dto);
                    }
            );

            return eventRegistrationDtos;
        }
    }
}
