package com.kevinproject.backtienda.service;


import com.kevinproject.backtienda.entity.NoteCategory;
import com.kevinproject.backtienda.repository.NoteCatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class NoteCatService {

    @Autowired
    NoteCatRepository noteCatRepository;

    public Optional<NoteCategory> findNoteByName(String name){
        return noteCatRepository.findByNameIgnoreCase(name);
    }

}
