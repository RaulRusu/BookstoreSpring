package ro.ubb.catalog.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.core.model.domain.Client;
import ro.ubb.catalog.core.service.IClientService;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.listDto.BaseListDto;

@RestController
@RequestMapping(value = "/clients")
public class ClientController extends CrudController<Integer, Client, ClientDto> {
    Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    IClientService clientService;
    @Autowired
    ClientConverter clientConverter;

    public ClientController(IClientService service, ClientConverter converter) {
        super(service, converter);
        super.logger = this.logger;
    }

    @RequestMapping(value = "/filter/{age}", method = RequestMethod.GET)
    BaseListDto<ClientDto> getSortedClients(@PathVariable Integer age) {
        logger.trace("getSortedClients - method entered: " + "type = get" + ", age = {}", age);
        BaseListDto<ClientDto> result = new BaseListDto<>(this.clientConverter
                .convertModelsToDtoList(this.clientService.filterClientsByAge(age)));

        logger.trace("getSortedClients - method finished: " + "result = {}", result);
        return result;
    }
}
