package com.kevinproject.backtienda.service;


import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
