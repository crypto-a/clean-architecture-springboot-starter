package springboot.starter.main.config;


import springboot.starter.main.entity.user.UserFactory;
import springboot.starter.main.infrastructure.auth.presenter.UserRegisterPresenter;
import springboot.starter.main.infrastructure.auth.presenter.UserLoginPresenter;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.security.JWTUtils;
import springboot.starter.main.usecase.auth.login.UserLoginInteractor;
import springboot.starter.main.usecase.auth.register.UserRegisterInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig
{
    @Bean
    public UserRegisterPresenter userRegisterPresenter()
    {
        return new UserRegisterPresenter();
    }

    @Bean
    public UserRegisterInteractor userRegisterInteractor(UserAuthDsGateway userAuthDsGateway,
                                                         UserRegisterPresenter userRegisterPresenter,
                                                         UserFactory userFactory)
    {
        return new UserRegisterInteractor(
                userAuthDsGateway,
                userRegisterPresenter,
                userFactory
        );
    }

    @Bean
    public UserLoginPresenter userLoginPresenter()
    {
        return new UserLoginPresenter();
    }

    @Bean
    public UserLoginInteractor userLoginInteractor(UserAuthDsGateway userAuthDsGateway,
                                                   PasswordEncoder passwordEncoder,
                                                   UserLoginPresenter userLoginPresenter,
                                                   JWTUtils jwtUtils)
    {
        return new UserLoginInteractor(
                userAuthDsGateway,
                passwordEncoder,
                userLoginPresenter,
                jwtUtils
        );
    }

    @Bean
    public UserFactory userFactory(PasswordEncoder passwordEncoder)
    {
        return new UserFactory(passwordEncoder);
    }
}
