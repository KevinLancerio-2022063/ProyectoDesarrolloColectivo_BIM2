package com.BIM1.ProyectoDesarrolloColectivo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoDesarrolloColectivoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoDesarrolloColectivoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("test api");
	}
}
