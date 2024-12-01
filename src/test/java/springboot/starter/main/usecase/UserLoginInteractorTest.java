package springboot.starter.main.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboot.starter.main.entity.user.User;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.security.JWTUtils;
import springboot.starter.main.usecase.auth.dto.UserLoginRequestModel;
import springboot.starter.main.usecase.auth.login.UserLoginInteractor;
import springboot.starter.main.usecase.auth.login.UserLoginOutputBoundary;

import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserLoginInteractorTest
{

    private UserAuthDsGateway mockGateway;
    private PasswordEncoder mockPasswordEncoder;
    private JWTUtils mockJwtUtils;
    private UserLoginOutputBoundary mockPresenter;
    private UserLoginInteractor interactor;

    @BeforeEach
    void setup()
    {
        mockGateway = Mockito.mock(UserAuthDsGateway.class);
        mockPasswordEncoder = Mockito.mock(PasswordEncoder.class);
        mockJwtUtils = Mockito.mock(JWTUtils.class);
        mockPresenter = Mockito.mock(UserLoginOutputBoundary.class);
        interactor = new UserLoginInteractor(mockGateway, mockPasswordEncoder, mockPresenter, mockJwtUtils);
    }

    @Test
    void testSuccessfulLoginWithUsername()
    {
        UserLoginRequestModel request = new UserLoginRequestModel("testuser", "Password1!");
        User mockUser = new User("test@example.com", "testuser", "hashedpassword");

        when(mockGateway.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(mockPasswordEncoder.matches("Password1!", "hashedpassword")).thenReturn(true);
        when(mockJwtUtils.generateToken("testuser", Map.of())).thenReturn("mocktoken");

        interactor.login(request);

        verify(mockPresenter, times(1)).prepareSuccessView("mocktoken", "testuser");
    }

    @Test
    void testSuccessfulLoginWithEmail()
    {
        UserLoginRequestModel request = new UserLoginRequestModel("test@example.com", "Password1!");
        User mockUser = new User("test@example.com", "testuser", "hashedpassword");

        when(mockGateway.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(mockPasswordEncoder.matches("Password1!", "hashedpassword")).thenReturn(true);
        when(mockJwtUtils.generateToken("testuser", Map.of())).thenReturn("mocktoken");

        interactor.login(request);

        verify(mockPresenter, times(1)).prepareSuccessView("mocktoken", "testuser");
    }

    @Test
    void testInvalidPassword()
    {
        UserLoginRequestModel request = new UserLoginRequestModel("testuser", "WrongPassword");
        User mockUser = new User("test@example.com", "testuser", "hashedpassword");

        when(mockGateway.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(mockPasswordEncoder.matches("WrongPassword", "hashedpassword")).thenReturn(false);

        interactor.login(request);

        verify(mockPresenter, times(1)).prepareInvalidCredentialsView("Invalid username/email or password.");
    }

    @Test
    void testUserNotFound()
    {
        UserLoginRequestModel request = new UserLoginRequestModel("nonexistentuser", "Password1!");

        when(mockGateway.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        interactor.login(request);

        verify(mockPresenter, times(1)).prepareInvalidCredentialsView("Invalid username/email or password.");
    }
}
