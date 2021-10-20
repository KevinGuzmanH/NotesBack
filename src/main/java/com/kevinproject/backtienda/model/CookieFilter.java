package com.kevinproject.backtienda.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinproject.backtienda.dto.LoginUsuario;
import com.kevinproject.backtienda.service.UserDetailsServiceImpl;
import com.kevinproject.backtienda.service.UsuarioService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        loger.info("doFilterInternal");
        loger.info(request.getRemoteAddr());

        if (!"/security/V1/signIn".equals(request.getServletPath()) && HttpMethod.POST.matches(request.getMethod())){
            MAPPER.readValue(request.getInputStream(), LoginUsuario.class);

            String token = new UsernamePasswordAuthenticationToken()
        }

        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
        UserDetails userDetails = userDetailsService.loadUserByUsername("username");
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Optional<Cookie> cookie = Stream.of(Optional.ofNullable(request.getCookies())
                .orElse(new Cookie[0]))
                .filter(cookie1 -> "auth_by_cookie".equals(cookie1.getName()))
                .findFirst();

            if (cookie.isPresent()) {
                loger.info(cookie.get().toString());
                loger.info("cookie presente");
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
