package com.BackSpringBoys.Java_Backend.Controlador;

import com.BackSpringBoys.Java_Backend.Modelo.Prueba;
import com.BackSpringBoys.Java_Backend.Repositorio.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/demo")
public class PruebaController {

    @Autowired
    private PruebaRepository pruebaRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        Prueba n = new Prueba();
        n.setNombre(name);
        n.setApellido(email);
        pruebaRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Prueba> getAllUsers() {

        return pruebaRepository.findAll();
    }
}