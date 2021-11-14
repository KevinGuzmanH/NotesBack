package com.kevinproject.backtienda.service;


import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public Note saveNote(Note newNote){
        return noteRepository.save(newNote);
    }

    public Optional<Note> findByTitle(String title){
        return noteRepository.findByTitleIgnoreCase(title);
    }

    public void deleteBytitle(String title){
        noteRepository.delete(this.findByTitle(title).get());
    }

    public Set<Note> findAllByUsuario(Usuario usuario){
        return noteRepository.findAllByUsuario(usuario);
    }

    public Note findByTitleAndUsuario(String title, Usuario usuario){
        return noteRepository.findByTitleAndUsuario(title,usuario).get();
    }
}
