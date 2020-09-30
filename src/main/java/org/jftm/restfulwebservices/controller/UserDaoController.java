package org.jftm.restfulwebservices.controller;

import org.jftm.restfulwebservices.exception.UserNotFoundException;
import org.jftm.restfulwebservices.model.User;
import org.jftm.restfulwebservices.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserDaoController {
	
	@Autowired
	private UserDaoService service;

	@GetMapping("/dao/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping("/dao/users/{id}")
	public EntityModel<User>  retrieveUser(@PathVariable int id) {
		User user = service.findUser(id);
		if(user==null) {
			throw new UserNotFoundException("User with id: " + id + " not found");
		}
		
		//HATEOAS
		EntityModel<User> resource = EntityModel.of(user);
		
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@PostMapping("/dao/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/dao/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteUserById(id);
		if(user==null) {
			throw new UserNotFoundException("User with id: " + id + " not found");
		}
		
	}

}
