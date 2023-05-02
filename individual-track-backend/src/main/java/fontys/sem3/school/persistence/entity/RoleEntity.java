package fontys.sem3.school.persistence.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="role")
public class RoleEntity {
    @Id
    @Column(name="ID")
    @Type(type="org.hibernate.type.UUIDCharType")
    private @Getter @Setter UUID id;
    private @Getter @Setter String name;
}
