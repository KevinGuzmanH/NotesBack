package com.kevinproject.backtienda.service;


import com.kevinproject.backtienda.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoteService {

    @Autowired
    NoteRepository noteRepository;


}
