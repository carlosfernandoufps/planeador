package com.co.planeador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class PlaneadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaneadorApplication.class, args);
	}

}
