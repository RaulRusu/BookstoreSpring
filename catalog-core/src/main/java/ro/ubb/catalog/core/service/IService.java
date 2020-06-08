package ro.ubb.catalog.core.service;

import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.domain.BaseEntity;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

import java.io.Serializable;
import java.util.Collection;

public interface IService<ID extends Serializable, T extends BaseEntity<ID>> {
    void add(T entity) throws ValidatorException;
    Collection<T> getAll();

    Collection<T> getAll(Sort sort);

    void delete(ID id);

    void update(T entity) throws ValidatorException;
}
