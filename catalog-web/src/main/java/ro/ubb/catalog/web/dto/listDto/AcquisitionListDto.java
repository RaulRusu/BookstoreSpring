package ro.ubb.catalog.web.dto.listDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.ubb.catalog.web.dto.AcquisitionDto;
import ro.ubb.catalog.web.dto.ClientDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class AcquisitionListDto extends BaseListDto<AcquisitionDto> {
}
