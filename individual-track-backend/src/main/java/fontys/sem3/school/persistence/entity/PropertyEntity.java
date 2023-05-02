package fontys.sem3.school.persistence.entity;
import fontys.sem3.school.enums.PropertyStatus;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


//@Data
//@Builder
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name="properties_s3")
public class PropertyEntity {
    @Id
    //@Column(name="ID")
    @Type(type="org.hibernate.type.UUIDCharType")
    private  @Getter @Setter UUID id;
    //@Column(name="price")
    private  @Getter @Setter Integer price;
    //@Column(name="description")
    private  @Getter @Setter String description;
    //@Column(name="location")
    private  @Getter @Setter String location;
    //@Column(name="streetName")
    private  @Getter @Setter String streetName;
    //@Column(name="houseNumber")
    private  @Getter @Setter Integer houseNumber;
    //@Column(name="propertySize")
    private  @Getter @Setter Integer propertySize;
    @Enumerated(EnumType.STRING)
    private  @Getter @Setter PropertyStatus approved;
    @ManyToOne
   // @Column(name="land_lord_id")
    private @Getter @Setter UserEntity landLord;
    private @Getter @Setter String available;



}
