package com.example.demo.services;

import java.util.List;

import com.example.demo.models.Cat;

public interface CatService {
	
	List<Cat> getAll();
	
	Cat getById(Integer id);
	
	Cat create(Cat cat);
	
	Cat update(Cat cat);
	
	Cat createOrUpdate(Cat cat);
	
	void delete(Integer id);
	
}