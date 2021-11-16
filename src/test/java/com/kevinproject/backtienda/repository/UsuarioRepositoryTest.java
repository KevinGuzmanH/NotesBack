package com.kevinproject.backtienda.repository;

import com.google.gson.Gson;
import com.kevinproject.backtienda.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void findByUsername() {
        //given
            String username = "kevinYGH";
        //when
            Usuario usuario = usuarioRepository.findByUsernameIgnoreCase(username).get();
        //then
        assertEquals("Perez",usuario.getLast_name());
    }

    @Test
    void getHashWithUserName() {
        //given
        String username = "kevinYGH";
        //when
        Usuario usuario = usuarioRepository.findByUsernameIgnoreCase(username).get();
        //then
        assertEquals("$2a$10$0DxvQQxK.6kZ.6x8W8XvS.a.5b5j5QP4w8lz4x4pE4nCkKj.7rXK",usuario.getHash().getHash());
    }

}