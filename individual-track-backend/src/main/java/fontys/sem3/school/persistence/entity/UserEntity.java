package fontys.sem3.school.persistence.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users_s3")
public class UserEntity {
    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Getter @Setter UUID id ;
    @Column(name="firstName")
    private @Getter @Setter String firstName;
    @Column(name="lastName")
    private @Getter @Setter String lastName;
    @Column(name="email")
    private @Getter @Setter String email;
    @Column(name="phoneNumber")
    private @Getter @Setter String phoneNumber;

    @Column(name="dateOfBirth")
    private @Getter @Setter String dateOfBirth;
    @Column(name="gender")
    private @Getter @Setter String gender;
    @Column(name="password")

    private @Getter @Setter String password;
    @ManyToMany(
            fetch=FetchType.EAGER
    )
    private @Getter @Setter Collection<RoleEntity> roles = new ArrayList<>();


}
