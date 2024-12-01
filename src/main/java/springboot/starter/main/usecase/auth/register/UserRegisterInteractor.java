package springboot.starter.main.usecase.auth.register;

import springboot.starter.main.entity.user.User;
import springboot.starter.main.entity.user.UserFactory;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.email.EmailSenderService;
import springboot.starter.main.infrastructure.security.JWTUtils;
import springboot.starter.main.infrastructure.security.exception.InvalidPasswordException;
import springboot.starter.main.shared.validation.EmailValidator;
import springboot.starter.main.shared.validation.PasswordValidator;
import springboot.starter.main.usecase.auth.dto.UserRegisterRequestModel;

import java.util.Map;

public class UserRegisterInteractor implements UserRegisterInputBoundary
{
    private final UserAuthDsGateway userAuthDsGateway;
    private final UserRegisterOutputBoundary presenter;
    private final EmailSenderService emailSenderService;
    private final JWTUtils jwtUtils;
    private final UserFactory userFactory;

    public UserRegisterInteractor(UserAuthDsGateway userAuthDsGateway,
                                  UserRegisterOutputBoundary presenter,
                                  EmailSenderService emailSenderService,
                                  UserFactory userFactory,
                                  JWTUtils jwtUtils)
    {
        this.userAuthDsGateway = userAuthDsGateway;
        this.presenter = presenter;
        this.emailSenderService = emailSenderService;
        this.userFactory = userFactory;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void register(UserRegisterRequestModel requestModel)
    {
        // make sure username us unique
        if (userAuthDsGateway.existsByUsername(requestModel.getUsername()))
        {
            presenter.prepareUsernameAlreadyExistsView(requestModel.getUsername());
            return;
        }

        // make sure email is unique
        if (userAuthDsGateway.existsByEmail(requestModel.getEmail()))
        {
            presenter.prepareEmailAlreadyExistsView(requestModel.getEmail());
            return;
        }

        // Validate the email format
        if (!EmailValidator.isValid(requestModel.getEmail()))
        {
            presenter.prepareInvalidEmailFormatView(requestModel.getEmail());
            return;
        }


        // Validate the password
        try
        {
            PasswordValidator.isValid(requestModel.getPassword());

        } catch (InvalidPasswordException e)
        {
            presenter.prepareInvalidPasswordView(e.getMessage());
            return;
        }

        User newUser = userFactory.createUser(
                requestModel.getEmail(),
                requestModel.getUsername(),
                requestModel.getPassword()
        );

        userAuthDsGateway.save(newUser);

        // Generate email confirmation token
        String token = jwtUtils.generateToken(newUser.getEmail(), Map.of());
        String confirmationUrl = "http://localhost:8080/api/v1/auth/confirm?token=" + token;

        // Send confirmation email
        emailSenderService.sendEmail(
                newUser.getEmail(),
                "Email Confirmation",
                "confirmation_email",
                Map.of("username", newUser.getUsername(), "confirmation_link", confirmationUrl)
        );

        // Prepare the success view
        presenter.prepareSuccessView(newUser);
    }
}
