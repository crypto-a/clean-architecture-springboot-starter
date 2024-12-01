package springboot.starter.main.factories;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboot.starter.main.entity.user.User;
import springboot.starter.main.entity.user.UserFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserFactoryTest
{

    @Test
    public void testCreateUser()
    {
        PasswordEncoder mockEncoder = Mockito.mock(PasswordEncoder.class);
        when(mockEncoder.encode("password")).thenReturn("hashedpassword");

        UserFactory userFactory = new UserFactory(mockEncoder);
        User user = userFactory.createUser("test@example.com", "testuser", "password");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("testuser", user.getUsername());
        assertEquals("hashedpassword", user.getPasswordHash());
    }
}
