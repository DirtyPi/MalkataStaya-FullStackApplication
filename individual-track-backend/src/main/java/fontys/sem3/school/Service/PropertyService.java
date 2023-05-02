package fontys.sem3.school.Service;
import fontys.sem3.school.DTO.PropertyDTO.PropertyDTO;
import fontys.sem3.school.DTO.PropertyDTO.PropertyResponseDTO;
import fontys.sem3.school.Interfaces.Converters.IPropertyConverter;
import fontys.sem3.school.Interfaces.Data.IPropertyData;
import fontys.sem3.school.Interfaces.Service.IPropertyService;
import fontys.sem3.school.domain.Property;
import fontys.sem3.school.enums.PropertyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService implements IPropertyService {

    private final IPropertyData propertyData;


@Autowired
    public PropertyService(IPropertyData propertyData){

        this.propertyData = propertyData;

    }


    //new

    @Override
    public UUID CreateProperty(Property prop){
       return this.propertyData.CreateProperty(prop);
    }
    @Override
    public void DeleteProperty(UUID id) {
         this.propertyData.DeleteProperty(id);
    }

    @Override
    public void UpdateProperty(Property property) {
        this.propertyData.UpdateProperty(property);
    }
    @Override
    public List<Property> GetAllProperties() {
        return  propertyData.GetAllProperties();
    }
    @Override
    public List<Property> GetPropertiesByCity(String city){
        return propertyData.GetPropertiesByCity(city);
    }
    @Override
    public List<Property> GetUnapprovedProperties(){
       // return propertyData.GetUnapprovedProperties();
        return propertyData.GetAllProperties().stream()
                .filter(property -> property.getApproved() == PropertyStatus.Unapproved)
                .collect(Collectors.toList());
    }
    @Override
    public List<Property> GetAllPropertyEntityByUser(UUID id) {
        return propertyData.GetAllPropertyEntityByUser(id);
    }
    @Override
    public List<Property> GetUnapprovedPropertyEntityByUser(UUID id) {
        return propertyData.GetUnapprovedPropertyEntityByUser(id);
    }
    @Override
    public List<Property> GetApprovedPropertyEntityByUser(UUID id) {
        return propertyData.GetApprovedPropertyEntityByUser(id);
    }
    @Override
    public Optional<Property> GetByUUID(UUID id) {
        return this.propertyData.GetByUUID(id);
    }


    //----------------old design-------------------
    //    @Override
//    public boolean Create(PropertyDTO prop){
//        this.propertyData.Create(propertyConverter.convertToEntity(prop));
//        return true;
//    }
//    @Override
//    public List<PropertyResponseDTO> GetAll() {
//        return propertyConverter.ListEntityToDTO(this.propertyData.GetAll());
//    }
//    @Override
//    public boolean Delete(UUID id){
//        this.propertyData.Delete(id);
//        return true;
//    }
//    @Override
//    public PropertyResponseDTO GetByID(UUID id) {
//        return propertyConverter.convertToDTO(this.propertyData.GetByID(id));
//    }
//    @Override
//    public List<PropertyResponseDTO> getUnapprovedProperties(){
//
//        return propertyConverter.ListEntityToDTO(this.propertyData.getUnapprovedProperties());
//    }
//
//    @Override
//    public List<PropertyResponseDTO> getApprovedProperties(){
//        return propertyConverter.ListEntityToDTO(this.propertyData.getApprovedProperties());
//    }
//
//
//    @Override
//    public Integer getUnapprovedPropertiesCount(){
//        return this.propertyData.getUnapprovedPropertiesCount();
//    }
//    @Override
//    public boolean Update(PropertyDTO propertyDTO){
//        propertyData.Update(propertyConverter.convertToEntity(propertyDTO));
//        return true;
//    }
//    @Override
//    public List<PropertyResponseDTO> getPropertyByCityAndAndApproved(String city){
//    return propertyConverter.ListEntityToDTO(propertyData.getPropertyByCityAndAndApproved(city));
//    }
//    @Override
//    public List<PropertyResponseDTO> getUnapprovedPropertyEntityByUser(UUID id) {
//        return propertyConverter.ListEntityToDTO(propertyData.getUnapprovedPropertyEntityByUser(id));
//    }
//    @Override
//    public List<PropertyResponseDTO> getApprovedPropertyEntityByUser(UUID id) {
//        return propertyConverter.ListEntityToDTO(propertyData.getApprovedPropertyEntityByUser(id));
//    }
//    @Override
//    public List<PropertyResponseDTO> getAllPropertyEntityByUser(UUID id) {
//        return propertyConverter.ListEntityToDTO(propertyData.getAllPropertyEntityByUser(id));
//    }
}
