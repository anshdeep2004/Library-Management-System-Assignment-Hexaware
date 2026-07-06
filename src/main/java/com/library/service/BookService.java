package com.library.service;

import com.library.dao.AuthorDao;
import com.library.dao.BookDao;
import com.library.dao.MemberDao;
import com.library.dao.impl.AuthorDaoImpl;
import com.library.dao.impl.BookDaoImpl;
import com.library.dao.impl.MemberDaoImpl;
import com.library.enums.BookStatus;
import com.library.enums.Genre;
import com.library.enums.MembershipType;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Member;

import java.time.Year;
import java.util.List;

public class BookService {
    BookDao bookDao = new BookDaoImpl();
    AuthorDao authorDao = new AuthorDaoImpl();
    MemberDao memberDao = new MemberDaoImpl();

    public Book isertBook(String title, String genre, long authorId) {
        Book book = new Book();
        book.setTitle(title);
        book.setGenre(Genre.valueOf(genre.toUpperCase()));
        book.setStatus(BookStatus.AVAILABLE);
        book.setBorrowedBy(null);
        book.setPublishedYear(Year.now().getValue());
        Author author = authorDao.getAuthoById(authorId);
        if(author != null) {
            book.setAuthor(author);
        }
        else throw new RuntimeException("Author with author ID = " + authorId + " does not exist.");

        return bookDao.insertBook(book);
    }

    public Book getBookById(int bookId) {
        return bookDao.getBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public Book updateBook(Book book, String title, String genre, int authorId, String status, int memberId, int year) {
        book.setTitle(title);
        book.setGenre(Genre.valueOf(genre.toUpperCase()));
        book.setStatus(BookStatus.valueOf(status.toUpperCase()));
        book.setPublishedYear(year);
        Author author = authorDao.getAuthoById(authorId);
        if(author != null) {
            book.setAuthor(author);
        }
        else throw new RuntimeException("Author with author ID = " + authorId + " does not exist.");

        Member member = memberDao.getMemberById(memberId);
        if(member != null) {
            book.setBorrowedBy(member);
        }
        else throw new RuntimeException("Member with ID = " + memberId + " does not exist.");

        return bookDao.updateBook(book);
    }

    public void deleteBookById(int bookId) {
        bookDao.deleteBookById(bookId);
    }

    public List<Book> findBooksByAuthor(int authorId) {
        return bookDao.findBooksByAuthor(authorId);
    }

    public List<Book> findBooksBorrowedByMember(int memberId) {
        return bookDao.findBooksBorrowedByMember(memberId);
    }

    public List<Book> filterBooks(Genre genreCheck, BookStatus bookStatus) {
        return bookDao.filterBooks(genreCheck, bookStatus);
    }
}
