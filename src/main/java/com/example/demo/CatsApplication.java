package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.example.demo.demobeans.DemoBean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @SpringBootApplication includes @Configuration, so we can write @Bean methods here
@EnableSwagger2
@SpringBootApplication
public class CatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatsApplication.class, args);
	}
	
	/**
	 * CORS Filter Bean. Just put this in projects to disable CORS.
	 * @return
	 */
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	  // We're defining the class we're using inline here as a shortcut
	  // You could create a separate file.
	  return new WebMvcConfigurer() {
	    public void addCorsMappings(CorsRegistry registry) {
	      registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
	    }
	  };
	}
	
	/**
	 * Swagger "Docket" bean for all the Controllers in our project. API Documentation.
	 * @return
	 */
	@Bean
	public Docket catApi() {
	  return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.example.demo")).build();
	}
	
	@Bean
	public DemoBean createDemoBean() {
	  return new DemoBean();
	}
	
	// We can easily use beans from the Spring framework or Spring projects using Java config
	// A bean is any object managed by Spring.
	@Bean
	public CommandLineRunner printOnStartupRunner() {
	  return (String[] args) -> {
	    System.out.println("Hello from inside CommanLineRunner bean");
	  };
	}

}
