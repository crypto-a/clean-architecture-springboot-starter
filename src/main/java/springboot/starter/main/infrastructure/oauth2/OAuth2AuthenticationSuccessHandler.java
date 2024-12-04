package springboot.starter.main.infrastructure.oauth2;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import springboot.starter.main.infrastructure.security.JWTUtils;

import java.io.IOException;


@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    private final JWTUtils jwtUtils;

    public OAuth2AuthenticationSuccessHandler(JWTUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        // Get the Oauth 2 user
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        // Generate the JWT Token
        String token = jwtUtils.generateToken(email, null);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}
