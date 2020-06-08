package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.domain.BaseEntity;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;
import ro.ubb.catalog.core.model.validators.Validator;
import ro.ubb.catalog.core.repository.BookstoreRepository;

import java.io.Serializable;
import java.util.Collection;


public abstract class CrudService<ID extends Serializable, T extends BaseEntity<ID>> implements IService<ID, T> {
    protected Logger logger;
    protected BookstoreRepository<T, ID> repository;
    protected Validator<T> validator;

    public CrudService(BookstoreRepository<T, ID> repository, Validator<T> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public void add(T entity) throws ValidatorException {
        logger.trace("add - method entered: " + "entity = {}", entity);
        this.validator.validate(entity);
        logger.trace("add - entity validate");
        this.repository.save(entity);
        logger.trace("add - method finished");
    }

    @Override
    public Collection<T> getAll() {
        logger.info("getAll - method entered");
        Collection<T> result = repository.findAll();
        logger.trace("getAll - method finished: " + "result: {}", result.toString());
        return result;
    }

    @Override
    public Collection<T> getAll(Sort sort) {
        logger.trace("getAll - method entered" + "sort: {}", sort);
        Collection<T> result = repository.findAll(sort);
        logger.trace("getAll - method finished: " + "result: {}", result.toString());
        return result;
    }

    @Override
    public void delete(ID id) {
        logger.trace("delete - method entered: " + "id = {}", id);
        this.repository.deleteById(id);

        logger.trace("delete - method finished: " + "id = {}", id);
    }

    @Override
    public void update(T entity) throws ValidatorException {
        logger.trace("update - method entered:" + "entity = {}", entity);
        this.validator.validate(entity);
        logger.trace("update - entity validate");
        this.repository.save(entity);
        logger.trace("update - method finished");
    }
}
