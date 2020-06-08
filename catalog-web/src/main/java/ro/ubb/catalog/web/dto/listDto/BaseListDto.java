package ro.ubb.catalog.web.dto.listDto;

import lombok.*;
import ro.ubb.catalog.web.dto.BaseDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseListDto<Dto> {
    protected List<Dto> dtoList;
}
