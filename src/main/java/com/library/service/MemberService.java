package com.library.service;

import com.library.LibraryManagementApp;
import com.library.dao.EventDao;
import com.library.dao.MemberDao;
import com.library.dao.impl.EventDaoImpl;
import com.library.dao.impl.MemberDaoImpl;
import com.library.model.LibraryEvent;
import com.library.model.Member;

import java.util.List;

public class MemberService {
    EventDao eventDao = new EventDaoImpl();
    MemberDao memberDao = new MemberDaoImpl();

    public List<Member> findNoShowsForEvent(long eventId) {
        LibraryEvent event = eventDao.getEventById(eventId);
        if(event == null) {
            throw new RuntimeException("Event with ID=" + eventId + " does not exist");
        }
        else {
            return memberDao.findNoShowsForEvent(event);
        }
    }
}
