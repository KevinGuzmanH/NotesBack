package com.kevinproject.backtienda.service;


import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public Optional<Usuario> findById(int id){
        return usuarioRepository.findById(id);
    }
    public Optional<Usuario> findUsuarioByUsername(String username){
      return usuarioRepository.findByUsernameIgnoreCase(username);
    }

    public Usuario saveUsuario(Usuario newUsuario){
        return usuarioRepository.save(newUsuario);
    }
}
