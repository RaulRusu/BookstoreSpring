package ro.ubb.catalog.core.model.validators;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.domain.Client;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

@Component
public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity.getName().equals("") || entity.getAge() < 1 ||(entity.getId()!=null && entity.getId() < 0))
            throw new ValidatorException("Client entity is not valid");
    }
}
