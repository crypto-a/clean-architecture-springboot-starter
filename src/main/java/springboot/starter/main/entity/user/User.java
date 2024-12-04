package springboot.starter.main.entity.user;

public class User
{

    // properties
    private Long id;
    private final String email;
    private final String username;
    private final String passwordHash;
    private boolean isEmailVerified;

    // Oauth Properties
    private String oauthProvider;
    private String oauthProviderId;

    // Constructor for email/password users
    public User(String email, String username, String passwordHash)
    {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEmailVerified = false; // default to false
    }

    // Constructor for OAuth2 users
    public User(String email, String username, String oauthProvider, String oauthProviderId)
    {
        this.email = email;
        this.username = username;
        this.passwordHash = ""; // No password
        this.isEmailVerified = true; // Assuming OAuth2 provides verified email
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;
    }

    // constructor for db
    public User(Long id, String email, String username, String passwordHash, boolean isEmailVerified)
    {
        this.id = id;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEmailVerified = isEmailVerified;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthProviderId() {
        return oauthProviderId;
    }

    public void setOauthProviderId(String oauthProviderId) {
        this.oauthProviderId = oauthProviderId;
    }
}
