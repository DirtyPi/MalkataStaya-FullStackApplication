package fontys.sem3.school.DTO.UserDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import fontys.sem3.school.persistence.entity.RoleEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;

@Data
public class UpdateUserDetails {
    private @Getter
    @Setter UUID id ;
    @NotBlank
    @Size(min = 1, max = 100)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 100)
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 10, max = 15)
    private String phoneNumber;
    @NotBlank
    private String dateOfBirth;
    @NotBlank
    @Size(max = 6)
    private String gender;
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, message = "Password should have min 6 characters")
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<RoleEntity> roles ;
}
