package fontys.sem3.school.controller;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import fontys.sem3.school.DTO.UserDTO.*;
import fontys.sem3.school.Exception.UpdateUserExeption;
import fontys.sem3.school.Interfaces.Converters.IUserConverter;
import fontys.sem3.school.Interfaces.Service.IUserService;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.persistence.entity.RoleEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
//@AllArgsConstructor
@RequiredArgsConstructor
public class UserController {

private final IUserService userService;
private final IUserConverter converter;
    private final ModelMapper modelMapper = new ModelMapper();
//    @GetMapping("/count")
//    public ResponseEntity<UserDTO> GetUserCount(){
//        List<UserDTO> users = this.userService.getUsers();
//        return new ResponseEntity(users.size(),HttpStatus.FOUND);
//    }

//New arch

    @PostMapping("/registerUser")
    public ResponseEntity<CreateUserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO requestDTO) {
        if(userService.existingEmailCheck(requestDTO.getEmail())){
            String s = "An user with the email:" + requestDTO.getEmail() + " already exists.";
            return new ResponseEntity(s,HttpStatus.CONFLICT);}
        else{
            // Convert the request DTO to a domain object
            User user = modelMapper.map(requestDTO, User.class);

            CreateUserResponseDTO responseDTO = CreateUserResponseDTO.builder()
                    .id(userService.CreateUser(user))
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }
    }
    @GetMapping("/MyPersonalDetails")
    public ResponseEntity<UserDTO> GetMyPersonalDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        final User user = this.userService.RetrieveUserByEmail(currentPrincipalName);
        if(user!=null){
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return ResponseEntity.ok().body(userDTO);
        }
        return new ResponseEntity("Please provide a valid user number.",HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> UpdateUserDetails(@PathVariable("id") final UUID id, @RequestBody @Valid UpdateUserDetails request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = this.userService.RetrieveUserByEmail(currentPrincipalName);
        if (user != null) {
            user = modelMapper.map(request, User.class);
            try {
                userService.UpdateDetails(user);
            } catch (UpdateUserExeption e) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.noContent().build();
    }




    @PostMapping("/role/create")
    public ResponseEntity<RoleEntity>createRole(@RequestBody RoleEntity role){
        String url = "user" + "/" + role.getName();
        URI uri = URI.create(url);
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }
    //keep
    @PostMapping("/role")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        String url = "user" + "/" + form.getRoleName();
        userService.addRoleToUser(form.getEmail(),form.getRoleName());
        return new ResponseEntity("Please provide a valid user number.", HttpStatus.FOUND);
    }
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorisationHeader = request.getHeader(AUTHORIZATION);
        if(authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorisationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                User user = userService.RetrieveUserByEmail(email);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_msg", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("Refresh token not found");
        }
    }
    @GetMapping("/check")
    public ResponseEntity<?>checkTokenStatus(){

        return ResponseEntity.ok().body("");
    }

    @GetMapping("/admin")
    public ResponseEntity<?>checkTokenStatusAdmin(){
        return ResponseEntity.ok().body("");
    }



    //----------------------- old design ------------------------
//       @PostMapping("/register")
//       public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO ){
//           if(userService.existingEmailCheck(userDTO.getEmail())){
//               String s = "An user with the email:" + userDTO.getEmail() + " already exists.";
//               return new ResponseEntity(s,HttpStatus.CONFLICT);
//           }else{
//               userService.Create2(userDTO);
//               String url = "Welcome"+ "/" +userDTO.getId();
//               URI uri = URI.create(url);
//               return new ResponseEntity(uri, HttpStatus.CREATED);
//           }
//       }
//    //keep
//
//    @GetMapping("/myDetails")
//    public ResponseEntity<UserDTO> GetUserDetails(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        UserDTO user = this.userService.getUserByEmail(currentPrincipalName);
//        if(user!=null){
//            return ResponseEntity.ok().body(user);
//        }
//        return new ResponseEntity("Please provide a valid user number.",HttpStatus.NOT_FOUND);
//    }
//    @PutMapping()
//    public ResponseEntity<UserDTO> UpdateUser(@Valid @RequestBody UserDTO user){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        UserDTO loggedInUser = this.userService.getUserByEmail(currentPrincipalName);
//        if(userService.existingEmailCheck(user.getEmail()) && !Objects.equals(user.getEmail(), loggedInUser.getEmail())){
//            String s = "An user with the email:" + user.getEmail() + " already exists.";
//            return new ResponseEntity(s,HttpStatus.CONFLICT);
//        }
//        else{
//            this.userService.Update(user);
//            String url = "user" + "/" + user.getId();
//            URI uri = URI.create(url);
//            return new ResponseEntity(uri,HttpStatus.CREATED);
//        }
//    }
}




