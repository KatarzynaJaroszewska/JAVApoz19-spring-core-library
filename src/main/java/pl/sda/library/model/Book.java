package pl.sda.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private Long id;
    private String author;
    private String title;
    private LocalDate dateOfReturn;

    public Book(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.author = author;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(LocalDate dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(author, book.author) && Objects
            .equals(title, book.title) && Objects.equals(dateOfReturn, book.dateOfReturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, dateOfReturn);
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", author='" + author + '\'' + ", title='" + title + '\''
            + ", dateOfReturn=" + dateOfReturn + '}';
    }
}
