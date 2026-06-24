package com.metroai.metroai_backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MetroaiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetroaiBackendApplication.class, args);
	}

}
