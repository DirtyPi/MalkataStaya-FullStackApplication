package fontys.sem3.school.Interfaces.Data;

import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.RoleEntity;
import fontys.sem3.school.persistence.entity.UserEntity;

import java.util.List;
import java.util.UUID;


public interface IUserData {
//    void Create(UserEntity user);
//    List<UserEntity> GetAll();
//    UserEntity GetByID(UUID Id);
//    UserEntity GetByEmail(String email);
//    void Update(UserEntity user);
    boolean existingEmailCheck(String email);
    void addRoleToUser(String email, String roleName);
    RoleEntity createRole(RoleEntity role);
    //New arch
    UUID CreateUser(User user);
    User RetrieveUserByEmail(String email);
    void UpdateUserDetails(User user);
}
