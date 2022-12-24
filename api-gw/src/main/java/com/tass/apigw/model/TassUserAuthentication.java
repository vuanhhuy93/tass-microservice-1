package com.tass.apigw.model;

import com.tass.apigw.security.UserDetailExtend;
import java.util.ArrayList;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class TassUserAuthentication extends UsernamePasswordAuthenticationToken {

    public TassUserAuthentication(UserDetailExtend userDetailExtend  ) {
        super(userDetailExtend, null , new ArrayList<>());
    }
}
