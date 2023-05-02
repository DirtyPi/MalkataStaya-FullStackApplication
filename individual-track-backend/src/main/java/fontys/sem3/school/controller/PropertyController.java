package fontys.sem3.school.controller;
import fontys.sem3.school.DTO.PropertyDTO.*;
import fontys.sem3.school.DTO.UserDTO.UserDTO;
import fontys.sem3.school.Exception.CreatePropertyException;
import fontys.sem3.school.Exception.DeletePropertyException;
import fontys.sem3.school.Exception.UpdatePropertyException;
import fontys.sem3.school.Interfaces.Service.IPropertyService;
import fontys.sem3.school.Interfaces.Service.IUserService;
import fontys.sem3.school.domain.Property;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.enums.PropertyStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/properties")
@CrossOrigin(origins = "http://localhost:3000")
//, allowedHeaders = "*"
@AllArgsConstructor

public class PropertyController {
    private final IPropertyService PropertyService;
    private final IUserService userService;
    private ModelMapper modelMapper;

    //New arch code

    @PostMapping("/CreateProperty")
    public ResponseEntity<CreatePropertyResponse> CreateProperty(@RequestBody @Valid PropertyDTO request) {

        Property propertyConv = modelMapper.map(request, Property.class);

        CreatePropertyResponse createPropertyResponse = new CreatePropertyResponse();

        try{
            propertyConv.setApproved(PropertyStatus.Unapproved);
            propertyConv.setAvailable("Available room");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            User loggedInUser = this.userService.RetrieveUserByEmail(currentPrincipalName);
            propertyConv.setLandLord(loggedInUser.getId());
            createPropertyResponse.setId(PropertyService.CreateProperty(propertyConv));
        }catch (CreatePropertyException ex){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createPropertyResponse);

    }

