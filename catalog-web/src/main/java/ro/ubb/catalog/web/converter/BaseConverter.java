package ro.ubb.catalog.web.converter;

import ro.ubb.catalog.core.model.domain.BaseEntity;
import ro.ubb.catalog.web.dto.BaseDto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by radu.
 */

public abstract class BaseConverter<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseDto>
        implements Converter<ID, Model, Dto> {


    public List<ID> convertModelsToIDs(Set<Model> models) {
        return models.stream()
                .map(model -> model.getId())
                .collect(Collectors.toList());
    }

    public List<Integer> convertDTOsToIDs(Set<Dto> dtos) {
        return dtos.stream()
                .map(dto -> dto.getId())
                .collect(Collectors.toList());
    }

    public List<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(model -> convertModelToDto(model))
                .collect(Collectors.toList());
    }

    public List<Dto> convertModelsToDtoList(Collection<Model> models) {
        return models.stream()
                .map(model ->convertModelToDto(model))
                .collect(Collectors.toList());
    }
}
