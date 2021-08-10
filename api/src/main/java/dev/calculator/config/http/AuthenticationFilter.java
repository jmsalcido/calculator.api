package dev.calculator.config.http;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.calculator.config.WebSecurityConfig;
import dev.calculator.config.error.RestAuthenticationException;
import dev.calculator.model.User;
import dev.calculator.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final long EXPIRATION_IN_MS = 1000L * 60 * 60;

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager, //
                                UserRepository userRepository,
                                ObjectMapper objectMapper)
    {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, //
                                            HttpServletResponse response, //
                                            FilterChain chain, //
                                            Authentication authResult) throws IOException //
    {
        Date expirationTime = new Date(System.currentTimeMillis() + EXPIRATION_IN_MS);

        List<String> roles = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String username = authResult.getName();
        String token = JWT.create()
                .withClaim("username", username)
                .withClaim("roles", roles)
                .withExpiresAt(expirationTime)
                .sign(Algorithm.HMAC512("password"));

        response.addHeader(WebSecurityConfig.SECURITY_HEADER_NAME, token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> responseValue = new HashMap<>();
        responseValue.put("username", username);
        responseValue.put("roles", roles);
        responseValue.put("accessToken", token);

        objectMapper.writeValue(response.getWriter(), responseValue);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, //
                                                HttpServletResponse response)
            throws AuthenticationException //
    {
        User user = getUser(request);

        UsernamePasswordAuthenticationToken token = createAuthentication(user);

        return getAuthenticationManager().authenticate(token);
    }

    private User getUser(HttpServletRequest request) {
        User user;
        try {
            user = objectMapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RestAuthenticationException("Error while authenticating user");
        }
        return user;
    }

    private UsernamePasswordAuthenticationToken createAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken( //
                user.getUsername(), //
                user.getPassword()
        );
    }
}
