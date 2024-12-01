package springboot.starter.main.usecase.auth.email_confirmation;

import springboot.starter.main.entity.user.User;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.security.JWTUtils;

public class ConfirmEmailInteractor implements ConfirmEmailInputBoundary
{
    private final UserAuthDsGateway userAuthDsGateway;
    private final JWTUtils jwtUtils;
    private final ConfirmEmailOutputBoundary presenter;

    public ConfirmEmailInteractor(UserAuthDsGateway userAuthDsGateway, JWTUtils jwtUtils, ConfirmEmailOutputBoundary presenter)
    {
        this.userAuthDsGateway = userAuthDsGateway;
        this.jwtUtils = jwtUtils;
        this.presenter = presenter;
    }

    @Override
    public void confirmEmail(String token)
    {
        try
        {
            // Extract email from token
            String email = jwtUtils.extractUserName(token);

            // Find the user by email
            User user = userAuthDsGateway.findByEmail(email)
                    .orElseThrow(() -> new IllegalStateException("User not found."));

            // Verify email
            user.verifyEmail();
            userAuthDsGateway.save(user);

            // Prepare success response
            presenter.prepareSuccessView();

        } catch (Exception e)
        {
            presenter.prepareFailureView("Invalid or expired token.");
        }
    }
}
