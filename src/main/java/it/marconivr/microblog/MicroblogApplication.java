package it.marconivr.microblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.1.0 - 20/03/2020
 */
@SpringBootApplication
@EnableSwagger2
public class MicroblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroblogApplication.class, args);
	}

}
