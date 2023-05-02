package fontys.sem3.school.Interfaces.jpa;

import fontys.sem3.school.enums.PropertyStatus;
import fontys.sem3.school.persistence.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface IJPAPropertyData extends JpaRepository<PropertyEntity, UUID> {
    List<PropertyEntity> getPropertyByLocationAndApproved(String location, PropertyStatus status);
    @Query(value = "select * from properties_s3 where approved=\"Unapproved\"",nativeQuery = true)
    List<PropertyEntity> getUnapprovedProperties();
    @Query(value = "select p.* from properties_s3 as p where p.land_lord_id = ?1 and p.approved=\"Unapproved\"",nativeQuery = true)
    List<PropertyEntity> getUnapprovedPropertyEntityByUser(String id);
    @Query(value = "select p.* from properties_s3 as p where p.land_lord_id = ?1 and p.approved=\"Approved\"",nativeQuery = true)
    List<PropertyEntity> getApprovedPropertyEntityByUser(String id);
    @Query(value = "select p.* from properties_s3 as p where p.land_lord_id = ?1 ",nativeQuery = true)
    List<PropertyEntity> getAllPropertyEntityByUser(String id);
//    PropertyEntity findPropertyEntityById(UUID id);
//    @Query(value = "select * from properties_s3 where approved=\"Approved\"",nativeQuery = true)
//    List<PropertyEntity> getApprovedProperties();
//    @Query(value = "select count(*) from properties_s3  where approved=\"Unapproved\"",nativeQuery = true)
//    Integer getUnapprovedPropertiesCount();
}
