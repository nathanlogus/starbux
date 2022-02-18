package com.starbux.starbuxbackend.repository;

import com.starbux.starbuxbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
