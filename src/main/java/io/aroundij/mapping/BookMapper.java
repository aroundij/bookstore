package io.aroundij.mapping;

import io.aroundij.domain.Book;
import io.aroundij.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book bookDTOToBook(BookDTO bookDTO);
    BookDTO bookToBookDTO(Book book);
}
