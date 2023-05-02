package fontys.sem3.school;

import fontys.sem3.school.Conventors.PropertyConverter;
import fontys.sem3.school.Conventors.UserConverter;
import fontys.sem3.school.DTO.UserDTO.UpdateUserDetails;
import fontys.sem3.school.DTO.UserDTO.UserDTO;
import fontys.sem3.school.Exception.CreatePropertyException;
import fontys.sem3.school.Exception.DeletePropertyException;
import fontys.sem3.school.Exception.UpdatePropertyException;
import fontys.sem3.school.Exception.UpdateUserExeption;
import fontys.sem3.school.Interfaces.Converters.IUserConverter;
import fontys.sem3.school.Interfaces.Data.IPropertyData;
import fontys.sem3.school.Interfaces.Data.IUserData;
import fontys.sem3.school.Interfaces.Service.IPropertyService;
import fontys.sem3.school.Interfaces.Service.IUserService;
import fontys.sem3.school.Repository.UserData;
import fontys.sem3.school.Service.PropertyService;
import fontys.sem3.school.Service.UserService;
import fontys.sem3.school.domain.Property;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.enums.PropertyStatus;
import fontys.sem3.school.persistence.entity.PropertyEntity;
import fontys.sem3.school.persistence.entity.RoleEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTests {
    @Mock
    private IUserData userData;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private Collection<RoleEntity> roles;
    @BeforeEach
    public void setUp() {
        roles = new ArrayList<>();
        roles.add(new RoleEntity(UUID.randomUUID(), "ROLE_USER"));
        user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Hristo")
                .lastName("Tanchev")
                .email("hristo@fhict.nl")
                .phoneNumber("1234567890")
                .dateOfBirth("20/01/2000")
                .gender("male")
                .password("password")
                .roles(roles)
                .build();
    }
    @Test
    public void createUser_happyFlow() {
        // Arrange
        when(userData.existingEmailCheck(any())).thenReturn(false);
        UUID mockedUserId = UUID.randomUUID();
        when(userData.CreateUser(any())).thenReturn(mockedUserId);
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");

        // Act
        UUID result = userService.CreateUser(user);

        // Assert
        assertEquals(mockedUserId, result);
        assertEquals("encoded_password", user.getPassword());
    }
    @Test
    public void testCreateUser_BadData_ShouldReturnNull() {
        // Arrange
        User user = new User();
        user.setEmail("");
        user.setPassword("");
        user.setFirstName("");
        user.setLastName("");
        user.setPhoneNumber("");
        user.setGender("");
        user.setDateOfBirth("");
        // Act
        UUID result = userService.CreateUser(user);
        // Assert
        Assertions.assertNull(result);
    }

    @Test
    public void testExistingEmailCheck_EmailExist_ShouldReturnTrue() {
        // Arrange
        String email = "existing@email.com";
        Mockito.when(userData.existingEmailCheck(email)).thenReturn(true);
        // Act
        boolean result = userService.existingEmailCheck(email);
        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void testExistingEmailCheck_EmailNotExist_ShouldReturnFalse() {
        // Arrange
        String email = "notexisting@email.com";
        Mockito.when(userData.existingEmailCheck(email)).thenReturn(false);
        // Act
        boolean result = userService.existingEmailCheck(email);
        // Assert
        Assertions.assertFalse(result);
    }
    @Test
    public void updateDetails_happyFlow_success() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setFirstName("Hristo1");
        user.setLastName("Tanchev2");
        user.setEmail("hristo@fhict.nl");
        user.setPhoneNumber("1234567890");
        user.setDateOfBirth("20/01/2000");
        user.setGender("male");
        user.setPassword("password");

        // Act
        userService.UpdateDetails(user);
        // Assert
        verify(userData, times(1)).UpdateUserDetails(user);
    }

    @Test
    public void updateDetails_userNotFound_throwsException() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);
        user.setFirstName("Hristo1");
        user.setLastName("Tanchev2");
        user.setEmail(" ");
        user.setPhoneNumber("1234567890");
        user.setDateOfBirth("20/01/2000");
        user.setGender("male");
        user.setPassword("password");

        doThrow(new UpdateUserExeption("user not found"))
                .when(userData).UpdateUserDetails(user);
        // Act & Assert
        assertThrows(UpdateUserExeption.class, () -> userService.UpdateDetails(user));
        verify(userData, times(1)).UpdateUserDetails(user);
    }




}



