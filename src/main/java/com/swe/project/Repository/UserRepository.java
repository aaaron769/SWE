package com.swe.project.Repository;

import com.swe.project.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmailEquals(String email);

    Integer countByEmail(String email);
}
