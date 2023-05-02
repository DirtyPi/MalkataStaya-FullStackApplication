package fontys.sem3.school.Conventors;


import fontys.sem3.school.DTO.UserDTO.UserDTO;
import fontys.sem3.school.Interfaces.Converters.IUserConverter;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter implements IUserConverter {

    private final ModelMapper modelMapper = new ModelMapper();
    public UserConverter(){}
//    @Override
//    public UserEntity convertToEntity(UserDTO user){
//        return modelMapper.map(user, UserEntity.class);
//    }
//    @Override
//    public UserDTO convertToDTO(UserEntity user){
//        return modelMapper.map(user,UserDTO.class);
//    }
//    @Override
//    public List<UserDTO> ListEntityToDTO(List<UserEntity> userEntityList) {
//        List<UserDTO> output = new ArrayList<>();
//        for(UserEntity user : userEntityList){
//            output.add(modelMapper.map(user,UserDTO.class));
//        }
//        return output;
//    }



}
