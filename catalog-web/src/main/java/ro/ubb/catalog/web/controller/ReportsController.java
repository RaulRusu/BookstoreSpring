package ro.ubb.catalog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.model.domain.Client;
import ro.ubb.catalog.core.service.IReports;
import ro.ubb.catalog.web.converter.ReportsConverter;
import ro.ubb.catalog.web.dto.BookDto;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.PairDto;
import ro.ubb.catalog.web.dto.listDto.BaseListDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/reports")
public class ReportsController {
    @Autowired
    IReports reports;
    @Autowired
    ReportsConverter reportsConverter;

    @RequestMapping(value = "/reportClientAcquisitions", method = RequestMethod.GET)
    BaseListDto<PairDto<ClientDto, BaseListDto<BookDto>>> reportClientAcquisitions() {
        //todo: log
        Collection<Map.Entry<Client, List<Book>>> result = this.reports.reportClientAcquisitions();
        System.out.println(this.reportsConverter.convertClientAcquisitionsToDtos(result));
        return new BaseListDto<>(this.reportsConverter.convertClientAcquisitionsToDtos(result));
    }
    @RequestMapping(value = "/reportClientsSpentMoney", method = RequestMethod.GET)
    public BaseListDto<PairDto<ClientDto, Integer>> reportClientsSpentMoney() {
        //todo: log
        Collection<Map.Entry<Client, Integer>> result = this.reports.reportClientsSpentMoney();

        return new BaseListDto<>(this.reportsConverter.convertClientsSpentMoneyToDtos(result));
    }

    @RequestMapping(value = "/reportBooksBought", method = RequestMethod.GET)
    public BaseListDto<PairDto<BookDto, Integer>> reportBooksBought() {
        //todo: log
        Collection<Map.Entry<Book, Integer>> result = this.reports.reportBooksBought();

        return new BaseListDto<>(this.reportsConverter.convertBooksBoughtToDtos(result));
    }
}
