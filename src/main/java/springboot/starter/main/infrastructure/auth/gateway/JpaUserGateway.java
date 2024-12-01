package springboot.starter.main.infrastructure.auth.gateway;

import springboot.starter.main.entity.user.User;
import org.springframework.stereotype.Component;

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
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username)
                .map(userDataMapper -> new User(
                        userDataMapper.getEmail(),
                        userDataMapper.getUsername(),
                        userDataMapper.getPasswordHash(),
                        userDataMapper.getEmailVerified()
                ));
    }

    @Override
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email)
                .map(userDataMapper -> new User(
                        userDataMapper.getEmail(),
                        userDataMapper.getUsername(),
                        userDataMapper.getPasswordHash(),
                        userDataMapper.getEmailVerified()
                ));
    }

    @Override
    public void save(User user)
    {
        UserDataMapper userDataMapper = new UserDataMapper(
                user.getEmail(),
                user.getUsername(),
                user.getPasswordHash(),
                user.isEmailVerified()
        );

        userRepository.save(userDataMapper);
    }
}
