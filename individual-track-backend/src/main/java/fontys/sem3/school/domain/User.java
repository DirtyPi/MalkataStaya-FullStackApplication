package fontys.sem3.school.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import fontys.sem3.school.persistence.entity.RoleEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private UUID id ;
    private  String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String gender;
    private String password;
    private Collection<RoleEntity> roles = new ArrayList<>();
}
