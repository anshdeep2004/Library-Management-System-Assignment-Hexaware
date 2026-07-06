package com.library.dao;

import com.library.enums.BookStatus;
import com.library.enums.Genre;
import com.library.model.Book;

import java.util.List;

public interface BookDao {
    Book insertBook(Book book);

    Book getBookById(int bookId);

    List<Book> getAllBooks();

    Book updateBook(Book book);

    void deleteBookById(int bookId);

    List<Book> findBooksByAuthor(int authorId);

    List<Book> findBooksBorrowedByMember(int memberId);

    List<Book> filterBooks(Genre genreCheck, BookStatus bookStatus);
}
