package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.exceptions.CatNotFoundException;
import com.example.demo.models.Cat;
import com.example.demo.services.CatService;

@RestController
public class CatController {
	
	// Specify that Spring should find us an appropriate bean to use as a dependency with @Autowired
	// If there are multiple beans that could work, its typically best to manually configure them rather than autowire.
	@Autowired
	CatService catService;

	@GetMapping("/hello")
	public String testEndPoint() {
		return "Hello";
	}
	
	// This endpoint returns a cat. That cat will be written to the response body.
	// Spring Boot uses a tool called Jackson to turn Java objects into JSON by default. We can (and will) modify
	// Jackson's behavior by using annotations and/or the ObjectMapper class.
	@GetMapping("/randomcat")
	public Cat testCatEndpoint() {
		Cat cat = new Cat();
		cat.setCatId(3);
		cat.setName("biscuit");
		cat.setCuteness(55.5);
		return cat;
	}
	
	@GetMapping("/cats")
	public List<Cat> getAllCats() {
		return catService.getAll();
	}
	
	// Use curly braces in a specified path to accept "path variables." Useful for things like /cats/2.
	@GetMapping("/cats/{id}")
	public Cat getCatById(@PathVariable Integer id) {
		try {
			return catService.getById(id);
		} catch (CatNotFoundException e) {
			// Throw a ResponseStatusException to have Spring Web use an error HTTP status code, this is a shortcut that uses default behavior
			// Dont really need the last two arguments below. Just the status is fine.
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found", e);
			
			// To write our own response, we can return a ResponseEntity, or we can wait and see error handling using "Advice"
		}
	}
	
	// PostMapping will take post requests. We can access JSON sent in the request body with @RequestBody
	// this is also using Jackson under the hood
	@PostMapping("/cats")
	public Cat addNewCat(@RequestBody Cat cat) {
		return catService.create(cat);
	}
	
	// in our RESTful APIs, PUT specifies that the given resource should be placed at the url:
	@PutMapping("/cats/{id}")
	public Cat createOrUpdateCatWithId(@RequestBody Cat cat, @PathVariable Integer id) {
		// set the incoming Cat's id to the appropriate id based on url
		cat.setCatId(id);
		return catService.createOrUpdate(cat);
	}
	
	// PATCH is used to update, not create: This should throw and Exception when cat with id doesn't exist.
	@PatchMapping("/cats/{id}")
	public Cat updateCatWithId(@RequestBody Cat cat, @PathVariable Integer id) {
		cat.setCatId(id);
		return catService.createOrUpdate(cat);
	}
	
	// DELETE deletes a resource. Many APIs don't offer this option or only partially implement it
	// We can specify a response status code in a few ways, one easy one:
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping("/cats/{id}")
	public void deleteCat(@PathVariable Integer id) {
		catService.delete(id);
	}
}
