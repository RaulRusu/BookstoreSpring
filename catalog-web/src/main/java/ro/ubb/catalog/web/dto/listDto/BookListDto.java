package ro.ubb.catalog.web.dto.listDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.ubb.catalog.web.dto.BookDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookListDto extends BaseListDto<BookDto> {
}
