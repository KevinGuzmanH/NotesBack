package com.kevinproject.backtienda.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinproject.backtienda.dto.LoginUsuario;
import com.kevinproject.backtienda.entity.Note;
import com.kevinproject.backtienda.entity.NoteCategory;
import com.kevinproject.backtienda.entity.Usuario;
import com.kevinproject.backtienda.repository.NoteCatRepository;
import com.kevinproject.backtienda.repository.NoteRepository;
import com.kevinproject.backtienda.repository.UsuarioRepository;
import com.kevinproject.backtienda.service.NoteCatService;
import com.kevinproject.backtienda.service.UserDetailsServiceImpl;
import com.kevinproject.backtienda.service.UsuarioService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CookieFilter extends OncePerRequestFilter {

    Logger loger = LoggerFactory.getLogger(CookieFilter.class);
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    HashCreator hashCreator;
    private static final ObjectMapper MAPPER = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!"/security/V1/signIn".equals(request.getServletPath()) && !"/security/V1/signUp".equals(request.getServletPath())){

            if (!getCookieValue(request,"SESSIONID").isEmpty()){
                String cookieValue = getCookieValue(request,"SESSIONID");
                String username = getCookieValue(request,"USERNAME");

                loger.error(usuarioService.findUsuarioByUsername(username).get().getLast_name());

                SecurityContextHolder.getContext().setAuthentication(hashCreator.hashValid(cookieValue,username));
            }else {
                loger.error("no hay cookie");
            }

        }

            filterChain.doFilter(request,response);
    }


    private String getCookieValue(HttpServletRequest req, String cookieName) {
        return Arrays.stream(req.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }


}
