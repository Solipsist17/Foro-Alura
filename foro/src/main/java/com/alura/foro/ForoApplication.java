package com.alura.foro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ForoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Esto aplica CORS a todas las rutas.
						//.allowedOrigins("http://localhost:3000") // Orígenes permitidos
						.allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
						.allowedHeaders("*");
						// .allowedHeaders("Content-Type", "Authorization") // solo encabezados permitidos
			}
		};
	}

}
