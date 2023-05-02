package fontys.sem3.school.Interfaces.Data;

import fontys.sem3.school.domain.Property;
import fontys.sem3.school.persistence.entity.PropertyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPropertyData {

//    void Create(PropertyEntity prop);
//    List<PropertyEntity> GetAll();
//    PropertyEntity GetByID(UUID id);
//    void Delete(UUID id);
//    List<PropertyEntity> getUnapprovedProperties();
//
//    List<PropertyEntity> getApprovedProperties();
//
//    Integer getUnapprovedPropertiesCount();
//    void Update(PropertyEntity p);
//    List<PropertyEntity> getPropertyByCityAndAndApproved(String city);
//    List<PropertyEntity> getUnapprovedPropertyEntityByUser(UUID id);
//    List<PropertyEntity> getApprovedPropertyEntityByUser(UUID id);
//    List<PropertyEntity> getAllPropertyEntityByUser(UUID id);


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
