package com.revature.cheese;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheeseController {

  private List<String> cheeses = Arrays.asList("Cheddar", "Brie", "Gorgonzola");
  private Integer cheeseIndex = 0;

  @GetMapping
  public String cheeseGet() {
    String cheese = this.cheeses.get(cheeseIndex);
    this.cheeseIndex++;
    this.cheeseIndex %= 3;
    return cheese;

  }


}
