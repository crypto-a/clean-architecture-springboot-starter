package springboot.starter.main.infrastructure.auth.presenter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import springboot.starter.main.shared.error.ErrorResponse;
import springboot.starter.main.usecase.auth.oauth2.OAuth2LoginOutputBoundary;

import java.util.Map;

@Component
public class OAuth2LoginPresenter implements OAuth2LoginOutputBoundary
{
    private ResponseEntity<?> responseEntity;

    @Override
    public void prepareSuccessView(String authToken, String username)
    {
        Map<String, String> response = Map.of(
                "token", authToken,
                "username", username,
                "message", "OAuth2 Login Successful!"
        );
        this.responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public void prepareFailureView(String errorMessage)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "OAuth2LoginError",
                errorMessage
        );

        this.responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    public ResponseEntity<?> getResponseEntity()
    {
        return responseEntity;
    }
}
