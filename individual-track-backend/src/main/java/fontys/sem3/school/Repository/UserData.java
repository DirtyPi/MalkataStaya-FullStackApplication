package fontys.sem3.school.Repository;

import fontys.sem3.school.Exception.UpdateUserExeption;
import fontys.sem3.school.Interfaces.jpa.IJPARoleData;
import fontys.sem3.school.Interfaces.jpa.IJPAUserData;
import fontys.sem3.school.Interfaces.Data.IUserData;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.RoleEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional

public class UserData implements IUserData{

//    private static UUID NEXT_ID = UUID.randomUUID();
    private final IJPAUserData jpaUserData;
    private final IJPARoleData ijpaRoleData;
    private final ModelMapper modelMapper;
    @Autowired
    public UserData(IJPAUserData db, IJPARoleData ijpaRoleData, ModelMapper modelMapper){
        this.jpaUserData = db;
        this.ijpaRoleData = ijpaRoleData;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean existingEmailCheck(String email) {
        if(jpaUserData.countUsersByEmail(email) == 0){
            return false;
        }
        return  true;
    }
    @Override
    public void addRoleToUser(String email, String roleName) {
        UserEntity user = jpaUserData.getUserByEmail(email);
        RoleEntity role = ijpaRoleData.findByName(roleName);
            //    RoleEntity role = ijpaRoleData.findByName("ROLE_DEFAULT");
        user.getRoles().add(role);
    }
    @Override
    public RoleEntity createRole(RoleEntity role) {
        return ijpaRoleData.save(role);
    }


    //New arch
    @Override
    public UUID CreateUser(User user){
        UserEntity userEntity = UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .password(user.getPassword())
                .build();
        return jpaUserData.save(userEntity).getId();
    }
    @Override
    public User RetrieveUserByEmail(String email) {
        UserEntity userEntity = jpaUserData.getUserByEmail(email);
        return modelMapper.map(userEntity, User.class);
    }
    @Override
    public void UpdateUserDetails(User user){
        UserEntity updateUser =modelMapper.map(user, UserEntity.class);;
        try{
            jpaUserData.save(updateUser);
        }catch (Exception exception){throw new UpdateUserExeption("Error while updating property");}
    }

    //--------------------------- old design-----------------
//    @Override
//    public void Create(UserEntity user){
////        if(user.getId() == null){
////            user.setId(NEXT_ID);
////            return NEXT_ID;
////        }
//
//        jpaUserData.save(user);
//    }
//    @Override
//    public void Update(UserEntity user){
//        UserEntity updateUser = GetByID(user.getId());
//        if(updateUser != null){
//            updateUser.setId(user.getId());
//            updateUser.setEmail(user.getEmail());
//            updateUser.setDateOfBirth(user.getDateOfBirth());
//            updateUser.setFirstName(user.getFirstName());
//            updateUser.setLastName(user.getLastName());
//            updateUser.setGender(user.getGender());
//            updateUser.setPhoneNumber(user.getPhoneNumber());
//            jpaUserData.save(updateUser);
//        }
//    }
//    @Override
//    public List<UserEntity> GetAll() {
//        return jpaUserData.findAll();
//    }
//    @Override
//    public UserEntity  GetByID(UUID id) {
//        return jpaUserData.findUserEntityById(id);
//    }
//    @Override
//    public UserEntity GetByEmail(String email) {
//        return jpaUserData.getUserByEmail(email);
//    }


}
