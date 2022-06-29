package com.jawapro.jawapro.Repository;

import com.jawapro.jawapro.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsersUsername(String username);
}