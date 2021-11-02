package com.kevinproject.backtienda.controller;

import com.google.gson.Gson;
import com.kevinproject.backtienda.dto.NewUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
@AutoConfigureMockMvc
class SecurityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    Gson gson;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void signUp() throws Exception {
       NewUsuario newUsuario = NewUsuario.builder()
                .name("juan")
                .last_name("perez")
                .username("machin")
                .password("123456")
                .build();

        mockMvc.perform(post("/security/V1/signUp")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(gson.toJson(newUsuario)))
                            .andExpect(status().is(201))
                            .andExpect(jsonPath("$.username",is("machin")))
                           ;
    }


    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/security/V1/delete/22"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message", is("asd")));
    }

    @Test
    void succes() throws Exception {
        mockMvc.perform(get("/security/V1/success",String.class)).andExpect(status().isOk());
    }

}