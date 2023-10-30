package io.aroundij;

import io.aroundij.domain.Author;
import io.aroundij.domain.Book;
import io.aroundij.dto.BookDTO;
import io.aroundij.mapping.BookMapper;
import io.quarkus.logging.Log;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Path("/books")
public class BookResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void createBook(BookDTO bookDTO) {

        // Validate book

        // Map Book
        Book book = BookMapper.INSTANCE.bookDTOToBook(bookDTO);
        // Persist book
        Book.persist(book);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return Book.listAll();
    }

    @DELETE
    @Path("/{isbn13}")
    public void deleteBook(@PathParam("isbn13") String isbn13) {
        Book book = Book.find("isbn13", isbn13).firstResult();
        if(Objects.isNull(book)) {
            throw new RuntimeException("No book found with isbn13 : " + isbn13);
        }
        Book.delete("isbn13", isbn13);
    }

    @GET
    @Path("/{isbn13}")
    public BookDTO findBook(@PathParam("isbn13") String isbn13) {
        Book book = Book.find("isbn13", isbn13).firstResult();
        return BookMapper.INSTANCE.bookToBookDTO(book);
    }

    private String randomString(int length) {
        return ThreadLocalRandom.current()
                .ints(2, 36)
                .limit(length)
                .map(i -> Character.forDigit(i, 36))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    private Book generateBook() {
        Log.info("***** Begin generateBook *****");

        Book book = new Book();
        Author author = new Author();
        book.setAuthor(author);

        book.setIsbn13("13_" + randomString(10));
        book.setTitle(randomString(30));

        author.setName(randomString(30));
        author.setBirthdate(new Date(1_000_000));
        author.setSport(randomString(20));

        Log.info(book);

        Log.info("***** End generateBook *****");
        return book;
    }

}
