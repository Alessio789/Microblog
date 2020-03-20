package it.marconivr.microblog;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.0.0 - 14/03/2020
 */
@SpringBootApplication
@EnableSwagger2
public class MicroblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroblogApplication.class, args);
	}

}
