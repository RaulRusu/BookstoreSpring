package ro.ubb.catalog.web.dto.listDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.ubb.catalog.web.dto.ClientDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientListDto extends BaseListDto<ClientDto> {
}
