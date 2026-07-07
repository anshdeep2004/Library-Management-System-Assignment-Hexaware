package com.library.dao;

import com.library.model.EventRegistration;
import com.library.model.LibraryEvent;

import java.util.List;

public interface EventDao {
    List<EventRegistration> findRegistrationsForEvent(LibraryEvent event);

    LibraryEvent getEventById(long eventId);
}
