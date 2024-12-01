package springboot.starter.main.infrastructure.auth.gateway;


import springboot.starter.main.entity.user.User;

import java.util.Optional;

public interface UserAuthDsGateway
{
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void save(User user);
}
