package com.stacksimplify.restservices.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repository.OrderRepository;
import com.stacksimplify.restservices.repository.UserRepository;

@RestController
@RequestMapping(value="/hateoas/users")
public class OrderHateoasController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	//get All Orders for a user
	@GetMapping("/{userid}/orders")
	public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
		Optional<User> userOptional = userRepository.findById(userid);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		
		return userOptional.get().getOrders();
		
	}
	/*public CollectionModel<EntityModel<Order>> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {
		
		Optional<User> userOptional = userRepository.findById(userid);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("User Not Found");
		}
		
		List<Order> allorders = userOptional.get().getOrders();
		
		List<EntityModel<Order>> result = new ArrayList<>();
		
		for(Order order: allorders) {
			EntityModel entityModel = EntityModel.of(order);
			//Link selflink = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
			//entityModel.add(selflink);
			
			result.add(entityModel);
		}
		
		CollectionModel<EntityModel<Order>> finalUsers = CollectionModel.of(result);
		return finalUsers;
	}*/
}
