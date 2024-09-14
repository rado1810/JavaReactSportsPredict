package bg.web.radoweb.service;

import bg.web.radoweb.model.Dto.*;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    void registerUser(UserRegisterDTO userRegisterDTO);

    void logout(HttpServletRequest request);

    boolean userExist(UserExistDto userExistDto);


}
