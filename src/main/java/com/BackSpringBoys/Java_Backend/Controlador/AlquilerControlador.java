package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Alquiler;
import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Modelo.Usuario;
import com.BackSpringBoys.Java_Backend.Modelo.Vehiculo;
import com.BackSpringBoys.Java_Backend.Repositorio.AlquilerRepositorio;
import com.BackSpringBoys.Java_Backend.Services.AlquilerService;
import com.BackSpringBoys.Java_Backend.Services.ClienteService;
import com.BackSpringBoys.Java_Backend.Services.UsuarioService;
import com.BackSpringBoys.Java_Backend.Services.VehiculoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"user/alquiler", "admin/alquiler"})
public class AlquilerControlador {

    private final VehiculoService vehiculoService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    private final AlquilerRepositorio alquilerRepositorio;
    public AlquilerService alquilerService;

    public AlquilerControlador(AlquilerService alquilerService, VehiculoService vehiculoService, ClienteService clienteService, UsuarioService usuarioService, AlquilerRepositorio alquilerRepositorio) {
        this.alquilerService = alquilerService;
        this.vehiculoService = vehiculoService;
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
        this.alquilerRepositorio = alquilerRepositorio;
    }

    /* debug alquiler
    @GetMapping
    public String listarAlquiler(Model model) {
        model.addAttribute("alquileres", alquilerService.obtenerTodosLosAlquileres());
        return "alquiler/listaAlquiler";
    } */

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String listarAlquiler(@AuthenticationPrincipal User user, Model model) {
        System.out.println("Intentando cargar lista de alquileres...");

        var alquileres = alquilerService.obtenerTodosLosAlquileres();
        if (user.getAuthorities() != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            System.out.println("Usuario autenticado: " + user.getUsername());
            Optional<Usuario> usuario = usuarioService.findByUsername(user.getUsername());
            if (usuario.isPresent()) {
                Cliente cli = usuario.get().getCliente();
                List<Alquiler> alquilerList = alquilerRepositorio.findByCliente(cli);
                if (alquilerList.isEmpty()) {
                    System.out.println("No hay alquileres para el cliente: " + cli.getNombre());
                } else {
                    alquileres = alquilerList;
                }
            } else {
                System.out.println("Usuario no encontrado.");
            }
        } else if(user.getAuthorities() != null && user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            System.out.println("Usuario autenticado: " + user.getUsername());
            alquileres = alquilerService.obtenerTodosLosAlquileres();
        }
        alquileres.forEach(a -> System.out.println(a));

        model.addAttribute("alquileres", alquileres);
        return "alquiler/listaAlquiler";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String alquiler(@RequestParam(name = "id", defaultValue = "0") Long id, Model model) {
        if (id == 0) {
            return "redirect:/alquiler";
        }
        model.addAttribute("alquiler", alquilerService.obtenerAlquilerPorId(id));
        return "alquiler/alquiler";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioAgregarAlquiler(Model model) {
        model.addAttribute("alquiler", new Alquiler());
        model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
        model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
        return "alquiler/addAlquiler";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardarAlquiler(@Valid @ModelAttribute Alquiler alquiler, BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (result.hasGlobalErrors()) {
                for (ObjectError error : result.getGlobalErrors()) {
                    System.out.println("ERROR GLOBAL: " + error.getDefaultMessage());
                }
            }

            if (result.hasFieldErrors()) {
                for (FieldError fieldError : result.getFieldErrors()) {
                    System.out.println("ERROR DE CAMPO: " + fieldError.getField() + " - " + fieldError.getDefaultMessage());
                }
            }

            System.out.println(result.getFieldError());
            model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
            model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
            return "alquiler/addAlquiler";
        }
        alquilerService.guardarAlquiler(alquiler);
        return "redirect:/admin/alquiler";
    }

    @GetMapping("/addPrueba")
    @PreAuthorize("hasRole('ADMIN')")
    public String addDatos(){
        ArrayList<Vehiculo> vehs = new ArrayList<>();
        vehs.add(new Vehiculo( "1235ABC", "Megane", "Renault", ""));
        vehs.add(new Vehiculo( "5678DEF", "Clio", "Renault", ""));
        vehs.add(new Vehiculo( "9101GHI", "Astra", "Opel", ""));
        vehs.add(new Vehiculo( "1121JKL", "Corsa", "Opel", ""));
        vehs.add(new Vehiculo( "3141MNO", "Ibiza", "Seat", ""));
        vehs.add(new Vehiculo( "5161PQR", "Leon", "Seat", ""));
        addAllVehiculos(vehs);
        System.out.println("vehiculos añadidos");

//        ArrayList<Cliente> clis = new ArrayList<>();
//        clis.add(new Cliente( "78776156Y", "Juan", "Perez", "Fernandez", LocalDate.of(2001, 2,15)));
//        clis.add(new Cliente( "98123270A", "Pedro", "Garcia", "Garcia", LocalDate.of(2000, 10, 10)));
//        clis.add(new Cliente( "73789685G", "Maria", "Lopez", "Perez", LocalDate.of(1999, 10, 2)));
//        clis.add(new Cliente( "06629623B", "Ana", "Martinez", "Garcia", LocalDate.of(1998, 11, 10)));
//        clis.add(new Cliente( "87654321E", "Luis", "Sanchez", "Lopez", LocalDate.of(1997, 4, 15)));
//        clis.add(new Cliente( "76710672Z", "Sara", "Gomez", "Martinez", LocalDate.of(1996, 5, 20)));
//        clis.add(new Cliente( "56781234G", "Pablo", "Rodriguez", "Sanchez", LocalDate.of(1995, 6, 25)));
//        clis.add(new Cliente( "87654321H", "Laura", "Hernandez", "Gomez", LocalDate.of(1994, 7, 30)));
//        clis.add(new Cliente( "12348765I", "Carlos", "Fernandez", "Rodriguez", LocalDate.of(1993, 8, 5)));
//        clis.add(new Cliente( "56781234J", "Sonia", "Garcia", "Hernandez", LocalDate.of(2000, 10, 10)));
//        addAllClientes(clis);
//        System.out.println("clientes añadidos");
//
//        ArrayList<Alquiler> alqs = new ArrayList<>();
//        Iterable<Cliente> clientesIT = clienteService.obtenerTodosLosClientes();
//        clis.clear();
//        clientesIT.forEach(clis::add);
//        Iterable<Vehiculo> vehiculosIT = vehiculoService.obternerTodosLosVehiculos();
//        vehs.clear();
//        vehiculosIT.forEach(vehs::add);
//        alqs.add(new Alquiler(LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 7), vehs.get(0), clis.get(0), 150.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 7, 10), LocalDate.of(2025, 7, 20), vehs.get(1), clis.get(2), 200.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 6, 15), LocalDate.of(2025, 6, 22), vehs.get(3), clis.get(3), 180.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 8, 5), LocalDate.of(2025, 8, 12), vehs.get(4), clis.get(4), 160.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 8, 3), LocalDate.of(2025, 8, 10), vehs.get(5), clis.get(5), 170.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 7, 25), LocalDate.of(2025, 8, 2), vehs.get(3), clis.get(6), 190.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 6, 20), LocalDate.of(2025, 6, 25), vehs.get(1), clis.get(7), 140.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 7, 5), LocalDate.of(2025, 7, 15), vehs.get(2), clis.get(8), 210.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 17), vehs.get(3), clis.get(9), 185.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 6, 25), LocalDate.of(2025, 7, 5), vehs.get(4), clis.get(7), 200.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 8, 8), LocalDate.of(2025, 8, 14), vehs.get(5), clis.get(0), 160.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 7, 18), LocalDate.of(2025, 7, 24), vehs.get(0), clis.get(2), 190.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 5), vehs.get(1), clis.get(3), 130.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 6, 30), LocalDate.of(2025, 7, 4), vehs.get(2), clis.get(4), 170.00));
//        alqs.add(new Alquiler(LocalDate.of(2025, 6, 22), LocalDate.of(2025, 8, 1), vehs.get(3), clis.get(5), 180.00));
//        alqs.forEach(alquiler -> {
//            System.out.println("Alquiler: " + alquiler.getId() + ", Vehiculo: " + alquiler.getVehiculo().getMatricula() + ", Cliente: " + alquiler.getCliente().getNombre());
//        });
//        addAllAlquiler(alqs);
        System.out.println("Datos de prueba añadidos");
        return "redirect:/admin/alquiler";
    }

    private void addAllVehiculos(ArrayList<Vehiculo> vehs){
        for (Vehiculo vehiculo : vehs) {
            try {
                if(!vehiculoService.existeVehiculoPorMatricula(vehiculo.getMatricula())) {
                    vehiculoService.guardarVehiculo(vehiculo);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage() + vehs.toString());
            }
        }
    }

    private void addAllClientes(ArrayList<Cliente> clis){
        for (Cliente cliente : clis) {
            try{
                if(!clienteService.existsByDni(cliente.getDni())){
                    clienteService.guardarCliente(cliente);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage() + clis.toString());
            }
        }
    }

    private void addAllAlquiler(ArrayList<Alquiler> alqs){
        for (Alquiler alquiler : alqs) {
            try {
                alquilerService.guardarAlquiler(alquiler);
            } catch (Exception e) {
                System.out.println(e.getMessage() + alqs.toString());
            }
        }
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioEditarAlquiler(@PathVariable("id") Long id, Model model) {
        Optional<Alquiler> alquilerOptional = alquilerService.obtenerAlquilerPorId(id);
        if (alquilerOptional.isPresent()) {
            model.addAttribute("alquiler", alquilerOptional.get());
            model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
            model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
            return "alquiler/editAlquiler";
        } else {
            return "redirect:/admin/alquiler";
        }
    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasRole('ADMIN')")
    public String actualizarAlquiler(@Valid @ModelAttribute Alquiler alquiler, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("vehiculos", vehiculoService.obternerTodosLosVehiculos());
            model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
            return "alquiler/editAlquiler";
        }
        alquilerService.guardarAlquiler(alquiler);
        return "redirect:/admin/alquiler";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable("id") Long id) {
        alquilerService.eliminarAlquiler(id);
        return "redirect:/admin/alquiler";
    }

}
