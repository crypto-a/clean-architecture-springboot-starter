package springboot.starter.main.config;


import springboot.starter.main.entity.user.UserFactory;
import springboot.starter.main.infrastructure.auth.presenter.ConfirmEmailPresenter;
import springboot.starter.main.infrastructure.auth.presenter.UserRegisterPresenter;
import springboot.starter.main.infrastructure.auth.presenter.UserLoginPresenter;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.email.EmailSenderService;
import springboot.starter.main.infrastructure.security.JWTUtils;
import springboot.starter.main.usecase.auth.email_confirmation.ConfirmEmailInteractor;
import springboot.starter.main.usecase.auth.email_confirmation.ConfirmEmailOutputBoundary;
import springboot.starter.main.usecase.auth.login.UserLoginInteractor;
import springboot.starter.main.usecase.auth.register.UserRegisterInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig
{
    @Bean
    public UserRegisterPresenter userRegisterPresenter()
    {
        return new UserRegisterPresenter();
    }

    @Bean
    public UserRegisterInteractor userRegisterInteractor(UserAuthDsGateway userAuthDsGateway,
                                                         UserRegisterPresenter userRegisterPresenter,
                                                         EmailSenderService emailSenderService,
                                                         UserFactory userFactory,
                                                         JWTUtils jwtUtils)
    {
        return new UserRegisterInteractor(
                userAuthDsGateway,
                userRegisterPresenter,
                emailSenderService,
                userFactory,
                jwtUtils
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
    public ConfirmEmailPresenter confirmEmailPresenter()
    {
        return new ConfirmEmailPresenter();
    }

    @Bean
    public ConfirmEmailInteractor confirmEmailInteractor(
            UserAuthDsGateway userAuthDsGateway,
            JWTUtils jwtUtils,
            ConfirmEmailPresenter confirmEmailPresenter
    )
    {
        return new ConfirmEmailInteractor(
                userAuthDsGateway,
                jwtUtils,
                confirmEmailPresenter
                );
    }

    @Bean
    public UserFactory userFactory(PasswordEncoder passwordEncoder)
    {
        return new UserFactory(passwordEncoder);
    }
}
