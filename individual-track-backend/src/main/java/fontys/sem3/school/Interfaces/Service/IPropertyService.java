package fontys.sem3.school.Interfaces.Service;

import fontys.sem3.school.DTO.PropertyDTO.PropertyDTO;
import fontys.sem3.school.DTO.PropertyDTO.PropertyResponseDTO;
import fontys.sem3.school.domain.Property;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPropertyService {
//    boolean Create(PropertyDTO prop);
//    List<PropertyResponseDTO> GetAll();
//    boolean Delete(UUID id);
//    PropertyResponseDTO GetByID(UUID id);
//    List<PropertyResponseDTO> getUnapprovedProperties();
//
//    List<PropertyResponseDTO> getApprovedProperties();
//
//
//    Integer getUnapprovedPropertiesCount();
//    boolean Update(PropertyDTO propertyDTO);
//    List<PropertyResponseDTO> getPropertyByCityAndAndApproved(String city);
//
//    List<PropertyResponseDTO> getUnapprovedPropertyEntityByUser(UUID id);
//
//    List<PropertyResponseDTO> getApprovedPropertyEntityByUser(UUID id);
//
//    List<PropertyResponseDTO> getAllPropertyEntityByUser(UUID id);

    //new design

    UUID CreateProperty(Property prop);

    void DeleteProperty(UUID id);

    void UpdateProperty(Property property);

    List<Property> GetAllProperties();

    List<Property> GetPropertiesByCity(String city);

    List<Property> GetUnapprovedProperties();

    List<Property> GetAllPropertyEntityByUser(UUID id);

    List<Property> GetUnapprovedPropertyEntityByUser(UUID id);

    List<Property> GetApprovedPropertyEntityByUser(UUID id);

    Optional<Property> GetByUUID(UUID id);
}
