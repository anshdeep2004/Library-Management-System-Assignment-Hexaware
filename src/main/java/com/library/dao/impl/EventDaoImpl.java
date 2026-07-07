package com.library.dao.impl;

import com.library.config.LibraryConfig;
import com.library.dao.EventDao;
import com.library.model.EventRegistration;
import com.library.model.LibraryEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.w3c.dom.events.Event;

import java.util.List;

public class EventDaoImpl implements EventDao {

    @Override
    public LibraryEvent getEventById(long eventId) {
        try(Session session = LibraryConfig.getSessionFactory().openSession()) {
            LibraryEvent event = session.find(LibraryEvent.class, eventId);
            return event;
        }
    }

    @Override
    public List<EventRegistration> findRegistrationsForEvent(LibraryEvent event) {
        String jpql = """
                    Select er
                    from EventRegistration er
                    where er.event.id = ?1
                """;
        String hql = """
                    from EventRegistration er
                    where er.event.id = eventId
                """;
        try(Session session = LibraryConfig.getSessionFactory().openSession()) {
            Query<EventRegistration> query = session.createQuery(jpql, EventRegistration.class);
            query.setParameter(1, event.getId());
            List<EventRegistration> regList = query.list();
            return regList;
        }
    }
}
