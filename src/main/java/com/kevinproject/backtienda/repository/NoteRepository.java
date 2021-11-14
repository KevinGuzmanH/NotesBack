package com.kevinproject.backtienda.repository;


import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface NoteRepository extends JpaRepository<Note,Integer> {
    Optional<Note> findByTitleAndUsuario(String title, Usuario usuario);

    Optional<Note> findByTitleIgnoreCase(String title);

    Set<Note> findAllByUsuario(Usuario usuario);

}
