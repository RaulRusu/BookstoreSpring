package ro.ubb.catalog.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

@Component
public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidatorException {
        if(entity.getAuthor().equals("") || entity.getName().equals("") || (entity.getId()!=null && entity.getId() < 0))
            throw new ValidatorException("Book entity is not valid");
    }
}
