package com.library.dao.impl;

import com.library.config.LibraryConfig;
import com.library.dao.MemberDao;
import com.library.enums.AttendanceStatus;
import com.library.model.Author;
import com.library.model.EventRegistration;
import com.library.model.LibraryEvent;
import com.library.model.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MemberDaoImpl implements MemberDao {
    private final SessionFactory sessionFactory;

    public MemberDaoImpl() {
        sessionFactory = LibraryConfig.getSessionFactory();
    }

    @Override
    public Member getMemberById(int memberId) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Member member = session.find(Member.class, memberId);
            transaction.commit();

            return member;
        }
        catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Member> findNoShowsForEvent(LibraryEvent event) {
        String jpql = """
                    select er.member
                    from EventRegistration er
                    where er.event.id = ?1
                    and er.attendanceStatus = ?2
                """;
        try(Session session = LibraryConfig.getSessionFactory().openSession()) {
            Query<Member> query = session.createQuery(jpql, Member.class);
            query.setParameter(1, event.getId());
            query.setParameter(2, AttendanceStatus.NO_SHOW);
            List<Member> noShowList = query.list();
            return noShowList;
        }
    }
}
