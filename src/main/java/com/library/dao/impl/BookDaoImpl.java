package com.library.dao.impl;

import com.library.config.LibraryConfig;
import com.library.dao.BookDao;
import com.library.enums.BookStatus;
import com.library.enums.Genre;
import com.library.model.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private final SessionFactory sessionFactory;

    public BookDaoImpl() {
        sessionFactory = LibraryConfig.getSessionFactory();
    }

    @Override
    public Book insertBook(Book book) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();

            return book;
        }
        catch (Exception e) {
            if(transaction != null) {
                // this will happen if the transaction has begin but the insertion was not succeeded, so in this case we have to rollback.
                transaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Book getBookById(int bookId) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Book book = session.find(Book.class, bookId);
            transaction.commit();

            return book;
        }
        catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Book> getAllBooks() {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery("from Book", Book.class);
            List<Book> bookList = query.list();
            transaction.commit();

            return bookList;
        }
        catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Book updateBook(Book book) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(book);
            transaction.commit();

            return book;
        }
    }

    @Override
    public void deleteBookById(int bookId) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Book book = session.find(Book.class, bookId);
            if(book != null) {
                session.remove(book);
                transaction.commit();
            }
            else {
                transaction.rollback();
                throw new RuntimeException("No book present with ID = " + bookId);
            }
        }
    }

    @Override
    public List<Book> findBooksByAuthor(int authorId) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery("from Book b where b.author.id =: authorId", Book.class);
            query.setParameter("authorId", authorId);
            List<Book> authorBookList = query.list();
            transaction.commit();
            return authorBookList;
        }
    }

    @Override
    public List<Book> findBooksBorrowedByMember(int memberId) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Book> query = session.createQuery("from Book b where b.borrowedBy.id =: memberId", Book.class);
            query.setParameter("memberId", memberId);
            List<Book> memberBookList = query.list();
            transaction.commit();
            return memberBookList;
        }
    }

    @Override
    public List<Book> filterBooks(Genre genreCheck, BookStatus bookStatus) {
        try(Session session = sessionFactory.openSession()) {
            // create a criteriaBuilder : for conditions(predicates) and create query.
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Create the query
            CriteriaQuery<Book> cq = cb.createQuery(Book.class);

            // create a 'from' statement for query
            Root<Book> root = cq.from(Book.class);

            // the conditions in where clause -- predicates
            List<Predicate> predicates = new ArrayList<>();

            if(genreCheck != null) {
                predicates.add(cb.equal(root.get("genre"), genreCheck));
            }

            if(bookStatus != null) {
                predicates.add(cb.equal(root.get("status"), bookStatus));
            }

            // where statement needs a var args(...) which means it needs an array not a list
            cq.where(predicates.toArray(Predicate[]::new));

            Query<Book> query = session.createQuery(cq);

            return query.list();
        }
    }

}
