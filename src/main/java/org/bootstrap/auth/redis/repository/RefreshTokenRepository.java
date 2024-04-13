package org.bootstrap.auth.redis.repository;

import org.bootstrap.auth.redis.entity.RefreshToken;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long>{
}
