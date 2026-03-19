package com.clubdeportivo2.servicioreservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// habilitamos OpenFeign para que Spring detecte nuestras interfaces @FeignClient
// y genere automáticamente las implementaciones que hacen las llamadas HTTP
@SpringBootApplication
@EnableFeignClients
public class ServicioreservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioreservasApplication.class, args);
	}

}
