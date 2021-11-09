package com.kevinproject.backtienda.model;

import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Random;

@Component
public class HashService {

    @Autowired
    UsuarioService usuarioService;

    public String getHash() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 64;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public Authentication hashAuthentication(String hash, String username){
        Usuario usuario = usuarioService.findUsuarioByUsername(username).orElse(null);
        usuario.getHash().getHash().equals(hash);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario,null, Collections.emptyList());
        return auth;
    }
}
