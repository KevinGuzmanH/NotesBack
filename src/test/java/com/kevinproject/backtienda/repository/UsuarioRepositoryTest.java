package com.kevinproject.backtienda.repository;

import com.kevinproject.backtienda.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application.yml")
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    Logger logger = LoggerFactory.getLogger(UsuarioRepositoryTest.class);
    @Test
    void findByUsername() {
        usuarioRepository.save(Usuario.builder().username("usernn").build());
        assertNotNull(usuarioRepository.findByUsername("u"));
        logger.info(String.valueOf(usuarioRepository.findByUsername("u").get().getUsername()));
    }
}