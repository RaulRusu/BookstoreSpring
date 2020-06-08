package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.domain.Acquisition;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.model.domain.Client;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.model.validators.Validator;
import ro.ubb.catalog.core.repository.BookstoreRepository;

import java.util.stream.StreamSupport;

@Service
public class AcquisitionService extends CrudService<Integer, Acquisition> implements IAcquisitionService {

    Logger logger = LoggerFactory.getLogger(AcquisitionService.class);

    @Autowired
    IBookService bookService;
    @Autowired
    IClientService clientService;

    @Autowired
    public AcquisitionService(BookstoreRepository<Acquisition, Integer> repository, Validator<Acquisition> validator) {
        super(repository, validator);
        super.logger = this.logger;
    }

    @Override
    public void add(Acquisition acquisition) throws ValidatorException {
        logger.trace("add - method entered: " + "acquisition = {}", acquisition);
        Iterable<Book> books = bookService.getAll();
        Iterable<Client> clients = clientService.getAll();
        boolean foundBook = StreamSupport.stream(books.spliterator(),false).anyMatch(book -> book.getId()==acquisition.getBookID());
        logger.trace("add - book found: " + "bookID = {}", acquisition.getBookID());
        boolean foundClient = StreamSupport.stream(clients.spliterator(),false).anyMatch(client -> client.getId()==acquisition.getClientID());
        logger.trace("add - client found: " + "clientID = {}", acquisition.getClientID());
        if(foundBook&&foundClient) {
            super.add(acquisition);
        }
        else{
            logger.trace("add - Book or Client does not exist");
            throw new ValidatorException("Book or Client does not exist");
        }

        logger.trace("update - method finished");
    }

    @Override
    public void update(Acquisition acquisition) throws ValidatorException {
        logger.trace("update - method entered: " + "acquisition = {}", acquisition);
        Iterable<Book> books = bookService.getAll();
        Iterable<Client> clients = clientService.getAll();
        boolean foundBook = StreamSupport.stream(books.spliterator(),false).anyMatch(book -> book.getId()==acquisition.getBookID());
        logger.trace("update - book found: " + "bookID = {}", acquisition.getBookID());
        boolean foundClient = StreamSupport.stream(clients.spliterator(),false).anyMatch(client -> client.getId()==acquisition.getClientID());
        logger.trace("update - client found: " + "clientID = {}", acquisition.getClientID());
        if(foundBook&&foundClient) {
            logger.trace("update - Book or Client does not exist");
            super.update(acquisition);
        }
        else{
            logger.trace("update - Book or Client does not exist");
            throw new ValidatorException("Book or Client does not exist");
        }
        logger.trace("update - method finished");
    }

    @Override
    public void cascadeBook(int id) {
        logger.trace("cascadeBook - method entered: " + "id = {}", id);
        Iterable<Acquisition> acquisitions = repository.findAll();
        StreamSupport.stream(acquisitions.spliterator(),false).forEach(acquisition -> {if(acquisition.getBookID()==id) this.delete(acquisition.getId());});
        logger.trace("cascadeBook - method finished");
    }

    @Override
    public void cascadeClient(int id) {
        logger.trace("cascadeClient - method entered: " + "id = {}", id);
        Iterable<Acquisition> acquisitions = repository.findAll();
        StreamSupport.stream(acquisitions.spliterator(),false).forEach(acquisition -> {if(acquisition.getClientID()==id) this.delete(acquisition.getId());});
        logger.trace("cascadeClient - method finished");
    }
}
