package com.kevinproject.backtienda.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinproject.backtienda.dto.NewNote;
import com.kevinproject.backtienda.entity.Note;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureMockMvc

class NotesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Cookie cookieHash = new Cookie("SESSIONID","$2a$10$0DxvQQxK.6kZ.6x8W8XvS.a.5b5j5QP4w8lz4x4pE4nCkKj.7rXK");
    Cookie cookieUsername = new Cookie("USERNAME","kevinYGH");

    @Test
    void deleteNote() throws Exception {
        mockMvc.perform(delete("/Notes/V1/deleteNote/test project")
                        .cookie(cookieHash,cookieUsername)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message",is("Note deleted")));
    }

    @Test
    void getNotes() throws Exception {
        mockMvc.perform(get("/Notes/V1/getNotes")
                        .cookie(cookieHash,cookieUsername)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }
}