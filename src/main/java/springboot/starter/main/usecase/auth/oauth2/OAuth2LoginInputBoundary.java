package springboot.starter.main.usecase.auth.oauth2;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2LoginInputBoundary
{
    void login(OAuth2User oauth2User);
}