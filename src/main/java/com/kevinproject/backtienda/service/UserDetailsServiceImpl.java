package com.kevinproject.backtienda.service;


import com.kevinproject.backtienda.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario usuario = Usuario.builder()
                         .username("juan")
                         .last_name("lastname")
                         .name("name")
                         .password("$2a$04$40duVuXgdle/sPNqa56fweego7AZIhuH2Pr0C42tAJNxM4ZgNMy2e")
                         .id(1)
                         .build();

        return usuario;
    }

}
