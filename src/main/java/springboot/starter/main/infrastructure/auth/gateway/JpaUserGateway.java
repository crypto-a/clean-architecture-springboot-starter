package springboot.starter.main.infrastructure.auth.gateway;

import org.springframework.stereotype.Component;
import springboot.starter.main.entity.user.User;

import java.util.Optional;

@Component
public class JpaUserGateway implements UserAuthDsGateway
{
    private final JpaUserRepository userRepository;

    public JpaUserGateway(JpaUserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByUsername(String username)
    {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email)
    {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(User user)
    {
        UserDataMapper userDataMapper;

        if (user.getId() != null)
        {
            // Fetch existing user
            userDataMapper = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found in database"));

            // Update fields
            userDataMapper.setEmailVerified(user.isEmailVerified());
            // Update other fields if necessary
        } else
        {
            // New user
            userDataMapper = new UserDataMapper(
                    user.getEmail(),
                    user.getUsername(),
                    user.getPasswordHash(),
                    user.isEmailVerified()
            );
        }

        // Save the userDataMapper
        userRepository.save(userDataMapper);

        // Update the user's id (in case of new user)
        user.setId(userDataMapper.getId());
    }

    @Override
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email)
                .map(userDataMapper -> new User(
                        userDataMapper.getId(),
                        userDataMapper.getEmail(),
                        userDataMapper.getUsername(),
                        userDataMapper.getPasswordHash(),
                        userDataMapper.getEmailVerified()
                ));
    }

    @Override
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username)
                .map(userDataMapper -> new User(
                        userDataMapper.getId(),
                        userDataMapper.getEmail(),
                        userDataMapper.getUsername(),
                        userDataMapper.getPasswordHash(),
                        userDataMapper.getEmailVerified()
                ));
    }
}
