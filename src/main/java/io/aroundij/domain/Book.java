package io.aroundij.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
public class Book extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long bookId;
    private String title;
    @Column(name = "isbn_13")
    private String isbn13;
    @Column(name = "publish_date")
    private LocalDate publishDate;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Author.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;
    private double price;
}
