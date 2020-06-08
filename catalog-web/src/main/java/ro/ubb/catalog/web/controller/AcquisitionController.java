package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.domain.Acquisition;
import ro.ubb.catalog.core.service.IAcquisitionService;
import ro.ubb.catalog.web.converter.AcquisitionConverter;
import ro.ubb.catalog.web.dto.AcquisitionDto;

@RestController
@RequestMapping(value = "/acquisitions")
public class AcquisitionController extends CrudController<Integer, Acquisition, AcquisitionDto> {
    Logger logger = LoggerFactory.getLogger(AcquisitionController.class);
    @Autowired
    IAcquisitionService acquisitionService;
    @Autowired
    AcquisitionConverter acquisitionConverter;

    @Autowired
    public AcquisitionController(IAcquisitionService service, AcquisitionConverter converter) {
        super(service, converter);
        super.logger = this.logger;
    }

    @RequestMapping(value = "/cascadeBook/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    void cascadeBook(@PathVariable Integer id){
        logger.trace("cascadeBook - method entered: " + "type = delete" + ", id = {}", id);
        this.acquisitionService.cascadeBook(id);
        logger.trace("cascadeBook - method finished");
    }

    @RequestMapping(value = "/cascadeClient/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    void cascadeClient(@PathVariable Integer id){
        logger.trace("cascadeClient - method entered: " + "type = delete" + ", id = {}", id);
        this.acquisitionService.cascadeClient(id);
        logger.trace("cascadeClient - method finished");
    }
}
