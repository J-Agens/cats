package com.example.demo.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  
//  @Query(value = "select u from User u where u.username = :username and u.password = :password", nativeQuery = true)
//  List<User> checkUsernamePassword(String username, String password);
//  
  // Password actually isn't a part of our cats example, see adam's code in repo
  List<User> findByUsernameAndPassword(String username, String password);
}
