package springboot.starter.main.usecase.auth.email_confirmation;

public interface ConfirmEmailOutputBoundary
{
    void prepareSuccessView();
    void prepareFailureView(String errorMessage);
}
