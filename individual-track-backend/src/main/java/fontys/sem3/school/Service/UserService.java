package fontys.sem3.school.Service;
import fontys.sem3.school.DTO.UserDTO.UserDTO;
import fontys.sem3.school.Interfaces.Converters.IUserConverter;
import fontys.sem3.school.Interfaces.Data.IUserData;
import fontys.sem3.school.Interfaces.Service.IUserService;

import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.RoleEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService, UserDetailsService {


    private final IUserData userData;

   private PasswordEncoder passwordEncoder;
    private final IUserConverter userConverter;
//    @Autowired
//    public UserService(IUserData userData,PasswordEncoder passwordEncoder,IUserConverter userConverter){
//        this.userData = userData;
//        this.passwordEncoder = passwordEncoder;
//        this.userConverter = userConverter;
//    }


    @Override
    public boolean existingEmailCheck(String email) {
        return userData.existingEmailCheck(email);
    }
    @Override
    public RoleEntity saveRole(RoleEntity role){
        return userData.createRole(role);
    }
    @Override
    public void addRoleToUser(String email, String roleName){
        userData.addRoleToUser(email,roleName);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userData.RetrieveUserByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    //New arch
    @Override
    public UUID CreateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userData.CreateUser(user);

    }
    @Override
    public User RetrieveUserByEmail(String email){
        return userData.RetrieveUserByEmail(email);
    }
    @Override
    public void UpdateDetails(User user){
        this.userData.UpdateUserDetails(user);

    }



    //-----------------old design--------------

//    @Override
//    public boolean Create(UserDTO user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        this.userData.Create(userConverter.convertToEntity(user));
//        return true;
//    }
//    @Override
//    public boolean Create2(UserDTO user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        this.userData.Create(userConverter.convertToEntity(user));
//        return true;
//    }


//    @Override
//    public UserDTO getUserByEmail(String email){
//        return userConverter.convertToDTO(userData.GetByEmail(email));
//
//    }
//
//    @Override
//    public UserDTO getUserByID(UUID id) {
//        return userConverter.convertToDTO(userData.GetByID(id));
//    }
//
//    @Override
//    public List<UserDTO> getUsers(){
//        return userConverter.ListEntityToDTO(userData.GetAll());
//    }
//    @Override
//    public boolean Update(UserDTO user){
//        this.userData.Update(userConverter.convertToEntity(user));
//        return true;
//    }

}
