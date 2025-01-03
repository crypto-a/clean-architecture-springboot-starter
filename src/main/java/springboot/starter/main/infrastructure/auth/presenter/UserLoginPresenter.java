package springboot.starter.main.infrastructure.auth.presenter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import springboot.starter.main.shared.error.ErrorResponse;
import springboot.starter.main.usecase.auth.dto.UserLoginResponseModel;
import springboot.starter.main.usecase.auth.login.UserLoginOutputBoundary;

@Component
public class UserLoginPresenter implements UserLoginOutputBoundary
{
    private ResponseEntity<?> responseEntity;

    @Override
    public void prepareSuccessView(String authToken, String username)
    {
        UserLoginResponseModel userLoginResponseModel = new UserLoginResponseModel(
                authToken,
                username,
                "Login Successful!"
        );

        this.responseEntity = ResponseEntity.status(HttpStatus.OK).body(userLoginResponseModel);
    }

    @Override
    public void prepareInvalidCredentialsView(String errorMessage)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid Credentials",
                errorMessage
        );

        this.responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @Override
    public void prepareEmailNotVerifiedView(String errorMessage)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "EmailNotVerified",
                errorMessage
        );
        this.responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    public ResponseEntity<?> getResponseEntity()
    {
        return responseEntity;
    }
}
