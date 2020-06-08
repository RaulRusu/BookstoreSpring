package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.domain.BaseEntity;
import ro.ubb.catalog.core.service.IService;
import ro.ubb.catalog.web.converter.Converter;
import ro.ubb.catalog.web.dto.BaseDto;
import ro.ubb.catalog.web.dto.listDto.BaseListDto;

import java.io.Serializable;

public class CrudController<ID extends Serializable, Model extends BaseEntity<ID>, Dto extends BaseDto> {
    protected Logger logger;
    private IService<ID, Model> service;
    private Converter<ID, Model, Dto> converter;

    public CrudController(IService<ID, Model> service, Converter<ID,Model, Dto> converter) {
        this.service = service;
        this.converter = converter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    BaseListDto<Dto> getEntities() {
        logger.trace("getEntities - method entered: " + "type = get");
        BaseListDto<Dto> result =  new BaseListDto<>(this.converter
                .convertModelsToDtos(this.service.getAll()));
        logger.trace("getEntities - method finished: " + "result = {}", result);
        return result;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<?> saveEntitie(@RequestBody Dto entityDto) {
        logger.trace("saveEntitie - method entered: " + "type = post");
        this.service.add(this.converter.convertDtoToModel(entityDto));
        ResponseEntity<?> result =  new ResponseEntity<>(HttpStatus.OK);
        logger.trace("getEntities - method finished");
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    void updateEntitie(@RequestBody Dto entityDto, @PathVariable Integer id) {
        logger.trace("saveEntitie - method entered: " + "type = post" + ", id = {}", id);
        this.service.update(this.converter.convertDtoToModel(entityDto));
        logger.trace("saveEntitie - method finished");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    void deleteEntitie(@PathVariable ID id){
        logger.trace("deleteEntitie - method entered: " + "type = delete" + ", id = {}", id);
        this.service.delete(id);
        logger.trace("deleteEntitie - method finished");
    }

    @RequestMapping(value = "/sorted", method = RequestMethod.GET)
    BaseListDto<Dto> getSortedEntitie() {
        logger.trace("getSortedEntitie - method entered: " + "type = get");
        BaseListDto<Dto> result = new BaseListDto<>(this.converter
                .convertModelsToDtoList(this.service.getAll()));
        logger.trace("deleteEntitie - method finished: " + "result = {}", result);
        return result;
    }
}
