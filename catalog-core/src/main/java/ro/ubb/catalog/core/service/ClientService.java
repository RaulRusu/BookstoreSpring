package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.domain.Client;
import ro.ubb.catalog.core.model.validators.ClientValidator;
import ro.ubb.catalog.core.repository.ClientRepository;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService extends CrudService<Integer, Client> implements IClientService {

    Logger logger = LoggerFactory.getLogger(Client.class);

    @Autowired
    public ClientService(ClientRepository repository, ClientValidator clientValidator) {
        super(repository, clientValidator);
        super.logger = this.logger;
    }

    @Override
    public Collection<Client> filterClientsByAge(int age) {

        logger.trace("filterClientsByAge - method entered: " + "age = {}", age);
        Iterable<Client> clients = this.repository.findAll();

        Collection<Client> result = StreamSupport.stream(clients.spliterator(), false)
                .filter(client -> client.getAge() >= age).collect(Collectors.toSet());
        logger.trace("filterClientsByAge - method finished: " + "result = {}", result);

        return result;
    }
}
