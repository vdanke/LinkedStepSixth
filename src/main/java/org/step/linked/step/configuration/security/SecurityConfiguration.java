package org.step.linked.step.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.step.linked.step.configuration.security.filter.AuthenticationFilter;
import org.step.linked.step.configuration.security.filter.AuthorizationFilter;
import org.step.linked.step.configuration.security.handlers.OAuth2FailureHandler;
import org.step.linked.step.configuration.security.handlers.OAuth2SuccessHandler;
import org.step.linked.step.configuration.security.handlers.RestAuthenticationEntryPoint;
import org.step.linked.step.configuration.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import org.step.linked.step.model.User;
import org.step.linked.step.service.JwtService;
import org.step.linked.step.service.SerializationDeserializationService;
import org.step.linked.step.service.impl.UserServiceImpl;

import java.util.Collections;

import static org.step.linked.step.configuration.security.SecurityConstants.OAUTH2_AUTHORIZATION_ENDPOINT;
import static org.step.linked.step.configuration.security.SecurityConstants.OAUTH2_REDIRECT_ENDPOINT;

/*
app -> provider -> app -> request repository -> authorization -> success or failure handler
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    jsr250Enabled = true,
    securedEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final SerializationDeserializationService<User, String> serializationDeserializationService;
    private final JwtService jwtService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository requestRepository;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .antMatchers("/courses/**").permitAll()
                .antMatchers("/notifications/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .logout()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(requestRepository)
                .baseUri(OAUTH2_AUTHORIZATION_ENDPOINT)
                .and()
                .redirectionEndpoint()
                .baseUri(OAUTH2_REDIRECT_ENDPOINT)
                .and()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
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
                })
                .httpBasic().disable()
                .addFilter(customAuthenticationFilter())
                .addFilter(customAuthorizationFilter());
    }
    
    @Bean
    public UsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authFilter = new AuthenticationFilter(
                serializationDeserializationService, userService, jwtService
        );
        authFilter.setAuthenticationManager(super.authenticationManager());
        authFilter.setFilterProcessesUrl("/auth/login/filter");
        return authFilter;
    }

    @Bean
    public BasicAuthenticationFilter customAuthorizationFilter() throws Exception {
        return new AuthorizationFilter(
                super.authenticationManager(), jwtService
        );
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }
}
