package com.ajay.springjwt;

import com.ajay.springjwt.models.ERole;
import com.ajay.springjwt.models.Role;
import com.ajay.springjwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringBootSecurityJwtApplication {

	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	public void insertIntoRoles(){
		List<Role> roles= new ArrayList<>();
		roles.add(new Role(ERole.ROLE_USER));
		roles.add(new Role(ERole.ROLE_ADMIN));
		roles.add(new Role(ERole.ROLE_MODERATOR));
		List<Role> existing=roleRepository.findAll();
		if(existing.isEmpty()){
			roleRepository.saveAll(roles);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

}
