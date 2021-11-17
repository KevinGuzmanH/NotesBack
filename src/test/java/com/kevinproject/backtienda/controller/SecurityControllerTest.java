package com.kevinproject.backtienda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinproject.backtienda.dto.NewUsuario;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application.yml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class SecurityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void signUp() throws Exception {

        //given
       NewUsuario newUsuario = NewUsuario.builder()
                .name("juan")
                .last_name("perez")
                .username("machin")
                .password("123456")
                .build();
       //when then
        mockMvc.perform(post("/security/V1/signUp")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(newUsuario)))
                            .andExpect(status().is(201))
                            .andExpect(jsonPath("$.username",is("machin")))
                           ;
    }


    @Test
    void checkHashWithCorrectCredentials() throws Exception {
        //given
        Cookie cookieHash = new Cookie("SESSIONID","$2a$10$0DxvQQxK.6kZ.6x8W8XvS.a.5b5j5QP4w8lz4x4pE4nCkKj.7rXK");
        Cookie cookieUsername = new Cookie("USERNAME","kevinYGH");

        //when then
        mockMvc.perform(get("/security/V1/checkHash")
                        .cookie(cookieHash,cookieUsername))
                .andExpect(status().is(200));

    }

    @Test
    void checkHashWithBadCredentials() throws Exception {
        //given
        Cookie cookieHash = new Cookie("SESSIONID","$2a$10$0DxvQQxKkKj.7rXK");
        Cookie cookieUsername = new Cookie("USERNAME","kevinYGH");

        //when then
        mockMvc.perform(get("/security/V1/checkHash")
                        .cookie(cookieHash,cookieUsername))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.error", is("Unauthorized Path")))
                .andExpect(jsonPath("$.code", is(401)));

    }

    @Test
    void checkHashWithoutCredentials() throws Exception {

        mockMvc.perform(get("/security/V1/checkHash"))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.error", is("Unauthorized Path")))
                .andExpect(jsonPath("$.code", is(401)));
    }


}