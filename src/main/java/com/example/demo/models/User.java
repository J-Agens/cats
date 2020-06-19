package com.example.demo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(schema = "cat", name = "users")

public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;
  @Column(name = "username")
  private String username;
  // we're leaving out the password
  // now I'm adding it
  @Column(name = "password")
  private String password;
  @OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE)
  @JsonIgnoreProperties({"owner"})
  private List<Cat> cats;

  public List<Cat> getCats() {
    return cats;
  }

  public void setCats(List<Cat> cats) {
    this.cats = cats;
    // Make each cat in the incoming list of catas have this user as the owner
    for(Cat c : cats) {
      c.setOwner(this);
    }
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", username=" + username + "]";
  }

}
