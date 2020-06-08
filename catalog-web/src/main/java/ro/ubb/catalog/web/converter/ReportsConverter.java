package ro.ubb.catalog.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.model.domain.Client;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.PairDto;
import ro.ubb.catalog.web.dto.listDto.BaseListDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReportsConverter {
    @Autowired
    BookConverter bookConverter;
    @Autowired
    ClientConverter clientConverter;
    @Autowired
    AcquisitionConverter acquisitionConverter;

    public List<PairDto<ClientDto, BaseListDto<BookDto>>> convertClientAcquisitionsToDtos(Collection<Map.Entry<Client, List<Book>>> collection) {
        return collection.stream()
                .map(entry -> new PairDto<>(clientConverter.convertModelToDto(entry.getKey()),
                         new BaseListDto<>(bookConverter.convertModelsToDtoList(entry.getValue()))))
                .collect(Collectors.toList());

    }

    public List<PairDto<ClientDto, Integer>> convertClientsSpentMoneyToDtos(Collection<Map.Entry<Client, Integer>> collection) {
        return collection.stream()
                .map(entry -> new PairDto<>(clientConverter.convertModelToDto(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<PairDto<BookDto, Integer>> convertBooksBoughtToDtos(Collection<Map.Entry<Book, Integer>> collection) {
        return collection.stream()
                .map(entry -> new PairDto<>(bookConverter.convertModelToDto(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }
}
