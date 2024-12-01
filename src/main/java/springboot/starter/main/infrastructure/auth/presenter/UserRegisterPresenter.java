package springboot.starter.main.infrastructure.auth.presenter;

import springboot.starter.main.entity.user.User;
import springboot.starter.main.shared.error.ErrorResponse;
import springboot.starter.main.usecase.auth.dto.UserRegisterResponseModel;
import springboot.starter.main.usecase.auth.register.UserRegisterOutputBoundary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterPresenter implements UserRegisterOutputBoundary
{

    private ResponseEntity<?> responseEntity;

    @Override
    public void prepareSuccessView(User user)
    {
        UserRegisterResponseModel responseModel = new UserRegisterResponseModel(
                user.getUsername(),
                "User registered successfully!"
        );
        this.responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    @Override
    public void prepareUsernameAlreadyExistsView(String username) {
        ErrorResponse errorResponse = new ErrorResponse(
                "UsernameAlreadyExists",
                "The username '" + username + "' is already taken."
        );
        this.responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @Override
    public void prepareEmailAlreadyExistsView(String email) {
        ErrorResponse errorResponse = new ErrorResponse(
                "EmailAlreadyExists",
                "The email '" + email + "' is already registered."
        );
        this.responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @Override
    public void prepareInvalidPasswordView(String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse("InvalidPassword", errorMessage);
        this.responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @Override
    public void prepareInvalidEmailFormatView(String email) {
        ErrorResponse errorResponse = new ErrorResponse(
                "InvalidEmailFormat",
                "The email '" + email + "' is not in a valid format."
        );
        this.responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    public ResponseEntity<?> getResponseEntity() {
        return responseEntity;
    }
}
