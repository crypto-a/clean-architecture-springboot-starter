package springboot.starter.main.infrastructure.auth.gateway;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "users")
public class UserDataMapper
{
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "user_sequence")
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "isEmailVerified", nullable = false)
    private Boolean isEmailVerified;

    // Oauth Fields
    @Column(name = "oauth_provider")
    private String oauthProvider;

    @Column(name = "oauth_provider_id")
    private String oauthProviderId;


    // Constructors
    public UserDataMapper()
    {
    }

    public UserDataMapper(String email, String username, String passwordHash, Boolean isEmailVerified)
    {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEmailVerified = isEmailVerified;
    }

    public UserDataMapper(Long id, String email, String username, String passwordHash, Boolean isEmailVerified)
    {
        this.id = id;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEmailVerified = isEmailVerified;
    }

    // Constructor for OAuth
    public UserDataMapper(String email, String username, String passwordHash, Boolean isEmailVerified,
                          String oauthProvider, String oauthProviderId)
    {
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.isEmailVerified = isEmailVerified;
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;
    }


    public String getOauthProvider()
    {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider)
    {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthProviderId()
    {
        return oauthProviderId;
    }

    public void setOauthProviderId(String oauthProviderId)
    {
        this.oauthProviderId = oauthProviderId;
    }

    public Long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public Boolean getEmailVerified()
    {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified)
    {
        isEmailVerified = emailVerified;
    }
}
