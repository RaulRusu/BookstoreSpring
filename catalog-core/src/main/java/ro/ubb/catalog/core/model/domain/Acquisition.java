package ro.ubb.catalog.core.model.domain;

import lombok.*;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Acquisition extends BaseEntity<Integer> {
    private Integer clientID;
    private Integer bookID;
    private Date purchaseDate;
    private Integer price;
}
