package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.web.dto.BookDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class BookConverter extends BaseConverter<Integer, Book, BookDto> {
    @Override
    public Book convertDtoToModel(BookDto dto) {
        Book book = null;
        try {
            book = Book.builder()
                .name(dto.getName())
                .author(dto.getAuthor())
                .releaseDate(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getReleaseDate()))
                .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        book.setId(dto.getId());
        return book;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto bookDto = BookDto.builder()
                .name(book.getName())
                .author(book.getAuthor())
                .releaseDate(new SimpleDateFormat("dd/MM/yyyy").format(book.getReleaseDate()))
                .build();

        bookDto.setId(book.getId());
        return bookDto;

    }

}
