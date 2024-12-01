package springboot.starter.main.infrastructure.security.exception;

public class InvalidCredentialsException extends RuntimeException
{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
