package io.aroundij.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {
    private long bookId;
    private String title;
    private String isbn13;
    private double price;
    private LocalDate publishDate;
    private AuthorDTO author;

}
