package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.service.IBookService;
import ro.ubb.catalog.web.converter.BookConverter;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.listDto.BaseListDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/books")
public class BookController extends CrudController<Integer, Book, BookDto> {
    Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    IBookService bookService;
    @Autowired
    BookConverter bookConverter;

    @Autowired
    public BookController(IBookService service, BookConverter converter) {
        super(service, converter);
        super.logger = this.logger;
    }

    @RequestMapping(value = "/filter/{stringDate}", method = RequestMethod.GET)
    BaseListDto<BookDto> getFilteredBooks(@PathVariable String stringDate) throws ParseException {
        logger.trace("getFilteredBooks - method entered: " + "type = get" + ", date = {}", stringDate);
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
        BaseListDto<BookDto> result = new BaseListDto<>(bookConverter
                .convertModelsToDtoList(bookService.FilterBooksByDate(date)));

        logger.trace("getFilteredBooks - method finished: " + "result = {}", result);
        return result;
    }
}
