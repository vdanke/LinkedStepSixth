package org.step.linked.step.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.step.linked.step.model.Authorities;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("foo@mail.ru").password("password").authorities(Authorities.ROLE_USER.name())
                .and()
                .withUser("bar@mail.ru").password("password").authorities(Authorities.ROLE_USER.name())
                .and()
                .withUser("author@mail.ru").password("password").authorities(Authorities.ROLE_AUTHOR.name())
                .and()
                .withUser("admin@mail.ru").password("password").authorities(Authorities.ROLE_ADMIN.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/courses").permitAll()
                .antMatchers(HttpMethod.GET, "/courses/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/courses/*").hasAuthority(Authorities.ROLE_AUTHOR.name())
                .antMatchers(HttpMethod.POST, "/courses").hasAuthority(Authorities.ROLE_AUTHOR.name())
                .antMatchers(HttpMethod.GET, "/users").hasAuthority(Authorities.ROLE_ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/users/*").hasAnyAuthority(
                    Authorities.ROLE_USER.name(), Authorities.ROLE_AUTHOR.name(), Authorities.ROLE_ADMIN.name()
                )
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .csrf().disable()
                .cors(httpSecurityCorsConfigurer -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();

                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                    corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
                    corsConfiguration.setAllowCredentials(true);
                    corsConfiguration.setMaxAge(3600L);

//                    corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type", "Content-Disposition", "Accept", "Authorization"));
//                    corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
//                    corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5050", "https://google.com", "https://github.com", "http://178.0.1.19:80"));
//                    corsConfiguration.setAllowedOriginPatterns(Arrays.asList("http://users.*.kz"))
                });
    }
}
