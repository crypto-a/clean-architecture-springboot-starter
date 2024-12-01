package springboot.starter.main.usecase.auth.register;

import springboot.starter.main.usecase.auth.dto.UserRegisterRequestModel;

public interface UserRegisterInputBoundary
{
    void register(UserRegisterRequestModel requestModel);
}