    @DeleteMapping(value="/DeleteProperty/{id}")
    public ResponseEntity<Void> DeleteProperty(@PathVariable UUID id) {
        try{
            this.PropertyService.DeleteProperty(id);
        }catch (DeletePropertyException ex){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/UpdateProperty/{id}")
    public ResponseEntity<Void> UpdateProperty(@PathVariable("id") UUID id,
                                               @RequestBody @Valid UpdatePropertyRequestDTO request){
        request.setId(id);
        request.setApproved(PropertyStatus.Approved);
        Property property = modelMapper.map(request, Property.class);
        try{
            PropertyService.UpdateProperty(property);
        }
        catch (UpdatePropertyException ex){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("GetAllProperties")
    public ResponseEntity<GetPropertiesRespondDTO> getProperties(){
        List <Property> properties= PropertyService.GetAllProperties();
        if (properties.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            GetPropertiesRespondDTO response = new GetPropertiesRespondDTO(properties
                    .stream()
                    .map(property -> modelMapper.map(property, PropertyDTO.class))
                    .toList());
            return ResponseEntity.ok(response);
        }
    }
    @GetMapping("/Approved/{city}")
    public ResponseEntity<GetPropertiesRespondDTO>GetPropertiesByCity(@PathVariable String city){
        List<Property> props = this.PropertyService.GetPropertiesByCity(city);
        if (props.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            GetPropertiesRespondDTO response = new GetPropertiesRespondDTO(props
                    .stream()
                    .map(property -> modelMapper.map(property, PropertyDTO.class))
                    .toList());
            return ResponseEntity.ok(response);
        }
    }
    @GetMapping("/Unapproved")
    public ResponseEntity<GetPropertiesRespondDTO>GetUnapprovedProperties(){
        List<Property> list = this.PropertyService.GetUnapprovedProperties();
        if (list.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            GetPropertiesRespondDTO response = new GetPropertiesRespondDTO(list
                    .stream()
                    .map(property -> modelMapper.map(property, PropertyDTO.class))
                    .toList());
            return ResponseEntity.ok(response);

        }
    }
    @GetMapping("/AllProperties/user")
    public ResponseEntity<GetPropertiesRespondDTO> GetAllPropertyEntityByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedInUser = this.userService.RetrieveUserByEmail(currentPrincipalName);

        List<Property> prop = this.PropertyService.GetAllPropertyEntityByUser(loggedInUser.getId());
        if (prop.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            GetPropertiesRespondDTO response = new GetPropertiesRespondDTO(prop
                    .stream()
                    .map(property -> modelMapper.map(property, PropertyDTO.class))
                    .toList());
            return ResponseEntity.ok(response);

        }
    }

    @GetMapping("/Unapproved/user")
    public ResponseEntity<GetPropertiesRespondDTO> GetUnapprovedPropertyEntityByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedInUser = this.userService.RetrieveUserByEmail(currentPrincipalName);

        List<Property> prop = this.PropertyService.GetUnapprovedPropertyEntityByUser(loggedInUser.getId());
        if (prop.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            GetPropertiesRespondDTO response = new GetPropertiesRespondDTO(prop
                    .stream()
                    .map(property -> modelMapper.map(property, PropertyDTO.class))
                    .toList());
            return ResponseEntity.ok(response);

        }
    }

    @GetMapping("/Approved/user")
    public ResponseEntity<GetPropertiesRespondDTO> GetApprovedPropertyEntityByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedInUser = this.userService.RetrieveUserByEmail(currentPrincipalName);

        List<Property> prop = this.PropertyService.GetApprovedPropertyEntityByUser(loggedInUser.getId());
        if (prop.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            GetPropertiesRespondDTO response = new GetPropertiesRespondDTO(prop
                    .stream()
                    .map(property -> modelMapper.map(property, PropertyDTO.class))
                    .toList());
            return ResponseEntity.ok(response);

        }
    }

    @GetMapping("{id}")
    public ResponseEntity<PropertyResponseDTO> GetPropertyByUUID(@PathVariable(value = "id") UUID id) {
        final Optional<Property> propertyOptional = this.PropertyService.GetByUUID(id);
        if(propertyOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else{
            Optional<PropertyResponseDTO> property = Optional.of(modelMapper.map(propertyOptional.get(), PropertyResponseDTO.class));
            return ResponseEntity.ok().body(property.get());
        }
    }


            //--------------------Keep this one in case--------------------
//    @GetMapping()
//    public ResponseEntity<List<PropertyResponseDTO>>getApprovedProperties(){
//        List<PropertyResponseDTO> list = this.PropertyService.getApprovedProperties();
//        if(list.size()> 0)
//            return ResponseEntity.ok().body(list);
//        return ResponseEntity.ok().body(Collections.emptyList());
//
//    }

                 //-----------------------Old design-----------------------------
//    @PostMapping()
//    public ResponseEntity<PropertyDTO> createProperty(@RequestBody @Valid PropertyDTO request) {
//        request.setApproved(PropertyStatus.Unapproved);
//        request.setAvailable("Available room");
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
//        request.setLandLord(loggedInUser.getId());
//
//
//        PropertyService.Create(request);
//        String url = "Property" + "/" + request.getId();
//        URI uri = URI.create(url);
//        return new ResponseEntity(uri, HttpStatus.CREATED);
//    }
//    //@DeleteMapping("{id}")
////    @PathVariable(value = "id")
//    @PostMapping(value="/delete")
//    public ResponseEntity deleteProperty(@RequestBody UUID propertyId) {
//        if (this.PropertyService.Delete(propertyId)) {
//            return ResponseEntity.noContent().build();
//        } else {return new ResponseEntity("Not found", HttpStatus.NOT_FOUND);}
//    }
//    @GetMapping("{id}")
//    public ResponseEntity<PropertyResponseDTO> getProperty(@PathVariable(value = "id") UUID id) {
//        PropertyResponseDTO propertyDTO = this.PropertyService.GetByID(id);
//        if(propertyDTO!=null){return ResponseEntity.ok().body(propertyDTO);}
//        return new ResponseEntity("Not Found",HttpStatus.NOT_FOUND);
//    }
//    //@GetMapping()
//    public ResponseEntity<List<PropertyResponseDTO>> getAllProperties(){
//        List<PropertyResponseDTO> propertyDTOS = this.PropertyService.GetAll();
//        if(propertyDTOS.size()>0)
//            return ResponseEntity.ok().body(propertyDTOS);
//        return ResponseEntity.notFound().build();
//    }
//    @GetMapping("/Unapproved")
//    public ResponseEntity<List<PropertyResponseDTO>>getUnapprovedProperties(){
//        List<PropertyResponseDTO> list = this.PropertyService.getUnapprovedProperties();
//        if(list.size()> 0)
//            return ResponseEntity.ok().body(list);
//        return ResponseEntity.ok().body(Collections.emptyList());
//
//    }
//    //    @GetMapping("/getUByUser")
////    public ResponseEntity<List<PropertyResponseDTO>>getUnapprovedPropertiesByUser(){
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String currentPrincipalName = authentication.getName();
////        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
////        List<PropertyResponseDTO> properties = this.PropertyService.getUnapprovedPropertiesByUser(loggedInUser.getId());
////        if(properties != null){
////            return ResponseEntity.ok().body(properties);
////        }
////        return new ResponseEntity("Please provide a valid number.", HttpStatus.NOT_FOUND);
////    }
//    @GetMapping()
//    public ResponseEntity<List<PropertyResponseDTO>>getApprovedProperties(){
//        List<PropertyResponseDTO> list = this.PropertyService.getApprovedProperties();
//        if(list.size()> 0)
//            return ResponseEntity.ok().body(list);
//        return ResponseEntity.ok().body(Collections.emptyList());
//
//    }
//    //    @GetMapping("/getAByUser")
////    public ResponseEntity<List<PropertyResponseDTO>>getApprovedPropertiesByUser(){
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String currentPrincipalName = authentication.getName();
////        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
////        List<PropertyResponseDTO> properties = this.PropertyService.getApprovedPropertiesByUser(loggedInUser.getId());
////        if(properties != null){
////            return ResponseEntity.ok().body(properties);
////        }
////        return new ResponseEntity("Please provide a valid number.", HttpStatus.NOT_FOUND);
////    }
////    @GetMapping("/getByUser")
////    public ResponseEntity<List<PropertyResponseDTO>>getPropertiesByUser(){
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String currentPrincipalName = authentication.getName();
////        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
////        List<PropertyResponseDTO> properties = this.PropertyService.getPropertyEntityByUser(loggedInUser.getId());
////        if(properties != null){
////            return ResponseEntity.ok().body(properties);
////        }
////        return new ResponseEntity("Please provide a valid number.", HttpStatus.NOT_FOUND);
////    }
//    @GetMapping("/Unapproved/count")
//    public ResponseEntity<Integer> getUnapprovedPropertiesCount(){
//        if(this.PropertyService.getUnapprovedPropertiesCount() > 0)
//            return ResponseEntity.ok().body(this.PropertyService.getUnapprovedPropertiesCount());
//        return ResponseEntity.ok().body(0);
//    }
//    @PutMapping("/update")
//    public ResponseEntity<PropertyDTO> Update(@RequestBody PropertyDTO propertyDTO){
//        propertyDTO.setApproved(PropertyStatus.Approved);
//        if(this.PropertyService.Update(propertyDTO)){
//            String url = "Property" + "/" + propertyDTO.getId();
//            URI uri = URI.create(url);
//            return new ResponseEntity(uri, HttpStatus.CREATED);
//        }else{
//            return new ResponseEntity("Please provide a valid trip number.",HttpStatus.NOT_FOUND);
//        }
//
//    }
//    @GetMapping("/Approved/{city}")
//    public ResponseEntity<List<PropertyResponseDTO>>getPropertyByCityAndAndApproved(@PathVariable String city){
//        List<PropertyResponseDTO> props = this.PropertyService.getPropertyByCityAndAndApproved(city);
//        if(props != null){
//            return ResponseEntity.ok().body(props);
//        }
//        return new ResponseEntity("Please provide a valid number.", HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping("/Approved/user")
//    public ResponseEntity<List<PropertyResponseDTO>> getApprovedPropertyEntityByUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
//        List<PropertyResponseDTO> prop = this.PropertyService.getApprovedPropertyEntityByUser(loggedInUser.getId());
//        if(prop !=null){
//            return ResponseEntity.ok().body(prop);
//        }
//
//        return new ResponseEntity("Please provide a valid number.", HttpStatus.NOT_FOUND);
//    }
//    @GetMapping("/Unapproved/user")
//    public ResponseEntity<List<PropertyResponseDTO>> getUnapprovedPropertyEntityByUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
//        List<PropertyResponseDTO> prop = this.PropertyService.getUnapprovedPropertyEntityByUser(loggedInUser.getId());
//        if(prop !=null){
//            return ResponseEntity.ok().body(prop);
//        }
//        return new ResponseEntity("Please provide a valid number.", HttpStatus.NOT_FOUND);
//    }
//    @GetMapping("/AllProperties/user")
//    public ResponseEntity<List<PropertyResponseDTO>> getAllPropertyEntityByUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
//        List<PropertyResponseDTO> prop = this.PropertyService.getAllPropertyEntityByUser(loggedInUser.getId());
//        if(prop !=null){
//            return ResponseEntity.ok().body(prop);
//        }
//        return new ResponseEntity("Please provide a valid number.", HttpStatus.NOT_FOUND);
//    }
//


}
