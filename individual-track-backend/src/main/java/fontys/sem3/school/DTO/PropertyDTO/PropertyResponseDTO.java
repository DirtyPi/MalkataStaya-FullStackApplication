package fontys.sem3.school.DTO.PropertyDTO;

import fontys.sem3.school.DTO.UserDTO.MiniUserDTO;
import fontys.sem3.school.enums.PropertyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponseDTO {
    private @Getter @Setter UUID id;

    private @Getter @Setter Integer price;

    private @Getter @Setter String description;

    private @Getter @Setter String location;

    private @Getter @Setter String streetName;

    private @Getter @Setter Integer houseNumber;

    private @Getter @Setter Integer propertySize;
    @Enumerated(EnumType.STRING)
    private @Getter @Setter PropertyStatus approved;
    private @Getter @Setter String available;
    private @Getter @Setter MiniUserDTO landLord;
}
