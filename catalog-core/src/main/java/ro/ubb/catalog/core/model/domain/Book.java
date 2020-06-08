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
public class Book extends BaseEntity<Integer> {
    private String name;
    private String author;
    private Date releaseDate;

}
