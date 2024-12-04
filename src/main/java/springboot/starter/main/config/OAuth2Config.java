package springboot.starter.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboot.starter.main.infrastructure.auth.gateway.UserAuthDsGateway;
import springboot.starter.main.infrastructure.auth.presenter.OAuth2LoginPresenter;
import springboot.starter.main.infrastructure.security.JWTUtils;
import springboot.starter.main.usecase.auth.oauth2.OAuth2LoginInputBoundary;
import springboot.starter.main.usecase.auth.oauth2.OAuth2LoginInteractor;

@Configuration
public class OAuth2Config
{

    @Bean
    public OAuth2LoginInputBoundary oAuth2LoginInteractor(UserAuthDsGateway userAuthDsGateway,
                                                          JWTUtils jwtUtils,
                                                          OAuth2LoginPresenter presenter)
    {
        return new OAuth2LoginInteractor(userAuthDsGateway, jwtUtils, presenter);
    }
}
