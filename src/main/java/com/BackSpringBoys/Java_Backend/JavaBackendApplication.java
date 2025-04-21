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
	public CommandLineRunner crearAdminYUser(UsuarioRepositorio usuarioRepo, ClienteRepositorio clienteRepo) {
		return args -> {
			if (!usuarioRepo.existsByUsername("admin")) {
				Cliente adminCliente = new Cliente();
				adminCliente.setDni("00000000A");
				adminCliente.setNombre("Admin");
				adminCliente.setApellido1("Sistema");
				adminCliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));
				adminCliente = clienteRepo.save(adminCliente);

				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
				admin.setEmail("admin@example.com");
				admin.setRol("ADMIN");
				admin.setCliente(adminCliente);
				usuarioRepo.save(admin);
			}

			if (!usuarioRepo.existsByUsername("user")) {
				Cliente userCliente = new Cliente();
				userCliente.setDni("11111111B");
				userCliente.setNombre("Usuario");
				userCliente.setApellido1("Normal");
				userCliente.setFechaNacimiento(LocalDate.of(1995, 5, 5));
				userCliente = clienteRepo.save(userCliente);

				Usuario user = new Usuario();
				user.setUsername("user");
				user.setPassword(new BCryptPasswordEncoder().encode("user123"));
				user.setEmail("user@example.com");
				user.setRol("USER");
				user.setCliente(userCliente);
				usuarioRepo.save(user);
			}
		};
	}

}
