package springboot.starter.main.usecase.auth.oauth2;


import org.springframework.security.oauth2.core.user.OAuth2User;
import springboot.starter.main.entity.user.User;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.security.JWTUtils;

import java.util.Map;

public class OAuth2LoginInteractor implements OAuth2LoginInputBoundary
{
    private final UserAuthDsGateway userAuthDsGateway;
    private final JWTUtils jwtUtils;
    private final OAuth2LoginOutputBoundary presenter;

    // Constructor
    public OAuth2LoginInteractor(UserAuthDsGateway userAuthDsGateway,
                                 JWTUtils jwtUtils,
                                 OAuth2LoginOutputBoundary presenter)
    {
        this.userAuthDsGateway = userAuthDsGateway;
        this.jwtUtils = jwtUtils;
        this.presenter = presenter;
    }

    @Override
    public void login(OAuth2User oauth2User)
    {
        try
        {
            String email = oauth2User.getAttribute("email");
            String username = oauth2User.getAttribute("name");

            User user = userAuthDsGateway.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("User not found."));

            // Check if email is verified
            if (!user.isEmailVerified()) {
                presenter.prepareFailureView("Email not verified.");
                return;
            }

            // Generate JWT token
            String token = jwtUtils.generateToken(user.getEmail(), Map.of());

            // Prepare success response
            presenter.prepareSuccessView(token, user.getUsername());

        } catch (Exception e)
        {
            presenter.prepareFailureView("OAuth2 Authentication Failed.");
        }
    }
}
