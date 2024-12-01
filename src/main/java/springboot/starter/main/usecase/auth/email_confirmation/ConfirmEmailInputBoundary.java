package springboot.starter.main.usecase.auth.email_confirmation;

public interface ConfirmEmailInputBoundary
{
    void confirmEmail(String token);
}
