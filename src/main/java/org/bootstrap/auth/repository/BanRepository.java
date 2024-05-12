package org.bootstrap.auth.repository;

import org.bootstrap.auth.entity.Ban;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BanRepository extends JpaRepository<Ban, Long> {
    Optional<Ban> findByMemberId(Long memberId);
}
