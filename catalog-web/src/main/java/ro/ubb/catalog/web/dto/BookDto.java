package ro.ubb.catalog.web.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class BookDto extends BaseDto{
    private String name;
    private String author;
    private String releaseDate;
}
