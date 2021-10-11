package com.kevinproject.backtienda.controller;

import com.google.gson.Gson;
import com.kevinproject.backtienda.dto.Message;
import com.kevinproject.backtienda.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping(path = "/security/V1",method = RequestMethod.POST)
public class Controller {

    @Autowired
    Gson gson;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping(path = "/register",
                 consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(){
        return new ResponseEntity<>(gson.toJson(""), HttpStatus.OK);
    }

    @GetMapping(path = "/delete/{id}")
    public ResponseEntity<String> deletebyid(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("auth_by_cookie","12345");
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(70 * 70);
        response.addCookie(cookie);
        return ResponseEntity.ok("200");
    }

    @PreAuthorize("permitAll()")
    @PostMapping(path = "/login",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> login(HttpServletResponse response,
                                         @RequestHeader(name = "username",defaultValue = "username",required = false) String username,
                                         @RequestHeader(name = "password",defaultValue = "password",required = false) String password) {
        if (!usuarioService.GetUserByUsername(username).isPresent())
            return new ResponseEntity<>(gson.toJson(new Message("Nombre de Usuario Incorrecto")),HttpStatus.UNAUTHORIZED);
        if (!passwordEncoder.matches(password,usuarioService.GetUserByUsername(username).get().getPassword()))
            return new ResponseEntity<>(gson.toJson(new Message("Contraseña Incorrecta")),HttpStatus.UNAUTHORIZED);
        Cookie cookie = new Cookie("nombre","ooollla");
        response.addCookie(cookie);
        return new ResponseEntity<>(gson.toJson(SecurityContextHolder.getContext().getAuthentication()),HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "/success")
    public ResponseEntity<String> success() {

        return new ResponseEntity<>(gson.toJson("success"),HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "/fail")
    public ResponseEntity<String> fail() {

        return new ResponseEntity<>(gson.toJson("fail"),HttpStatus.OK);
    }
}
