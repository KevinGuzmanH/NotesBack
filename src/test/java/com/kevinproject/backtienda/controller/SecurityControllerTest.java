package com.kevinproject.backtienda.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kevinproject.backtienda.dto.NewUsuario;
import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.model.CookieFilter;
import com.kevinproject.backtienda.service.UserDetailsServiceImpl;
import com.sun.istack.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
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

       Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<NewUsuario>> violations = validator.validate(newUsuario);

        assertThat(violations.size()).isEqualTo(0);

        mockMvc.perform(post("/security/V1/signUp")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(gson.toJson("pepe")))
                            .andExpect(status().is(201))
                           ;
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/security/V1/delete/22"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.text", is("mensaje")));
    }

    @Test
    void succes() throws Exception {
        mockMvc.perform(get("/security/V1/success",String.class)).andExpect(status().isOk());
    }

}