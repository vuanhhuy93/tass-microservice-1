package com.tass.userservice.controllers;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.redis.dto.UserLoginDTO;
import com.tass.userservice.model.request.LoginRequest;
import com.tass.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public BaseResponseV2<UserLoginDTO> login(@RequestBody LoginRequest loginRequest) throws
        ApplicationException{
        return userService.login(loginRequest);
    }
}
