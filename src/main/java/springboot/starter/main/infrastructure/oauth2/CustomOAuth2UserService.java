package springboot.starter.main.infrastructure.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import springboot.starter.main.entity.user.User;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.security.JWTUtils;
import springboot.starter.main.usecase.auth.register.UserRegisterInteractor;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService
{
    private final UserAuthDsGateway userAuthDsGateway;
    private final JWTUtils jwtUtils;

    // Constructor
    public CustomOAuth2UserService(UserAuthDsGateway userAuthDsGateway, JWTUtils jwtUtils)
    {
        this.userAuthDsGateway = userAuthDsGateway;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        // login
        OAuth2User oauth2User = super.loadUser(userRequest);
        // Process the OAuth2 user information
        processOAuth2User(oauth2User);
        // return the OAuth2 User
        return oauth2User;
    }

    private void processOAuth2User(OAuth2User oauth2User)
    {
        String email = oauth2User.getAttribute("email");
        String username = oauth2User.getAttribute("name");

        // Collect user from gateway
        Optional<User> existingUserOpt = userAuthDsGateway.findByEmail(email);

        // If the user exists
        if (existingUserOpt.isPresent())
        {
            // Collect the user
            User existingUser = existingUserOpt.get();

            // If the users email is not verified
            if (!existingUser.isEmailVerified())
            {
                // verify the users email
                existingUser.verifyEmail();

                // Save the user
                userAuthDsGateway.save(existingUser);
            }

            // ToDo: Update user information from Oauth Connection
            return;
        }

        // Register the user
        User newUser = new User(email, username, ""); // Password is not set for Oauth 2 users
        newUser.verifyEmail(); // Email is Verified for Oauth 2 users
        // Save user
        userAuthDsGateway.save(newUser);

        // ToDo: Send a welcome email to the user

    }
}
