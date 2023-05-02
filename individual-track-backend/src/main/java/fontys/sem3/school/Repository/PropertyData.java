package fontys.sem3.school.Repository;
import fontys.sem3.school.Conventors.PropertyConverter;
import fontys.sem3.school.Exception.CreatePropertyException;
import fontys.sem3.school.Exception.DeletePropertyException;
import fontys.sem3.school.Exception.UpdatePropertyException;
import fontys.sem3.school.Interfaces.Converters.IPropertyConverter;
import fontys.sem3.school.Interfaces.Data.IPropertyData;
import fontys.sem3.school.Interfaces.jpa.IJPAPropertyData;
import fontys.sem3.school.domain.Property;
import fontys.sem3.school.enums.PropertyStatus;
import fontys.sem3.school.persistence.entity.PropertyEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class PropertyData implements IPropertyData {

    private final IJPAPropertyData jpaPropertyData;
    private final ModelMapper modelMapper;

    @Autowired
    public PropertyData(IJPAPropertyData jpaPropertyData, ModelMapper modelMapper){
        this.jpaPropertyData = jpaPropertyData;
        this.modelMapper = modelMapper;
    }


    //new

    @Override
    public UUID CreateProperty(Property prop){
       PropertyEntity propertyE = modelMapper.map(prop, PropertyEntity.class);
       try{
           return  jpaPropertyData.save(propertyE).getId();
       }catch (Exception e){
           throw new CreatePropertyException("Error while creating property");
       }
    }
    @Override
    public void DeleteProperty(UUID id) {
        try {
            jpaPropertyData.deleteById(id);
        }
        catch (Exception e){
            throw new DeletePropertyException("Property not deleted. Something went wrong");
        }
    }
    @Override
    public void UpdateProperty(Property property) {
        PropertyEntity propertyEntity = modelMapper.map(property, PropertyEntity.class);
        try {
            jpaPropertyData.save(propertyEntity);
        }
        catch (Exception e){
            throw new UpdatePropertyException("Error while updating property");
        }
    }
    @Override
    public List<Property> GetAllProperties() {
        return jpaPropertyData.findAll().stream().map(PropertyConverter::convert).toList();
    }

    @Override
    public List<Property> GetPropertiesByCity(String city){
        return jpaPropertyData.getPropertyByLocationAndApproved(city, PropertyStatus.Approved)
                .stream().map(PropertyConverter::convert).toList();
    }
    @Override
    public List<Property> GetUnapprovedProperties(){
        return jpaPropertyData.getUnapprovedProperties()
                .stream().map(PropertyConverter::convert).toList();
//        return jpaPropertyData.findAll()
//                .stream()
//                .filter(property -> property.getApproved() == PropertyStatus.Unapproved)
//                .map(PropertyConverter::convert)
//                .toList();
    }
    @Override
    public List<Property> GetAllPropertyEntityByUser(UUID id){
        return jpaPropertyData.getAllPropertyEntityByUser(id.toString())
                .stream().map(PropertyConverter::convert).toList();
    }
    @Override
    public List<Property> GetUnapprovedPropertyEntityByUser(UUID id){
        return jpaPropertyData.getUnapprovedPropertyEntityByUser(id.toString())
                .stream().map(PropertyConverter::convert).toList();
    }
    @Override
    public List<Property> GetApprovedPropertyEntityByUser(UUID id){
        return jpaPropertyData.getApprovedPropertyEntityByUser(id.toString())
                .stream().map(PropertyConverter::convert).toList();
    }
    @Override
    public Optional<Property> GetByUUID(UUID id) {
        return jpaPropertyData.findById(id).map(PropertyConverter::convert);
    }
    //------------------old design--------------------------

    //    @Override
//    public void Create(PropertyEntity prop){
//        jpaPropertyData.save(prop);
//    }
//    @Override
//    public List<PropertyEntity> GetAll() {
//        return jpaPropertyData.findAll();
//    }
//    @Override
//    public PropertyEntity GetByID(UUID id) {
//        return jpaPropertyData.findPropertyEntityById(id);
//    }
//    @Override
//    public void Delete(UUID id){
//        jpaPropertyData.deleteById(id);
//    }
//    @Override
//    public List<PropertyEntity> getUnapprovedProperties(){
//        return jpaPropertyData.getUnapprovedProperties();
//    }
//
//    @Override
//    public List<PropertyEntity> getApprovedProperties(){
//        return jpaPropertyData.getApprovedProperties();
//    }
//
//
//
//    @Override
//    public Integer getUnapprovedPropertiesCount(){
//        return jpaPropertyData.getUnapprovedPropertiesCount();
//    }
//    @Override
//    public void Update (PropertyEntity property){
//        PropertyEntity prop = GetByID(property.getId());
//        prop.setId(property.getId());
//        prop.setDescription(property.getDescription());
//        prop.setLocation(property.getLocation());
//        prop.setApproved(property.getApproved());
//        prop.setAvailable(property.getAvailable());
//        jpaPropertyData.save(prop);
//    }
//    @Override
//    public List<PropertyEntity> getPropertyByCityAndAndApproved(String city){
//        return jpaPropertyData.getPropertyByLocationAndApproved(city, PropertyStatus.Approved);
//    }
//    @Override
//    public List<PropertyEntity> getUnapprovedPropertyEntityByUser(UUID id){
//        return jpaPropertyData.getUnapprovedPropertyEntityByUser(id.toString());
//    }
//    @Override
//    public List<PropertyEntity> getApprovedPropertyEntityByUser(UUID id){
//        return jpaPropertyData.getApprovedPropertyEntityByUser(id.toString());
//    }
//    @Override
//    public List<PropertyEntity> getAllPropertyEntityByUser(UUID id){
//        return jpaPropertyData.getAllPropertyEntityByUser(id.toString());
//    }
}
