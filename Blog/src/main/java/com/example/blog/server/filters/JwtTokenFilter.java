package com.example.blog.server.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.blog.domain.services.AccountDetailsService;
import com.example.blog.http.models.responses.ResponseModel;
import com.example.blog.server.security.AccountDetails;
import com.example.blog.server.security.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final AccountDetailsService accountDetailsService;
    private final ObjectMapper mapper = new ObjectMapper();

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, AccountDetailsService accountDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountDetailsService = accountDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.setCharacterEncoding("utf-8");
        String authHeader = httpServletRequest.getHeader("Authorization");

        ResponseModel<?> response = new ResponseModel<>();
        response.setSucceeded(false);

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                response.setMessage("Неопознанный JWT токен");
                mapper.writeValue(httpServletResponse.getWriter(), response);
                return;

            } else {
                try {
                    int accountId = jwtTokenUtil.validateTokenAndRetrieveClaimAccountId(jwt);
                    String role = jwtTokenUtil.validateTokenAndRetrieveClaimRole(jwt);
                    UserDetails userDetails = accountDetailsService.loadAccountById(accountId);


                    if (!Objects.equals(((AccountDetails)userDetails).account().getRole().getName(), role)){
                        response.setMessage("Недействительный JWT токен");
                        mapper.writeValue(httpServletResponse.getWriter(), response);
                        return;
                    }

                    if (!userDetails.isAccountNonLocked()){
                        response.setMessage("Аккаунт заблокирован");
                        mapper.writeValue(httpServletResponse.getWriter(), response);
                        return;
                    }

                    if (!userDetails.isEnabled()) {
                        response.setMessage("Аккаунт удален");
                        mapper.writeValue(httpServletResponse.getWriter(), response);
                        return;
                    }

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                    int x = 5;
                } catch (JWTVerificationException exc) {
                    response.setMessage("Недействительный JWT токен");
                    mapper.writeValue(httpServletResponse.getWriter(), response);
                    return;
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
