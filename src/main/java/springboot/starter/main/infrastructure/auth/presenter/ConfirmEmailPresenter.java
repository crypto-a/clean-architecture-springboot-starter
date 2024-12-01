package springboot.starter.main.infrastructure.auth.presenter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import springboot.starter.main.shared.error.ErrorResponse;
import springboot.starter.main.usecase.auth.email_confirmation.ConfirmEmailOutputBoundary;

@Component
public class ConfirmEmailPresenter implements ConfirmEmailOutputBoundary
{

    private ResponseEntity<?> responseEntity;

    @Override
    public void prepareSuccessView()
    {
        this.responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body("Email confirmation successful!");
    }

    @Override
    public void prepareFailureView(String errorMessage)
    {
        ErrorResponse errorResponse = new ErrorResponse(
                "EmailConfirmationError",
                errorMessage
        );
        this.responseEntity = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    public ResponseEntity<?> getResponseEntity()
    {
        return responseEntity;
    }
}
