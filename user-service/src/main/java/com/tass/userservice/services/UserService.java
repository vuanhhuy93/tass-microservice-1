package com.tass.userservice.services;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.redis.dto.UserLoginDTO;
import com.tass.common.redis.repository.UserLoginRepository;
import com.tass.userservice.entities.User;
import com.tass.userservice.model.request.LoginRequest;
import com.tass.userservice.model.request.UserRequest;
import com.tass.userservice.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserLoginRepository userLoginRepository;

    public BaseResponseV2 register(UserRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getUsername())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username is empty");
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password is empty");
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not match");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        userRepository.save(user);
        return new BaseResponseV2<>(user);
    }

    public BaseResponseV2<UserLoginDTO> login(LoginRequest request) throws ApplicationException {
        Optional<User> optionalUser = userRepository.findUserByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username not found");
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not match");
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        String token = UUID.randomUUID().toString();

        userLoginDTO.setToken(token);
        userLoginDTO.setUserId(user.getId());
        userLoginDTO.setTimeToLive(10000);

        userLoginRepository.save(userLoginDTO);

        BaseResponseV2<UserLoginDTO> loginResponse = new BaseResponseV2<>();
        loginResponse.setData(userLoginDTO);

        return loginResponse;
    }
}
