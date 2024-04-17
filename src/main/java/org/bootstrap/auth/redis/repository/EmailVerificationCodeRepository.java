package org.bootstrap.auth.redis.repository;

import org.bootstrap.auth.redis.entity.EmailVerificationCode;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface EmailVerificationCodeRepository extends CrudRepository<EmailVerificationCode, String>{
}
