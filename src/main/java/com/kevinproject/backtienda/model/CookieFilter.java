package com.kevinproject.backtienda.model;


import com.kevinproject.backtienda.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieFilter extends OncePerRequestFilter {

    Logger loger = LoggerFactory.getLogger(CookieFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        loger.info("doFilterInternal");
        loger.info(request.getRemoteAddr());

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
}
