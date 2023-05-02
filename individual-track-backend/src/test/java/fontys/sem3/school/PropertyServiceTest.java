package fontys.sem3.school;

import fontys.sem3.school.Conventors.PropertyConverter;
import fontys.sem3.school.DTO.PropertyDTO.PropertyDTO;
import fontys.sem3.school.DTO.PropertyDTO.PropertyResponseDTO;
import fontys.sem3.school.Exception.CityNotFoundException;
import fontys.sem3.school.Exception.CreatePropertyException;
import fontys.sem3.school.Exception.DeletePropertyException;
import fontys.sem3.school.Interfaces.Converters.IPropertyConverter;
import fontys.sem3.school.Interfaces.Data.IPropertyData;
import fontys.sem3.school.Interfaces.Data.IUserData;
import fontys.sem3.school.Interfaces.Service.IPropertyService;
import fontys.sem3.school.Service.PropertyService;
import fontys.sem3.school.domain.Property;
import fontys.sem3.school.domain.User;
import fontys.sem3.school.enums.PropertyStatus;
import fontys.sem3.school.persistence.entity.PropertyEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static java.util.UUID.fromString;
import static org.hamcrest.collection.IsEmptyCollection.empty;


import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@ActiveProfiles("test")
@SpringBootTest
public class PropertyServiceTest {

        @Mock
        private IPropertyData mockPropertyData;

        private PropertyService propertyService;
        Property property;
        Property property2;
        Property property3;
        private List<Property> mockProperties;
        @BeforeEach
        public void setUp() {
            propertyService = new PropertyService(mockPropertyData);
            property = Property.builder()
                    .id(fromString("c6e3c8d8-5672-419c-8354-fcc3dc1c2cfc"))
                    .price(1000)
                    .description("Nice property")
                    .location("Eind")
                    .streetName("Main St")
                    .houseNumber(123)
                    .propertySize(1000)
                    .approved(PropertyStatus.Approved)
                    .available("Unavailable")
                    .landLord(fromString("dc7f6298-75a7-4cd2-ab7c-311616f97d8c"))
                    .build();
            property2 = Property.builder()
                    .id(fromString("9e648c2f-781b-4f6c-9911-feca48faebf9"))
                    .price(1000)
                    .description("Nice property")
                    .location("New York")
                    .streetName("Main St")
                    .houseNumber(123)
                    .propertySize(1000)
                    .approved(PropertyStatus.Unapproved)
                    .available("Available")
                    .landLord(fromString("dc7f6298-75a7-4cd2-ab7c-311616f97d8c"))
                    .build();
            property3 = Property.builder()
                    .id(fromString("04840ac9-036d-4d6e-8739-2b13fa00e13f"))
                    .price(1000)
                    .description("Nice property")
                    .location("New York")
                    .streetName("Main St")
                    .houseNumber(123)
                    .propertySize(1000)
                    .approved(PropertyStatus.Unapproved)
                    .available("Available")
                    .landLord(fromString("dc7f6298-75a7-4cd2-ab7c-311616f97d8c"))
                    .build();
            mockProperties = Arrays.asList(property, property2);
        }

        @Test
        public void whenCreateProperty_thenReturnUUID() {
            // Arrange
            Property property = new Property();
            UUID expectedId = UUID.randomUUID();
            when(mockPropertyData.CreateProperty(any(Property.class))).thenReturn(expectedId);
            // Act
            UUID actualId = propertyService.CreateProperty(property);
            // Assert
            assertEquals(expectedId, actualId);
        }
        @Test
        void whenValidProperty_thenCreateProperty() {
            // Arrange
            when(mockPropertyData.CreateProperty(property)).thenReturn(property.getId());

            // Act
            UUID result = propertyService.CreateProperty(property);

            // Assert
            assertEquals(property.getId(), result);
            verify(mockPropertyData, times(1)).CreateProperty(property);
        }
        @Test
        public void testCreateProperty_GivenInvalidProperty_ShouldThrowException() {
            // Arrange
            Property invalidProperty = new Property();
            doThrow(new CreatePropertyException("Error while creating property")).when(mockPropertyData).CreateProperty(invalidProperty);
            // Act
            try {
                propertyService.CreateProperty(invalidProperty);
                fail();
            } catch (CreatePropertyException e) {
                // Assert
                assertEquals("Error while creating property", e.getMessage());
            }
        }
        @Test
        void whenInvalidProperty_thenCreatePropertyThrowsException() {
            // Arrange
            when(mockPropertyData.CreateProperty(property)).thenThrow(new CreatePropertyException("Error while creating property"));

            // Act and Assert
            assertThrows(CreatePropertyException.class, () -> propertyService.CreateProperty(property));
            verify(mockPropertyData, times(1)).CreateProperty(property);
        }

        @Test
        void whenValidId_thenDeletePropertySuccess() {
            // Arrange
//        UUID id = UUID.randomUUID();
            UUID id = fromString("c6e3c8d8-5672-419c-8354-fcc3dc1c2cfc");
            doNothing().when(mockPropertyData).DeleteProperty(id);
            // Act
            propertyService.DeleteProperty(id);
            // Assert
            verify(mockPropertyData, times(1)).DeleteProperty(id);
        }
        @Test
        void whenInvalidId_thenDeletePropertyThrowsException() {
            // Arrange
            UUID invalidId = UUID.randomUUID();
            doThrow(new DeletePropertyException("Invalid id")).when(mockPropertyData).DeleteProperty(invalidId);
            // Act and Assert
            assertThrows(DeletePropertyException.class, () -> propertyService.DeleteProperty(invalidId));
        }
        @Test
        public void whenValidInput_thenUpdateProperty() {
            // Arrange

            when(mockPropertyData.GetByUUID(property.getId())).thenReturn(Optional.of(property));

            // Act
            propertyService.UpdateProperty(property);

            // Assert
            verify(mockPropertyData, times(1)).UpdateProperty(property);
        }

