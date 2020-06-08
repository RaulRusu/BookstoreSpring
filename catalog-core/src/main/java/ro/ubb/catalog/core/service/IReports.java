package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.model.domain.Client;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IReports {
    Collection<Map.Entry<Client, List<Book>>> reportClientAcquisitions();

    Collection<Map.Entry<Client, Integer>> reportClientsSpentMoney();

    Collection<Map.Entry<Book, Integer>> reportBooksBought();
}
