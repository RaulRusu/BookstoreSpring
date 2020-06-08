package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.model.validators.BookValidator;
import ro.ubb.catalog.core.repository.BookRepository;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService extends CrudService<Integer, Book> implements IBookService{

    Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BookRepository repository, BookValidator bookValidator) {
        super(repository, bookValidator);
        super.logger = this.logger;
    }

    @Override
    public Collection<Book> FilterBooksByDate(Date date) {
        logger.trace("FilterBooksByDate - method entered: " + "date = {}", date);
        Iterable<Book> books = this.repository.findAll();

        Collection<Book> result = StreamSupport.stream(books.spliterator(), false)
                .filter(book -> book.getReleaseDate().compareTo(date) > 0).collect(Collectors.toSet());

        logger.trace("FilterBooksByDate - method finished: " + "result = {}", result);

        return result;
    }
}
