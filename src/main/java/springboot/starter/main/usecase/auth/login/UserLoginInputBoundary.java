package springboot.starter.main.usecase.auth.login;

import springboot.starter.main.usecase.auth.dto.UserLoginRequestModel;

public interface UserLoginInputBoundary
{
    void login(UserLoginRequestModel requestModel);
}
