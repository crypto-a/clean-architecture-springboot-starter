package springboot.starter.main.infrastructure.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springboot.starter.main.infrastructure.auth.presenter.OAuth2LoginPresenter;
import springboot.starter.main.usecase.auth.oauth2.OAuth2LoginInputBoundary;

@Controller
public class OAuth2Controller
{

    private final OAuth2LoginInputBoundary oAuth2LoginInteractor;
    private final OAuth2LoginPresenter presenter;

    @Autowired
    public OAuth2Controller(OAuth2LoginInputBoundary oAuth2LoginInteractor,
                            OAuth2LoginPresenter presenter)
    {
        this.oAuth2LoginInteractor = oAuth2LoginInteractor;
        this.presenter = presenter;
    }

    @GetMapping("/login/oauth2/code/google")
    public void handleGoogleCallback(OAuth2User oauth2User, HttpServletRequest request, HttpServletResponse response)
    {
        oAuth2LoginInteractor.login(oauth2User);
        // Send the presenter response back to the client
        try
        {
            response.setStatus(presenter.getResponseEntity().getStatusCodeValue());
            response.setContentType("application/json");
            response.getWriter().write(
                    new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(
                            presenter.getResponseEntity().getBody()
                    )
            );
            response.getWriter().flush();
        } catch (Exception e)
        {
            // Handle exceptions
        }
    }
}
