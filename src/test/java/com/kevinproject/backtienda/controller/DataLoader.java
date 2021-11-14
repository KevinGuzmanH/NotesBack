package com.kevinproject.backtienda.controller;

import com.kevinproject.backtienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DataLoader implements CommandLineRunner {

    @Autowired
    UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {

    }
}
