package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

// It's good practice to make interfaces for your servcies, but not strictly necessary. Just less
// modular
@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getById(Integer id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  public User create(User user) {
    user.setUserId(0);
    return userRepository.save(user);
  }

  public User update(User user) {
    Optional<User> existingUser = userRepository.findById(user.getUserId());
    if (existingUser.isPresent()) {
      return userRepository.save(user);
    } else {
      throw new UserNotFoundException();
    }
  }

  public User createOrUpdate(User user) {
    return userRepository.save(user);
  }
  
  public Boolean checkCredentials(String username, String password) {
    // we just check if this username and password exist in the DB
    return userRepository.findByUsernameAndPassword(username, password).size() > 0;
//    return userRepository.checkUsernamePassword(username, password).size() > 0;
  }

  public void delete(Integer id) {
    userRepository.deleteById(id);
  }
}
