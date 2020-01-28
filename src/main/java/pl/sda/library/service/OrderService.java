package pl.sda.library.service;

import org.springframework.stereotype.Service;
import pl.sda.library.model.Book;
import pl.sda.library.model.Fine;
import pl.sda.library.repository.BookRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderService {

    private final BookRepository bookRepository;

    public OrderService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> borrowBook(String title) {
        return bookRepository.borrowBook(title, LocalDate.now().plusDays(30));
    }

    public Long addBook(Book book) {
        return bookRepository.addBook(book);
    }

    public boolean deleteBook(long id) {
        return bookRepository.deleteBook(id);
    }

    public Optional<Fine> returnBook(long id) {
        long overdue = bookRepository.returnBook(id);
        return overdue >= 1 ? Optional.of(new Fine(BigDecimal.ONE, overdue)) : Optional.empty();
    }
}
