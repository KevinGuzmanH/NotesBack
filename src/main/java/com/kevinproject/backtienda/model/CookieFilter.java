package com.kevinproject.backtienda.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinproject.backtienda.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CookieFilter extends OncePerRequestFilter {

    Logger loger = LoggerFactory.getLogger(CookieFilter.class);
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    HashService hashService;
    private static final ObjectMapper MAPPER = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!"/security/V1/signIn".equals(request.getServletPath()) && !"/security/V1/signUp".equals(request.getServletPath())){

            if (!getCookieValue(request,"SESSIONID").isEmpty()){
                String cookieValue = getCookieValue(request,"SESSIONID");
                String username = getCookieValue(request,"USERNAME");

                SecurityContextHolder.getContext().setAuthentication(hashService.hashAuthentication(cookieValue,username));
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
