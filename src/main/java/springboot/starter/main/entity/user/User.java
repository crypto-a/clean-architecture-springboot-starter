package springboot.starter.main.entity.user;

public class User
{

    // properties
    private final String email;
    private final String username;
    private final String passwordHash;
    private boolean isEmailVerified;

    // Constructor
    public User(String email, String username, String passwordHash)
    {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEmailVerified = false; // default to false
    }

    // constructor for db
    public User(String email, String username, String passwordHash, boolean isEmailVerified)
    {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEmailVerified = isEmailVerified;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public boolean isEmailVerified()
    {
        return isEmailVerified;
    }

    public void verifyEmail()
    {
        this.isEmailVerified = true;
    }
}
