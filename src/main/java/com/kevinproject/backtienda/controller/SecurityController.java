package com.kevinproject.backtienda.controller;

import com.google.gson.Gson;
import com.kevinproject.backtienda.dto.ErrorDto;
import com.kevinproject.backtienda.dto.Message;
import com.kevinproject.backtienda.dto.NewUsuario;
import com.kevinproject.backtienda.entity.SessionIdHash;
import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.model.HashCreator;
import com.kevinproject.backtienda.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;

@CrossOrigin
@RestController
@RequestMapping(path = "/security/V1")
public class SecurityController {

    @Autowired
    Gson gson;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HashCreator hashCreator;

    @PostMapping(path = "/signUp",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signUp(@Valid NewUsuario newUsuario, BindingResult result){
        if (result.hasErrors())
            throw new ValidationException("Check the fields and retry");
        if (usuarioService.findUsuarioByUsername(newUsuario.getUsername()).isPresent())
            throw new RuntimeException("Username already exists");
        Usuario savedUsuario = usuarioService.saveUsuario(Usuario.builder()
                .name(newUsuario.getName())
                .last_name(newUsuario.getLast_name())
                .username(newUsuario.getUsername())
                .password(passwordEncoder.encode(newUsuario.getPassword()))
                .build());
            return ResponseEntity.created(URI.create("/security/V1/register")).body(gson.toJson(savedUsuario));
    }

    @GetMapping(path = "/delete/{id}")
    public ResponseEntity<String> deletebyid(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_by_cookie","12345");
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(70 * 70);
        response.addCookie(cookie);
        usuarioService.findUsuarioByUsername("u");
        return ResponseEntity.ok().body(gson.toJson(Message.builder().message("asd").build()));
    }

    @PostMapping(path = "/signIn",consumes = MediaType.APPLICATION_JSON_VALUE
                                    ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signIn(HttpServletResponse response,
                                         @RequestHeader(name = "username",defaultValue = "username") String username,
                                         @RequestHeader(name = "password",defaultValue = "password") String password) {

        if (!usuarioService.findUsuarioByUsername(username).isPresent())
            return new ResponseEntity<>(gson.toJson(new Message("Wrong Username")),HttpStatus.UNAUTHORIZED);
        if (!passwordEncoder.matches(password,passwordEncoder.encode(usuarioService.findUsuarioByUsername(username).get().getPassword())))
            return new ResponseEntity<>(gson.toJson(new Message("Wrong Password")),HttpStatus.UNAUTHORIZED);

        Usuario usuario = usuarioService.findUsuarioByUsername(username).get();
        String hash = hashCreator.getHash();

            Calendar expiration = Calendar.getInstance();
            expiration.add(Calendar.HOUR_OF_DAY,1);

            usuario.setHash(SessionIdHash.builder().hash(hash).expiration(expiration).build());
            usuarioService.saveUsuario(usuario);

            Cookie cookie = new Cookie("SESSIONID",hash);
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            return ResponseEntity.noContent().build();

    }


}
