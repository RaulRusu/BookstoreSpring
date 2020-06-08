package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.domain.Acquisition;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.model.domain.Client;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class Reports implements IReports {
    Logger logger = LoggerFactory.getLogger(Reports.class);

    @Autowired
    IBookService bookService;
    @Autowired
    IClientService clientService;
    @Autowired
    IAcquisitionService acquisitionService;

    @Override
    public Collection<Map.Entry<Client, List<Book>>> reportClientAcquisitions() {
        logger.trace("reportClientAcquisitions - method entered");
        Iterable<Acquisition> acquisitions=acquisitionService.getAll();
        Iterable<Client> clients=clientService.getAll();
        Iterable<Book> books=bookService.getAll();

        Collection<Map.Entry<Client, List<Book>>> result = StreamSupport.stream(clients.spliterator(),false)
                .collect(Collectors.toMap(client->client,
                        client-> StreamSupport.stream(acquisitions.spliterator(),false)
                                .filter(acquisition -> acquisition.getClientID()==client.getId())
                                .map(acquisition ->
                                        StreamSupport.stream(books.spliterator(),false)
                                                .filter(book -> book.getId()==acquisition.getBookID())
                                                .findAny().orElse(null)
                                ).collect(Collectors.toList()))).entrySet();

        logger.trace("reportClientAcquisitions - method finished");
        return result;
    }

    @Override
    public Collection<Map.Entry<Client, Integer>> reportClientsSpentMoney() {
        logger.trace("reportClientAcquisitions - method entered");

        Iterable<Acquisition> acquisitions=acquisitionService.getAll();
        Iterable<Client> clients=clientService.getAll();

        Collection<Map.Entry<Client, Integer>> result = StreamSupport.stream(clients.spliterator(),false)
                .collect(Collectors.toMap(client->client,
                        client-> StreamSupport.stream(acquisitions.spliterator(),false)
                                .mapToInt(acquisition -> {
                                    if(acquisition.getClientID()==client.getId())
                                        return acquisition.getPrice();
                                    else return 0;
                                }).sum())).entrySet();

        logger.trace("reportClientAcquisitions - method finished");
        return result;
    }

    @Override
    public Collection<Map.Entry<Book, Integer>> reportBooksBought() {
        logger.trace("reportClientAcquisitions - method entered");
        Iterable<Acquisition> acquisitions=acquisitionService.getAll();
        Iterable<Book> books=bookService.getAll();

        Collection<Map.Entry<Book, Integer>> result = StreamSupport.stream(books.spliterator(),false)
                .collect(Collectors.toMap(book->book,
                        book-> StreamSupport.stream(acquisitions.spliterator(),false)
                                .mapToInt(acquisition -> {
                                    if(acquisition.getBookID()==book.getId())
                                        return 1;
                                    else return 0;
                                }).sum())).entrySet();
        logger.trace("reportClientAcquisitions - method finished");
        return result;
    }
}
