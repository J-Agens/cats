package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.CatNotFoundException;
import com.example.demo.exceptions.NotYetImplementedException;
import com.example.demo.models.Cat;

@Service
public class MockCatService implements CatService {
	
	private List<Cat> mockCats;
	private int nextId;
	
	public MockCatService() {
		super();
		this.mockCats = new ArrayList<Cat>();
		this.mockCats.add(new Cat(1, "alley", 100.00));
		this.mockCats.add(new Cat(2, "biscuit II", 180.00));
		this.mockCats.add(new Cat(3, "johnson", 10.50));
		this.mockCats.add(new Cat(4, "leia", 10000.00));
		this.mockCats.add(new Cat(5, "diez", 378900.00));
		this.mockCats.add(new Cat(6, "jeb", 100.00));
		this.nextId = 7;
	}

	@Override
	public List<Cat> getAll() {
		// TODO Auto-generated method stub
		return this.mockCats;
	}

	@Override
	public Cat getById(Integer id) {
		// TODO Auto-generated method stub
		Cat out = null;
		for (Cat c : this.mockCats) {
			if(c.getCatId().equals(id)) {
				out = c;
				break; // exit early
			}
		}
		if (out == null) {
			throw new CatNotFoundException("Cat with id " + id + "not found");
		}
		return out;
	}

	@Override
	public Cat create(Cat cat) {
		cat.setCatId(this.nextId);
		this.nextId++;
		this.mockCats.add(cat);
		return cat;
	}

	@Override
	public Cat update(Cat cat) {
		throw new NotYetImplementedException();
//		return null;
	}

	@Override
	public Cat createOrUpdate(Cat cat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

}
