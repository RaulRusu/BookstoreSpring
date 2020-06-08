package ro.ubb.catalog.core.model.validators;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.domain.Acquisition;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

@Component
public class AcquisitionValidator implements Validator<Acquisition> {
    @Override
    public void validate(Acquisition entity) throws ValidatorException {
        if(entity.getClientID() < 0 || entity.getBookID() < 0 || (entity.getId()!=null && entity.getId() < 0)||entity.getPrice() < 0)
            throw new ValidatorException("Book entity is not valid");
    }
}
