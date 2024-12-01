package springboot.starter.main.usecase.auth.register;

import springboot.starter.main.entity.user.User;
import springboot.starter.main.entity.user.UserFactory;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.security.exception.InvalidPasswordException;
import springboot.starter.main.shared.validation.EmailValidator;
import springboot.starter.main.shared.validation.PasswordValidator;
import springboot.starter.main.usecase.auth.dto.UserRegisterRequestModel;

public class UserRegisterInteractor implements UserRegisterInputBoundary
{
    private final UserAuthDsGateway userAuthDsGateway;
    private final UserRegisterOutputBoundary presenter;
    private final UserFactory userFactory;

    public UserRegisterInteractor(UserAuthDsGateway userAuthDsGateway,
                                  UserRegisterOutputBoundary presenter,
                                  UserFactory userFactory)
    {
        this.userAuthDsGateway = userAuthDsGateway;
        this.presenter = presenter;
        this.userFactory = userFactory;
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

        // Prepare the success view
        presenter.prepareSuccessView(newUser);
    }
}
