package com.BackSpringBoys.Java_Backend;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class JavaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner crearAdmin(UsuarioRepositorio usuarioRepo, ClienteRepositorio clienteRepo) {
		return args -> {
			if (!usuarioRepo.existsByUsername("admin")) {
				Cliente adminCliente = new Cliente();
				adminCliente.setDni("00000000A");
				adminCliente.setNombre("Admin");
				adminCliente.setApellido1("Sistema");
				adminCliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
				clienteRepo.save(adminCliente);

				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
				admin.setEmail("admin@example.com");
				admin.setRol("ADMIN");
				admin.setCliente(adminCliente);
				usuarioRepo.save(admin);
			}
		};
	}
}
