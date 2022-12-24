package com.tass.common.redis.repository;

import com.tass.common.redis.dto.UserLoginDTO;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginRepository extends CrudRepository<UserLoginDTO, String> {

}
