package com.kevinproject.backtienda.service;


import com.kevinproject.backtienda.entity.NoteCategory;
import com.kevinproject.backtienda.repository.NoteCatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class NoteCatService {

    @Autowired
    NoteCatRepository noteCatRepository;

    public Optional<NoteCategory> findCategoryByName(String name){
        return noteCatRepository.findByNameIgnoreCase(name);
    }

    public NoteCategory saveCategory(NoteCategory noteCategory){
        return noteCatRepository.save(noteCategory);
    }

    public List<NoteCategory> findAll(){
        return noteCatRepository.findAll();
    }
}
