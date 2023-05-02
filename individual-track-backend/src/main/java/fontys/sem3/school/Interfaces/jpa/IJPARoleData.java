package fontys.sem3.school.Interfaces.jpa;

import fontys.sem3.school.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IJPARoleData extends JpaRepository<RoleEntity, UUID> {
    RoleEntity findByName(String name);
}
