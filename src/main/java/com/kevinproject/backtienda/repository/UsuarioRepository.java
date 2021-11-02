package com.kevinproject.backtienda.repository;


import com.kevinproject.backtienda.entity.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByUsernameIgnoreCase(String username);


}
