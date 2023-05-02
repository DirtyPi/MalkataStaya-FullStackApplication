package fontys.sem3.school.Interfaces.Service;

import fontys.sem3.school.DTO.UserDTO.UserDTO;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.RoleEntity;
import fontys.sem3.school.persistence.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface IUserService {
   // boolean Create(UserDTO user);
   // boolean Create2(UserDTO user);
   // UserDTO getUserByEmail(String email);
   // UserDTO getUserByID(UUID id);
   // List<UserDTO> getUsers();
    boolean existingEmailCheck(String email);

    RoleEntity saveRole(RoleEntity role);

    void addRoleToUser(String email, String roleName);
   // boolean Update(UserDTO user);

    UUID CreateUser(User user);

    User RetrieveUserByEmail(String email);

    void UpdateDetails(User user);
}
