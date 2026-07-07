package com.library;

import com.library.config.LibraryConfig;
import com.library.controller.BookController;
import com.library.controller.EventController;
import com.library.controller.MemberController;
import com.library.dto.EventRegistrationDto;
import com.library.enums.BookStatus;
import com.library.enums.Genre;
import com.library.model.Book;
import com.library.model.EventRegistration;
import com.library.model.Member;
import com.library.service.BookService;
import jakarta.persistence.GeneratedValue;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementApp {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        BookController bookController = new BookController();
        EventController eventController = new EventController();
        MemberController memberController = new MemberController();

        while(true) {
            System.out.println("---------Welcome to the Library----------");
            System.out.println("1. Insert a book.");
            System.out.println("2. Get a book with given ID.");
            System.out.println("3. Get all books.");
            System.out.println("4. Update book.");
            System.out.println("5. Delete book with given ID.");
            System.out.println("6. Get books of an author.");
            System.out.println("7. Get books borrowed by a member.");
            System.out.println("8. Get books by Genre / BookStatus.");
            System.out.println("9. Get all registrations for an event ID.(Without DTO)");
            System.out.println("10. Get all members who registered on an event but did not come.");
            System.out.println("11. Get all registrations for an event ID.(With DTO)");
            System.out.println("0. To Exit.");
            System.out.println("-----------------------------------------");
            System.out.print("Enter your choice: ");
            int input = sc.nextInt();
            sc.nextLine();
            if(input == 0) {
                System.out.println("Exiting...");
                break; // break the while loop
            }
            switch (input) {
                /*
                Task1 : Basic CRUD
                For each of the 3 entities, implement:
                - save(entity) — insert
                - findById(id) — read single
                - findAll() — read all
                - update(entity) — update
                - delete(id) — delete
                 */
                case 1 -> {
                    System.out.println("----------Insert Book----------");
                    try {
                        Book book = new Book();
                        System.out.print("Enter the book title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter the book genre: ");
                        String genre = sc.nextLine();
                        System.out.print("Enter id of author of the book: ");
                        long authorId = sc.nextLong();
                        book  = bookController.insertBook(title, genre, authorId);
                        System.out.println("Book inserted successfully..");
                        System.out.println("Inserted Book: ");
                        System.out.println(book);
                    }
                    catch (RuntimeException e) {
                        System.out.println("Insert operation failed: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("--------Get Book By ID---------");
                    System.out.print("Enter the book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    Book book = bookController.getBookById(bookId);
                    if(book != null) {
                        System.out.println("Book with ID = " + bookId);
                        System.out.println(book);
                    }
                    else {
                        System.out.println("Book not found with ID = "+ bookId);
                    }
                }
                case 3 -> {
                    System.out.println("--------Get All Books--------");
                    List<Book> books = bookController.getAllBooks();
                    books.forEach(System.out :: println);
                }
                case 4 -> {
                    System.out.println("---------Update Book by ID---------");
                    System.out.print("Enter book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    Book book = bookController.getBookById(bookId);
                    System.out.println("Current Book Info: ");
                    System.out.println(book);

                    System.out.print("Enter updated title: ");
                    String title = sc.nextLine();

                    System.out.println("Enter updated genre: ");
                    Arrays.stream(Genre.values()).forEach(System.out :: println);
                    String genre = sc.nextLine();

                    System.out.print("Enter updated author ID: ");
                    int authorId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter updated status: ");
                    Arrays.stream(BookStatus.values()).forEach(System.out :: println);
                    String status = sc.nextLine();

                    System.out.print("Enter updated borrow member ID: ");
                    int memberId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter updated Publish Year: ");
                    int year = sc.nextInt();
                    sc.nextLine();

                    Book updatedBook = bookController.updateBook(book, title, genre, authorId, status, memberId, year);
                    System.out.println("Updated Book Info: ");
                    System.out.println(updatedBook);
                }
                case 5 -> {
                    System.out.println("-------Delete Book by ID--------");
                    System.out.print("Enter the book ID: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    try {
                        bookController.deleteBookById(bookId);
                        System.out.println("Book deleted Successfully...");
                    }
                    catch (RuntimeException e) {
                        System.out.println("Delete operation failed: " + e.getMessage());
                    }
                }

                /*
                Task 2 — Find by relationship (HQL/JPQL)
                  Write a method findBooksByAuthor(Long authorId) that returns all books written by a
                  given author, using HQL with a join.
                 */
                case 6 -> {
                    System.out.println("------Get Book by Author ID------");
                    System.out.print("Enter Author ID: ");
                    int authorId = sc.nextInt();
                    List<Book> authorBooks = bookController.findBooksByAuthor(authorId);
                    if(authorBooks.isEmpty()) {
                        System.out.println("The author with id = " + authorId + " has no books..");
                    }
                    authorBooks.forEach(System.out :: println);
                }
                case 7 -> {
                    System.out.println("------Get Book by Member ID------");
                    System.out.print("Enter Member ID: ");
                    int memberId = sc.nextInt();
                    List<Book> memberBooks = bookController.findBooksBorrowedByMember(memberId);
                    if(memberBooks.isEmpty()) {
                        System.out.println("The member with id = " + memberId + " has borrowed no books..");
                    }
                    memberBooks.forEach(System.out :: println);
                }

                /*
                Task 3 — Dynamic Filtering (Criteria API)
                    Write a method:
                    List<Book> filterBooks(Genre genre, BookStatus status)
                    - Both parameters are optional (nullable) — filtering should work with either, both, or
                      neither provided.
                    - Use the Criteria API (CriteriaBuilder, CriteriaQuery, Root, Predicate list) —
                      not HQL — for this task.
                    - If both are null, return all books
                 */
                case 8 -> {
                    System.out.println("--------Filter Books by Genre / BookStatus--------");
                    System.out.println("Enter genre: ");
                    Arrays.stream(Genre.values()).forEach(System.out :: println);
                    System.out.println("For 'null' value just enter and do not give a input.");
                    String genre = sc.nextLine();
                    Genre genreCheck = null;
                    if(!genre.trim().isEmpty()) {
                        genreCheck = Genre.valueOf(genre.toUpperCase());
                    }

                    System.out.println("Enter status: ");
                    Arrays.stream(BookStatus.values()).forEach(System.out :: println);
                    System.out.println("For 'null' value just enter and do not give a input.");
                    String status = sc.nextLine();
                    BookStatus bookStatus = null;
                    if(!status.trim().isEmpty()) {
                        bookStatus = BookStatus.valueOf(status.toUpperCase());
                    }
                    List<Book> filteredList = bookController.filterBooks(genreCheck, bookStatus);
                    if(filteredList.isEmpty()) {
                        System.out.println("No books with given filter.");
                    }
                    else {
                        filteredList.forEach(System.out :: println);
                    }
                }

                /*
                Task 6:
                  Write a method findRegistrationsForEvent(Long eventId) that returns all
                  EventRegistration rows for a given event
                 */
                case 9 -> {
                    System.out.println("--------Get Registration by Event ID (Without DTO)--------");
                    System.out.print("Enter the event ID: ");
                    long eventId = sc.nextLong();
                    try {
                        List<EventRegistration> eventRegistrations = eventController.findRegistrationsForEvent(eventId);
                        if(eventRegistrations.isEmpty()) {
                            System.out.println("No registrations found for the event ID=" + eventId);
                        }
                        else {
                            eventRegistrations.forEach(System.out :: println);
                        }
                    }
                    catch (RuntimeException e) {
                        System.out.println("Operation failed: " + e.getMessage());
                    }
                }

                /*
                Task 7:
                    Write a method findNoShowsForEvent(Long eventId) that returns all Members who
                    registered for a given event but did not attend — filtering directly on
                    EventRegistration.attendanceStatus.
                 */
                case 10 -> {
                    System.out.println("--------Members who did not show for the Event--------");
                    System.out.print("Enter event ID: ");
                    long eventId = sc.nextInt();
                    sc.nextLine();
                    try {
                        List<Member> notShowMembers = memberController.findNoShowsForEvent(eventId);
                        if(notShowMembers.isEmpty()) {
                            System.out.println("There is not member who did not show for the event ID=" + eventId);
                        }
                        else {
                            notShowMembers.forEach(System.out :: println);
                        }
                    }
                    catch (RuntimeException e) {
                        System.out.println("Operation failed: " + e.getMessage());
                    }
                }

                /*
                Task 8:
                    Create an EventRegistrationDTO and an EventRegistrationMapper class to convert
                    EventRegistration entities to DTOs, and update the service-facing method to return
                    List<EventRegistrationDTO> instead of List<EventRegistration>.
                 */
                case 11 -> {
                    System.out.println("--------Get Registration by Event ID (With DTO)--------");
                    System.out.print("Enter the event ID: ");
                    long eventId = sc.nextLong();
                    try {
                        List<EventRegistrationDto> eventRegistrationsDto = eventController.findRegistrationsForEventWithDto(eventId);
                        if(eventRegistrationsDto.isEmpty()) {
                            System.out.println("No registrations found for the event ID=" + eventId);
                        }
                        else {
                            eventRegistrationsDto.forEach(System.out :: println);
                        }
                    }
                    catch (RuntimeException e) {
                        System.out.println("Operation failed: " + e.getMessage());
                    }
                }
            }
        }

        sc.close();
        LibraryConfig.closeFactory();
    }
}
