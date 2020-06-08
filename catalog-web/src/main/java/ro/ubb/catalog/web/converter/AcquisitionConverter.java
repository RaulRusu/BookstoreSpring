package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.domain.Acquisition;
import ro.ubb.catalog.core.model.domain.Book;
import ro.ubb.catalog.web.dto.AcquisitionDto;
import ro.ubb.catalog.web.dto.BookDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class AcquisitionConverter extends BaseConverter<Integer, Acquisition, AcquisitionDto> {
    @Override
    public Acquisition convertDtoToModel(AcquisitionDto dto) {
        Acquisition acquisition = null;
        try {
            acquisition = Acquisition.builder()
                    .clientID(dto.getClientID())
                    .bookID(dto.getBookID())
                    .purchaseDate(new SimpleDateFormat("dd/MM/yyyy").parse(dto.getPurchaseDate()))
                    .price(dto.getPrice())
                    .build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        acquisition.setId(dto.getId());
        return acquisition;
    }

    @Override
    public AcquisitionDto convertModelToDto(Acquisition acquisition) {
        AcquisitionDto acquisitionDto = AcquisitionDto.builder()
                .clientID(acquisition.getClientID())
                .bookID(acquisition.getBookID())
                .purchaseDate(new SimpleDateFormat("dd/MM/yyyy").format(acquisition.getPurchaseDate()))
                .price(acquisition.getPrice())
                .build();

        acquisitionDto.setId(acquisition.getId());
        return acquisitionDto;
    }
}
