package com.library.dao.impl;

import com.library.config.LibraryConfig;
import com.library.dao.MemberDao;
import com.library.model.Author;
import com.library.model.Member;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
}
