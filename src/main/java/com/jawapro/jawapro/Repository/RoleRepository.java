package com.jawapro.jawapro.Repository;

import com.jawapro.jawapro.Entity.Role;
import com.jawapro.jawapro.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleEnum name);
}
