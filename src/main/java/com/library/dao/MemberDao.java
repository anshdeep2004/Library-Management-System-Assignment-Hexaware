package com.library.dao;

import com.library.model.LibraryEvent;
import com.library.model.Member;

import java.util.List;

public interface MemberDao {
    Member getMemberById(int memberId);

    List<Member> findNoShowsForEvent(LibraryEvent event);
}
