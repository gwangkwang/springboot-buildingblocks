package com.stacksimplify.restservices.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repository.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	//getAllUsers Method
	@GetMapping
	public CollectionModel<EntityModel<User>> getAllUsers() throws UserNotFoundException {
		
		List<EntityModel<User>> result = new ArrayList<>();
		
		List<User> allusers = userService.getAllUsers();
		
		for(User user: allusers) {
			EntityModel entityModel = EntityModel.of(user);
			Long userid = user.getId();
			Link selflink =  WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			entityModel.add(selflink);
			
			//Relationship link with getAllOrders
			List<Order> orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userid);
			Link orderslink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
			entityModel.add(orderslink);
			//Relationship link with getAllOrders
			//CollectionModel<EntityModel<Order>> orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userid);
			
			//Link orderslink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
			//entityModel.add(orderslink);
			
			result.add(entityModel);
		}
		//Self link for getAllUsers
		Link selflinkgetAllUsers = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
		CollectionModel finalUsers = CollectionModel.of(result, selflinkgetAllUsers);
		return finalUsers;
	}
	
	//getUserById
	@GetMapping("/{id}")
	public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			Optional<User> userOptional = userService.getUserById(id);
			User user = userOptional.get();
			Long userid = user.getId();
			Link selflink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
			user.add(selflink);
			EntityModel<User> finalUser = EntityModel.of(user);
			return finalUser;
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
