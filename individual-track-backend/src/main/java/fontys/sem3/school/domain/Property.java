package fontys.sem3.school.domain;

import fontys.sem3.school.enums.PropertyStatus;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Property {
    private UUID id ;
    private Integer price;
    private String description;
    private String location;
    private String streetName;
    private Integer houseNumber;
    private Integer propertySize;
    private PropertyStatus approved;
    private String available;
    private UUID landLord;


}
