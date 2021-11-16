package com.kevinproject.backtienda.repository;

import com.kevinproject.backtienda.entity.NoteCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class NoteCatRepositoryTest {

    @Autowired
    NoteCatRepository noteCatRepository;

    @Test
    void findByNameIgnoreCase() {
        //given
        String name = "job";
        //when
        String categoryDescription = noteCatRepository.findByNameIgnoreCase(name).get().getDescription();
        //then
        assertEquals("Notes about job", categoryDescription);
    }

    @Test
    void findByNameIgnoreCaseNotFound() {
        //given
        String name = "job2";
        //when
        Optional<NoteCategory> categoryDescription = noteCatRepository.findByNameIgnoreCase(name);
        //then
        assertFalse(categoryDescription.isPresent());
    }
}