package com.library.mapper;

import com.library.dto.EventRegistrationDto;
import com.library.model.EventRegistration;

import java.time.LocalDate;

public class EventRegistrationMapper {
    public EventRegistrationDto mapEntityToDto(EventRegistration eventRegistration) {
        // I have an Entity given to me, and i need to convert it to dto record

        // Create an Object of Dto and pass the fields to its constructor

        EventRegistrationDto eventRegistrationDto = new EventRegistrationDto(
                eventRegistration.getEvent().getId(),
                eventRegistration.getEvent().getTitle(),
                LocalDate.parse(eventRegistration.getEvent().getEventDate().toString().split("T")[0]),
                eventRegistration.getMember().getId(),
                eventRegistration.getMember().getName(),
                eventRegistration.getMember().getEmail()
        );

        return eventRegistrationDto;
    }
}