        @Test
        public void whenInvalidInput_thenUpdatePropertyThrowsException() {
            // Arrange
            Property invalidProperty = new Property();
            property.setId(UUID.randomUUID());
            property.setPrice(-1000);
            property.setDescription("Nice property");
            property.setLocation("Eindhoven");
            property.setStreetName("streetzadsd");
            property.setHouseNumber(-100);
            property.setPropertySize(-2000);
            property.setApproved(PropertyStatus.Approved);
            property.setAvailable("Available");
            property.setLandLord(UUID.randomUUID());;
            doThrow(new IllegalArgumentException("Invalid Property input"))
                    .when(mockPropertyData).UpdateProperty(invalidProperty);
            // Act and Assert
            assertThrows(IllegalArgumentException.class, () -> propertyService.UpdateProperty(invalidProperty));
        }
        @Test
        public void getAllProperties_happyFlow() {
            // Arrange
            when(mockPropertyData.GetAllProperties()).thenReturn(mockProperties);

            // Act
            List<Property> result = propertyService.GetAllProperties();

            // Assert
            assertEquals(mockProperties, result);
        }
    @Test
    public void getAllProperties_badFlow() {
        // Arrange
        when(mockPropertyData.GetAllProperties()).thenReturn(Collections.emptyList());

        // Act
        List<Property> result = propertyService.GetAllProperties();

        // Assert
        assertTrue(result.isEmpty());
    }
    @Test
    void getPropertiesByCity_happyFlow_returnsPropertiesByCity() {
        // Arrange
        String city = "Eindhoven";
        List<Property> mockProperties = Arrays.asList(
                Property.builder().id(UUID.randomUUID()).location(city).build(),
                Property.builder().id(UUID.randomUUID()).location(city).build()
        );
        when(mockPropertyData.GetPropertiesByCity(city)).thenReturn(mockProperties);
        // Act
        List<Property> result = propertyService.GetPropertiesByCity(city);
        // Assert
        assertEquals(result, mockProperties);
    }
    @Test
    void getPropertiesByCity_badFlow_throwsCityNotFoundException() {
        // Arrange
        String city = "Aindhoven";
        when(mockPropertyData.GetPropertiesByCity(city)).thenThrow(new CityNotFoundException("City not found"));
        // Act and Assert
        assertThrows(CityNotFoundException.class, () -> propertyService.GetPropertiesByCity(city));
    }
    @Test
    public void getUnapprovedProperties_happyFlow() {
        // Arrange
        List<Property> expectedProperties = Arrays.asList(property2,property3);
        when(mockPropertyData.GetAllProperties()).thenReturn(Arrays.asList( property2,property3));

        // Act
        List<Property> result = propertyService.GetUnapprovedProperties();

        // Assert
        assertEquals(expectedProperties, result);
        verify(mockPropertyData).GetAllProperties();
    }


    @Test
    public void getUnapprovedProperties_badFlow() {
        // Arrange
        List<Property> allProperties = Arrays.asList(property);
        when(mockPropertyData.GetUnapprovedProperties()).thenReturn(allProperties);

        // Act
        List<Property> result = propertyService.GetUnapprovedProperties();

        // Assert
        assertEquals(0, result.size());
    }
    @Test
    public void getAllPropertyEntityByUser_happyFlow() {
        // Arrange
        List<Property> expectedProperties = Arrays.asList(property2,property3);
        when(mockPropertyData.GetAllPropertyEntityByUser(UUID.fromString("3baaf9c2-272d-45e8-a736-29c1f191ec26"))).thenReturn(expectedProperties);

        // Act
        List<Property> result = propertyService.GetAllPropertyEntityByUser(UUID.fromString("3baaf9c2-272d-45e8-a736-29c1f191ec26"));

        // Assert
        assertEquals(expectedProperties, result);
        verify(mockPropertyData).GetAllPropertyEntityByUser(UUID.fromString("3baaf9c2-272d-45e8-a736-29c1f191ec26"));
    }
    @Test
    public void getAllPropertyEntityByUser_badFlow() {
        // Arrange

        when(mockPropertyData.GetAllPropertyEntityByUser(UUID.fromString("3baaf9c2-272d-45e8-a736-29c1f191ec26"))).thenThrow(new IllegalArgumentException());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> propertyService.GetAllPropertyEntityByUser(UUID.fromString("3baaf9c2-272d-45e8-a736-29c1f191ec26")));
        verify(mockPropertyData).GetAllPropertyEntityByUser(UUID.fromString("3baaf9c2-272d-45e8-a736-29c1f191ec26"));
    }

    @Test
    void getByUUID_happyFlow() {
        // Arrange
        String propertyUUID =("c6e3c8d8-5672-419c-8354-fcc3dc1c2cfc");
        UUID uuid = UUID.fromString(propertyUUID);
        when(mockPropertyData.GetByUUID(uuid)).thenReturn(Optional.of(property));

        // Act
        Optional<Property> result = propertyService.GetByUUID(uuid);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(property, result.get());
        verify(mockPropertyData).GetByUUID(uuid);
    }
    @Test
    public void getByUUID_badFlow() {
        // Arrange
        String propertyUUID =("c6e3c8d8-5672-419c-8354-fcc3dc1c2cfc");
        UUID uuid = UUID.fromString(propertyUUID);
        when(mockPropertyData.GetByUUID(uuid)).thenReturn(Optional.empty());

        // Act
        Optional<Property> result = propertyService.GetByUUID(uuid);

        // Assert
        assertFalse(result.isPresent());
        verify(mockPropertyData).GetByUUID(uuid);
    }
}





