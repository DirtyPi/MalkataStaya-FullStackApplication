package fontys.sem3.school.Conventors;
import fontys.sem3.school.DTO.PropertyDTO.PropertyResponseDTO;
import fontys.sem3.school.Interfaces.Data.IUserData;
import fontys.sem3.school.domain.Property;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import fontys.sem3.school.DTO.PropertyDTO.PropertyDTO;
import fontys.sem3.school.Interfaces.Converters.IPropertyConverter;
import fontys.sem3.school.persistence.entity.PropertyEntity;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PropertyConverter implements IPropertyConverter {
   private final ModelMapper modelMapper = new ModelMapper();
   private final IUserData userData;



    //New

    public static Property convert(PropertyEntity property) {
        return Property.builder()
                .id(property.getId())
                .price(property.getPrice())
                .description(property.getDescription())
                .location(property.getLocation())
                .streetName(property.getStreetName())
                .houseNumber(property.getHouseNumber())
                .approved(property.getApproved())
                .available(property.getAvailable())
                .landLord(property.getLandLord().getId())
                .build();
    }

    // ---------------------------- old design--------------
//
//    @Override
//    public PropertyEntity convertToEntity(PropertyDTO property){
//        PropertyEntity entity = modelMapper.map(property,PropertyEntity.class);
//        entity.setLandLord(userData.GetByID(property.getLandLord()));
//        return entity;
//        //return modelMapper.map(property,PropertyEntity.class);
//    }
//    @Override
//    public PropertyResponseDTO convertToDTO(PropertyEntity property){
//        return modelMapper.map(property,PropertyResponseDTO.class);
//    }
//    @Override
//    public List<PropertyResponseDTO> ListEntityToDTO(List<PropertyEntity> propertyEntityList) {
//        List<PropertyResponseDTO> output = new ArrayList<>();
//        for(PropertyEntity prop : propertyEntityList){
//            output.add(modelMapper.map(prop,PropertyResponseDTO.class));
//        }
//        return output;
//    }
}
