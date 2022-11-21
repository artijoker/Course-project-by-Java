package com.example.blog.server.config;


import com.example.blog.server.filters.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers(
                        "/accounts/registration",
                        "/accounts/login",
                        "/accounts/get-authors",
                        "/categories/get-categories",
                        "/posts/get-posts",
                        "/posts/get-post",
                        "/posts/get-author-posts",
                        "/posts/get-posts-by-category",
                        "/posts/get-published-posts-by-account"
                ).permitAll()
                .antMatchers(
                        "/admin/accounts/get-account",
                        "/admin/accounts/get-all-account",
                        "/admin/accounts/add-new-account",
                        "/admin/accounts/add-new-account",
                        "/admin/accounts/update-account",
                        "/admin/accounts/banned-account",
                        "/admin/accounts/unlock-account",
                        "/admin/accounts/delete-account",
                        "/admin/posts/get-pending-posts",
                        "/admin/posts/add-post-and-published",
                        "/admin/posts/publish-post",
                        "/admin/posts/reject-post",
                        "/admin/posts/update-post",
                        "/admin/categories/add-new-category",
                        "/admin/categories/update-category",
                        "/admin/categories/delete-category",
                        "/admin/roles/get-roles"
                ).hasRole("ADMIN")
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors();
    }


    @Bean
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
