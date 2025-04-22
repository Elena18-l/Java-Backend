package com.BackSpringBoys.Java_Backend;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
import com.BackSpringBoys.Java_Backend.Repositorio.UsuarioRepositorio;
import com.BackSpringBoys.Java_Backend.Repositorio.VehiculoRepositorio;
import com.BackSpringBoys.Java_Backend.Repositorio.AlquilerRepositorio;
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
	public CommandLineRunner crearAdminYUser(
			UsuarioRepositorio usuarioRepo,
			ClienteRepositorio clienteRepo,
			VehiculoRepositorio vehiculoRepo,
			AlquilerRepositorio alquilerRepo
	) {
		return args -> {
			// Crear ADMIN
			Cliente adminCliente;
			if (!usuarioRepo.existsByUsername("admin")) {
				adminCliente = new Cliente();
				adminCliente.setDni("00000000A");
				adminCliente.setNombre("Admin");
				adminCliente.setApellido1("Sistema");
				adminCliente.setFechaNacimiento(LocalDate.of(1990, 1, 1));

				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
				admin.setEmail("admin@example.com");
				admin.setRol("ADMIN");
				admin.setEnabled(true);

				admin.setCliente(adminCliente);
				adminCliente.setUsuario(admin);

				usuarioRepo.save(admin);
			} else {
				adminCliente = usuarioRepo.findByUsername("admin").get().getCliente();
			}

			// Crear USER
			Cliente userCliente;
			if (!usuarioRepo.existsByUsername("user")) {
				userCliente = new Cliente();
				userCliente.setDni("11111111B");
				userCliente.setNombre("Usuario");
				userCliente.setApellido1("Normal");
				userCliente.setFechaNacimiento(LocalDate.of(1995, 5, 5));

				Usuario user = new Usuario();
				user.setUsername("user");
				user.setPassword(new BCryptPasswordEncoder().encode("user123"));
				user.setEmail("user@example.com");
				user.setRol("USER");
				user.setEnabled(true);

				user.setCliente(userCliente);
				userCliente.setUsuario(user);

				usuarioRepo.save(user);
			} else {
				userCliente = usuarioRepo.findByUsername("user").get().getCliente();
			}

			// Crear Vehículos si no existen
			Vehiculo vehiculoAdmin = vehiculoRepo.findByMatricula("0000AAA")
					.orElseGet(() -> vehiculoRepo.save(new Vehiculo("0000AAA", "Passat", "Volkswagen")));

			Vehiculo vehiculoUser = vehiculoRepo.findByMatricula("1111BBB")
					.orElseGet(() -> vehiculoRepo.save(new Vehiculo("1111BBB", "Civic", "Honda")));

			// Crear Alquileres si no existen
			if (!alquilerRepo.findByCliente(adminCliente).isEmpty() && !alquilerRepo.findByCliente(userCliente).isEmpty()) {
				return;
			}

			if (alquilerRepo.count() == 0) {
				alquilerRepo.save(new Alquiler(
						LocalDate.now().plusDays(1),
						LocalDate.now().plusDays(7),
						vehiculoAdmin,
						adminCliente,
						250.0
				));

				alquilerRepo.save(new Alquiler(
						LocalDate.now().plusDays(2),
						LocalDate.now().plusDays(5),
						vehiculoUser,
						userCliente,
						180.0
				));
			}
		};
	}
}
