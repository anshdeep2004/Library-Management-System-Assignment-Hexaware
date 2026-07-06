package com.library.controller;

import com.library.enums.BookStatus;
import com.library.enums.Genre;
import com.library.model.Book;
import com.library.service.BookService;

import java.util.List;

public class BookController {
    BookService bookService = new BookService();
    public Book insertBook(String title, String genre, long authorId) {
        return bookService.isertBook(title, genre, authorId);
    }

    public Book getBookById(int bookId) {
        return bookService.getBookById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public Book updateBook(Book book, String title, String genre, int authorId, String status, int memberId, int year) {
        return bookService.updateBook(book, title, genre, authorId, status, memberId, year);
    }

    public void deleteBookById(int bookId) {
        bookService.deleteBookById(bookId);
    }

    public List<Book> findBooksByAuthor(int authorId) {
        return bookService.findBooksByAuthor(authorId);
    }

    public List<Book> findBooksBorrowedByMember(int memberId) {
        return bookService.findBooksBorrowedByMember(memberId);
    }

    public List<Book> filterBooks(Genre genreCheck, BookStatus bookStatus) {
        return bookService.filterBooks(genreCheck, bookStatus);
    }
}
