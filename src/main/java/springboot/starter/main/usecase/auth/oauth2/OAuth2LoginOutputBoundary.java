package springboot.starter.main.usecase.auth.oauth2;

public interface OAuth2LoginOutputBoundary
{
    void prepareSuccessView(String authToken, String username);
    void prepareFailureView(String errorMessage);
}
