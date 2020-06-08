package ro.ubb.catalog.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class AcquisitionDto extends BaseDto{
    private Integer clientID;
    private Integer bookID;
    private String purchaseDate;
    private Integer price;
}
