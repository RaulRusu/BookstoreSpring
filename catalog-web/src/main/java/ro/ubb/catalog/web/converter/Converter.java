package ro.ubb.catalog.web.converter;

import ro.ubb.catalog.core.model.domain.BaseEntity;
import ro.ubb.catalog.web.dto.BaseDto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by radu.
 */

public interface Converter<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

    List<Dto> convertModelsToDtos(Collection<Model> models);

    List<Dto> convertModelsToDtoList(Collection<Model> models);
}

