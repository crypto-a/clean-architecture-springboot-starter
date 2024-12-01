package springboot.starter.main.usecase.auth.login;
import springboot.starter.main.infrastructure.security.exception.InvalidCredentialsException;
import springboot.starter.main.entity.user.User;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.security.JWTUtils;
import springboot.starter.main.shared.validation.EmailValidator;
import springboot.starter.main.usecase.auth.dto.UserLoginRequestModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;


public class UserLoginInteractor implements UserLoginInputBoundary
{
    private final UserAuthDsGateway userAuthDsGateway;
    private final PasswordEncoder passwordEncoder;
    private final UserLoginOutputBoundary presenter;
    private final JWTUtils jwtUtils;

    public UserLoginInteractor(UserAuthDsGateway userAuthDsGateway,
                               PasswordEncoder passwordEncoder,
                               UserLoginOutputBoundary presenter,
                               JWTUtils jwtUtils)
    {
        this.userAuthDsGateway = userAuthDsGateway;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.presenter = presenter;
    }

    @Override
    public void login(UserLoginRequestModel requestModel)
    {
        // identify if the login is being done by the username or password
        String usernameOrEmail = requestModel.getUsernameOrEmail();

        User user;

        try {
            // Fetch user by email or username
            user = fetchUserByUsernameOrEmail(usernameOrEmail);
        } catch (InvalidCredentialsException e)
        {
            presenter.prepareInvalidCredentialsView("Invalid username/email or password.");
            return;
        }

        // Validate the password
        if (!passwordEncoder.matches(requestModel.getPassword(), user.getPasswordHash())) {
            presenter.prepareInvalidCredentialsView("Invalid username/email or password.");
            return;
        }

        // Generate JWT token
        String authToken = jwtUtils.generateToken(user.getUsername(), Map.of());

        // Prepare the success response
        presenter.prepareSuccessView(authToken, user.getUsername());
    }

    private User fetchUserByUsernameOrEmail(String usernameOrEmail) {
        if (EmailValidator.isValid(usernameOrEmail))
        {
            return userAuthDsGateway.findByEmail(usernameOrEmail)
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid username/email or password."));
        } else
        {
            return userAuthDsGateway.findByUsername(usernameOrEmail)
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid username/email or password."));
        }
    }

}
