package com.kevinproject.backtienda.repository;

import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class NoteRepositoryTest {

    @Autowired
    NoteRepository noteRepository;

    @Test
    void findByTitleIgnoreCase() {
        //given
        String title = "tesT PROJECT";
        //when
        String noteText = noteRepository.findByTitleIgnoreCase(title).get().getText();
        //then
        assertEquals("Add test with mocks and send to juan",noteText);
    }

    @Test
    void findByTitleIgnoreCaseWithNonExistentTitle() {
        //given
        String title = "friend PROJECT";
        //when
        Optional<Note> note = noteRepository.findByTitleIgnoreCase(title);
        //then
        assertFalse(note.isPresent());
    }
}