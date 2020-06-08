package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.domain.Acquisition;
import ro.ubb.catalog.core.model.exceptions.ValidatorException;

public interface IAcquisitionService extends IService<Integer, Acquisition> {
    @Override
    void add(Acquisition entity) throws ValidatorException;

    @Override
    void update(Acquisition entity) throws ValidatorException;

    void cascadeBook(int id);

    void cascadeClient(int id);

}
