package pl.sda.library.repository;

import org.springframework.stereotype.Repository;
import pl.sda.library.exception.BookNotFoundException;
import pl.sda.library.model.Book;

import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Repository
public class BookRepository {

    private Set<Book> books = initialize();

    private Set<Book> initialize() {
        return new HashSet<>(Arrays.asList(new Book(41L, "Harry Potter", "JK Rowling"),
            new Book(2L, "Testy", "Kaczanowski"), new Book(3L, "Testy", "Kaczanowski"),
            new Book(4L, "Wladca Pierscieni", "Tolkien"),
            new Book(5L, "Wladca Pierscieni", "Tolkien"),
            new Book(6L, "Wladca Pierscieni", "Tolkien"), new Book(7L, "Anioly i demony", "Brown"),
            new Book(8L, "Effective Java", "Bloch"), new Book(9L, "Czysty kod", "Martin"),
            new Book(10L, "Czysty kod", "Martin")));
    }

    public Optional<Book> borrowBook(String title, LocalDate borrowedTill) {
        Optional<Book> bookToBorrow = books.stream()
            .filter(book -> book.getTitle().equals(title))
            .filter(book -> book.getDateOfReturn() == null)
            .findAny();
        bookToBorrow.ifPresent(book -> book.setDateOfReturn(borrowedTill));
        return bookToBorrow;
    }

    public Long addBook(Book book) {
        Long id = generateNextId();
        book.setId(id);
        books.add(book);
        return id;
    }

    public boolean deleteBook(long id) {
        Book bookToRemove = books.stream()
            .filter(book -> book.getId().equals(id))
            .filter(book -> book.getDateOfReturn() == null)
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException("Ni ma"));
       return books.remove(bookToRemove);
    }

    public long returnBook(long id) {
        Book returnedBook = books.stream()
            .filter(book -> book.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException("Ni ma"));
        long overdue = 0;
        if (LocalDate.now().isAfter(returnedBook.getDateOfReturn())) {
            overdue = DAYS.between(LocalDate.now(), returnedBook.getDateOfReturn());
        }
        returnedBook.setDateOfReturn(null);
        return overdue;
    }

    private Long generateNextId() {//Not thread safe
        OptionalLong max = books.stream().mapToLong(Book::getId).max();
        return max.isPresent() ? max.getAsLong() + 1 : 1;
    }
}
