package springboot.starter.main.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import springboot.starter.main.entity.user.User;
import springboot.starter.main.entity.user.UserFactory;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.usecase.auth.dto.UserRegisterRequestModel;
import springboot.starter.main.usecase.auth.register.UserRegisterInteractor;
import springboot.starter.main.usecase.auth.register.UserRegisterOutputBoundary;

import static org.mockito.Mockito.*;

class UserRegisterInteractorTest
{

    private UserAuthDsGateway mockGateway;
    private UserRegisterOutputBoundary mockPresenter;
    private UserFactory mockFactory;
    private UserRegisterInteractor interactor;

    @BeforeEach
    void setup()
    {
        mockGateway = Mockito.mock(UserAuthDsGateway.class);
        mockPresenter = Mockito.mock(UserRegisterOutputBoundary.class);
        mockFactory = Mockito.mock(UserFactory.class);
//        interactor = new UserRegisterInteractor(mockGateway, mockPresenter, mockFactory);
    }

    @Test
    void testSuccessfulRegistration()
    {
        UserRegisterRequestModel request = new UserRegisterRequestModel(
                "testuser",
                "Password1!", // Updated password to meet validation rules
                "test@example.com"
        );
        when(mockGateway.existsByUsername("testuser")).thenReturn(false);
        when(mockGateway.existsByEmail("test@example.com")).thenReturn(false);
        when(mockFactory.createUser("test@example.com", "testuser", "Password1!"))
                .thenReturn(new User("test@example.com", "testuser", "hashedpassword"));

        interactor.register(request);

        verify(mockPresenter, times(1)).prepareSuccessView(any(User.class));
    }

    @Test
    void testUsernameAlreadyExists()
    {
        UserRegisterRequestModel request = new UserRegisterRequestModel("testuser", "password", "test@example.com");
        when(mockGateway.existsByUsername("testuser")).thenReturn(true);

        interactor.register(request);

        verify(mockPresenter, times(1)).prepareUsernameAlreadyExistsView("testuser");
    }

    @Test
    void testInvalidPassword()
    {
        // Input with invalid password (less than 8 characters)
        UserRegisterRequestModel request = new UserRegisterRequestModel(
                "rahbarali",
                "1230!", // Invalid password (too short)
                "ali.rahbar@mail.utoronto.ca"
        );

        // Mock responses for gateway
        when(mockGateway.existsByUsername("rahbarali")).thenReturn(false);
        when(mockGateway.existsByEmail("ali.rahbar@mail.utoronto.ca")).thenReturn(false);

        // Call the interactor
        interactor.register(request);

        // Verify that the correct presenter method was called with the expected message
        verify(mockPresenter, times(1)).prepareInvalidPasswordView("Password must be between 8 and 32 characters.");
    }
}
