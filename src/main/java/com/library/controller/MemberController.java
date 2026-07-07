package com.library.controller;

import com.library.model.Member;
import com.library.service.MemberService;

import java.util.List;

public class MemberController {
    MemberService memberService = new MemberService();

    public List<Member> findNoShowsForEvent(long eventId) {
        return memberService.findNoShowsForEvent(eventId);
    }
}
