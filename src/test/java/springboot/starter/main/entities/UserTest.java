package springboot.starter.main.entities;

import org.junit.jupiter.api.Test;
import springboot.starter.main.entity.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest
{

    @Test
    public void testUserCreation()
    {
        User user = new User("test@example.com", "testuser", "hashedpassword123");

        assertEquals("test@example.com", user.getEmail());
        assertEquals("testuser", user.getUsername());
        assertEquals("hashedpassword123", user.getPasswordHash());
    }
}
