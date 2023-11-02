package io.aroundij.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

@Entity
public class Book extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long bookId;
    private String title;
    @Column(name = "isbn_13")
    private String isbn13;
    @Pattern(regexp = "\\d{4}")
    private int year;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Author.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return bookId == book.bookId && year == book.year && Objects.equals(title, book.title) && Objects.equals(isbn13, book.isbn13) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, isbn13, year, author);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", year=" + year +
                ", author=" + author +
                '}';
    }
}
