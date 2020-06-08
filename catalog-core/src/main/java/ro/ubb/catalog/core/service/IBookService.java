package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.domain.Book;

import java.util.Collection;
import java.util.Date;

public interface IBookService extends IService<Integer, Book> {
    Collection<Book> FilterBooksByDate(Date date);
}
