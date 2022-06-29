package com.jawapro.jawapro.Repository;

import com.jawapro.jawapro.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findUserByUsername(String username);
    Optional<User> findByUsername(String username);
}
