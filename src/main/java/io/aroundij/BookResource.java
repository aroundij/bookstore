package io.aroundij;

import io.aroundij.domain.Author;
import io.aroundij.domain.Book;
import io.aroundij.dto.BookDTO;
import io.aroundij.exception.BusinessException;
import io.aroundij.mapping.BookMapper;
import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Path("/api/books")
public class BookResource {

    public static final String ISBN_13 = "isbn13";

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void createBook(BookDTO bookDTO) {
        Log.info("Begin createBook");
        // Validate book

        // Map Book
        Book book = BookMapper.INSTANCE.bookDTOToBook(bookDTO);
        // Persist book
        Book.persist(book);
        Log.info("End createBook");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return Book.listAll();
    }

    @DELETE
    @Path("/{isbn13}")
    @Transactional
    public void deleteBook(@PathParam(ISBN_13) String isbn13) {
        Log.info("Begin deleteBook");
        Book book = Book.find(ISBN_13, isbn13).firstResult();
        if(Objects.isNull(book)) {
            throw new BusinessException("No book found with isbn13 : " + isbn13);
        }
        Book.delete(ISBN_13, isbn13);
        Log.info("End deleteBook");
    }

    @GET
    @Path("/{isbn13}")
    public BookDTO findBook(@PathParam(ISBN_13) String isbn13) {
        Log.info("Begin findBook");
        Book book = Book.find(ISBN_13, isbn13).firstResult();
        Log.info("End findBook");
        return BookMapper.INSTANCE.bookToBookDTO(book);
    }

    @POST
    @Path("/generate")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public BookDTO generateRandomBook() {
        Book book = generateBook();
        Book.persist(book);
        return BookMapper.INSTANCE.bookToBookDTO(book);
    }

    private String randomString(int length) {
        return ThreadLocalRandom.current()
                .ints(2, 36)
                .limit(length)
                .map(i -> Character.forDigit(i, 36))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
    private double randomPrice() {
        return ThreadLocalRandom.current().nextDouble(100, 99999999);
    }

    private Book generateBook() {
        Log.info("***** Begin generateBook *****");

        Book book = new Book();
        Author author = new Author();
        book.setAuthor(author);

        book.setIsbn13("13_" + randomString(10));
        book.setTitle(randomString(15));
        book.setPrice(randomPrice());
        book.setPublishDate(LocalDate.of(2001, Month.AUGUST, 31));

        author.setName(randomString(15));
        author.setBirthdate(LocalDate.of(1948, Month.MAY, 15));
        author.setSport(randomString(15));

        Log.info(book);

        Log.info("***** End generateBook *****");
        return book;
    }

}
