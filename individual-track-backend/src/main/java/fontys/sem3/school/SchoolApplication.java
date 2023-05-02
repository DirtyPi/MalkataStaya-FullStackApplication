package fontys.sem3.school;

import fontys.sem3.school.DTO.UserDTO.UserDTO;
import fontys.sem3.school.Interfaces.Service.IUserService;
import fontys.sem3.school.Repository.UserData;
import fontys.sem3.school.Service.UserService;
import fontys.sem3.school.persistence.entity.RoleEntity;
import fontys.sem3.school.persistence.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveRole(new RoleEntity(UUID.fromString("2b53b9be-b82b-4a35-ae98-5e03ec51c541"),"ROLE_ADMIN"));
//			userService.saveRole(new RoleEntity(UUID.fromString("81b83fb2-0727-4404-8cf2-1f73feeb2980"),"ROLE_DEFAULT"));
//
//
//			userService.Create2(new UserDTO(UUID.fromString("3baaf9c2-272d-45e8-a736-29c1f191ec26"),"Bob","NotTanchev","adminTest@mt.sz","+359841221345","10-02-2000","Male","password",null));
//			userService.Create2(new UserDTO(UUID.fromString("90d87f90-f1a3-4e92-863d-5468247dcb14"),"Hristo","Tanchev","userTest@mt.sz","+359863421345","10-02-2000","Male","password",null));
//
//
//			userService.addRoleToUser("userTest@mt.sz","ROLE_DEFAULT");
//			userService.addRoleToUser("adminTest@mt.sz","ROLE_ADMIN");
//		};
//	}



}

