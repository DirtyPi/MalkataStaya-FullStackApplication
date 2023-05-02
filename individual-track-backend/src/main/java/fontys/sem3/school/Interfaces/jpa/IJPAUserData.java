package fontys.sem3.school.Interfaces.jpa;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IJPAUserData extends JpaRepository<UserEntity, UUID> {
    UserEntity findUserEntityById(UUID id);
    UserEntity getUserByEmail(String email);

    @Query(value = "select count(*) from users_s3 where email = ?1",nativeQuery = true)
    int countUsersByEmail(String email);
}
