package fontys.sem3.school.DTO.PropertyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPropertiesRespondDTO {
    private List<PropertyDTO> properties;
}
