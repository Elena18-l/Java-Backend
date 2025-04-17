package com.BackSpringBoys.Java_Backend.Security;

import com.BackSpringBoys.Java_Backend.Modelo.Cliente;
import com.BackSpringBoys.Java_Backend.Repositorio.ClienteRepositorio;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ClienteDetailsServiceImpl implements UserDetailsService {
    private final ClienteRepositorio clienteRepositorio;

    public ClienteDetailsServiceImpl(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepositorio.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return new org.springframework.security.core.userdetails.User(
                cliente.getUsername(),
                cliente.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + cliente.getRol().name()))
        );
    }
}
