package fontys.sem3.school.DTO.PropertyDTO;

import fontys.sem3.school.enums.PropertyStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class UpdatePropertyRequestDTO {
    private UUID id ;
    @NotBlank
    @Min(value = 0, message = "Price should be a positive number")
    private  Integer price;
    @NotBlank(message = "Description should not be empty")
    private  String description;
    @NotBlank(message = "Location should not be empty")
    private  String location;
    @NotBlank(message = "Street name should not be empty")
    private  String streetName;
    @Min(value = 0, message = "House number should be a positive number")
    private  Integer houseNumber;
    @Min(value = 0, message = "Property size should be a positive number")
    private  Integer propertySize;
    @Enumerated(EnumType.STRING)
    private PropertyStatus approved;
    @NotBlank
    private  String available;

}
