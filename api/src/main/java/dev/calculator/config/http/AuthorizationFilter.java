package dev.calculator.config.http;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.calculator.config.WebSecurityConfig;
import dev.calculator.config.auth.ApplicationUserDetailsService;
import dev.calculator.config.error.RestAuthenticationException;
import dev.calculator.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public static final String SECURITY_HEADER_NAME = WebSecurityConfig.SECURITY_HEADER_NAME;

    private final ApplicationUserDetailsService applicationUserDetailsService;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               ApplicationUserDetailsService applicationUserDetailsService)
    {
        super(authenticationManager);
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) //
            throws IOException, ServletException {
        String header = request.getHeader(SECURITY_HEADER_NAME);

        if (header != null) {
            UsernamePasswordAuthenticationToken authenticate = authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        String token = request.getHeader(SECURITY_HEADER_NAME);
        if (token != null) {
            DecodedJWT decodedToken = JWT.decode(token);
            Claim username = decodedToken.getClaim("username");
            if (username == null) {
                throw new RestAuthenticationException("Token is invalid");
            }

            UserDetails userDetails = applicationUserDetailsService.loadUserByUsername(username.asString());

            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), //
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
        }

        throw new RestAuthenticationException("Token is invalid");
    }

}
