package com.library.dao.impl;

import com.library.config.LibraryConfig;
import com.library.dao.AuthorDao;
import com.library.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.dialect.function.array.ArrayViaArgumentReturnTypeResolver;

public class AuthorDaoImpl implements AuthorDao {
    private final SessionFactory sessionFactory;

    public AuthorDaoImpl() {
        sessionFactory = LibraryConfig.getSessionFactory();
    }
    @Override
    public Author getAuthoById(long authorId) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Author author = session.find(Author.class, authorId);
            transaction.commit();

            return author;
        }
        catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }
}
