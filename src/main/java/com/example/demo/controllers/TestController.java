package com.example.demo.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.models.Cat;
import com.example.demo.models.User;
import com.example.demo.repositories.CatRepository;
import com.example.demo.repositories.UserRepository;

@RestController
public class TestController {
  @Autowired
  CatRepository catRepository;
  @Autowired
  UserRepository userRepository;
  
  @Autowired
  EntityManager em;
  
  @GetMapping("/testrepo")
  public Object testRepoResult() {
    return catRepository.findByName("princess");
  }
  
  @GetMapping("/hbsession")
  public String hbSessionDemo() {
    String out = "";
    Session session = em.unwrap(Session.class);
    // begin DB transaction, good thing to start with.
    session.beginTransaction();
    
    // getCat with id 1
    //out += session.get(Cat.class, 1).toString();
    Cat fluffy = session.get(Cat.class, 1);
    
    fluffy.setCuteness(55.55);
    
    // session.get is one option, it gets eagerly and returns null if not found
    // session.load is another, it gets lazily:
    Cat lazyfluffy = session.load(Cat.class, 1);
    
    out += lazyfluffy.toString();
    
    // We can add new Cat objects to the DB with save and persist.
    // save takes place immediately, persist makes the object persistent (it will update eventually, 
    // but its part of the session cache as normal)
    
    Cat newCat = new Cat(null, "newhcat", 33.3);
    
    User user = session.get(User.class, 2);
    
    newCat.setOwner(user);
    
    session.save(newCat);
    
    // Some fancier ways to retrieve data from the DB: Criteria and Query
    // Note these are Hibernate 4 so you'll see them in the wild, but they're deprecated in Hibernate 5
    
    // Query is for HQL queries, which uses a query language like SQL but that deals with Objects
    // Criteria is programmative, type-safe way of specifying queryies to the DB.
    // You should know what these are, but in practice we'll use repository CRUD methods and HQL or 
    // SQL queries.
    // HQL is a benefit over SQL becasue it works with any DB.
    
    Set<String> catNames = new HashSet<String>();
    catNames.add("fluffy");
    catNames.add("whiskers");
    catNames.add("kibo");
    
 // add Restrictions. Specify Restrictions using methods on org.hibernate.criterion.Restrictions, 
    // a utility class for this purpose
    Criteria c = session.createCriteria(Cat.class).add(Restrictions.in("name", catNames));
    
    // we can add more restrictions on the end, or add "projections" which aggregate the results.
    List<Cat> catsMeetingCriteria = c.list();
    out += catsMeetingCriteria.toString();
    
    // HQL query, parameters start with :
    org.hibernate.Query<Cat> q = session.createQuery("from Cat where cuteness > :param");
    // q.list gets results
    // set the param to 100.0 to retrive cats with cuteness over 100.
    List<Cat> cuteCats = q.setDouble("param", 100.0).list();
    
    // All cute cats will get cuter.
    for(Cat cat : cuteCats) {
      cat.setCuteness(cat.getCuteness() + 20);
    }
    
    out += cuteCats.toString();
    
 // commit DB transaction
    session.getTransaction().commit();
    
    return out;
  }
}
