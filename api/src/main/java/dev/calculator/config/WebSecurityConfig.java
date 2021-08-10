package dev.calculator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.calculator.config.auth.ApplicationUserDetailsService;
import dev.calculator.config.http.AuthenticationFilter;
import dev.calculator.config.http.AuthorizationFilter;
import dev.calculator.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SECURITY_HEADER_NAME = "X-Auth-Token";

    public static final String USERS_LOGIN_PATH = "/v1/users/login";
    public static final String USERS_REGISTER_PATH = "/v1/users/register";

    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public WebSecurityConfig(ApplicationUserDetailsService applicationUserDetailsService,
                             BCryptPasswordEncoder bCryptPasswordEncoder,
                             ObjectMapper objectMapper,
                             UserRepository userRepository)//
    {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.addAllowedMethod("DELETE");
            corsConfiguration.addAllowedMethod("PUT");
            return corsConfiguration;
        })
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, USERS_REGISTER_PATH).permitAll()
                .antMatchers("/static_resource").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .addFilter(authenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager(), applicationUserDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(),
                        userRepository,
                        objectMapper);
        authenticationFilter.setFilterProcessesUrl(USERS_LOGIN_PATH);
        return authenticationFilter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(applicationUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
