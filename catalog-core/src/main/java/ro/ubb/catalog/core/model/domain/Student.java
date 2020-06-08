package ro.ubb.catalog.core.model.domain;

import lombok.*;

import javax.persistence.Entity;

/**
 * Created by radu.
 */

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Student extends BaseEntity<Integer>{
    private String serialNumber;
    private String name;
    private int groupNumber;
}
