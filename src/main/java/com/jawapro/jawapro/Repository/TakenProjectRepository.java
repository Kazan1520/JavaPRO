package com.jawapro.jawapro.Repository;

import com.jawapro.jawapro.Entity.TakenProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TakenProjectRepository extends JpaRepository<TakenProject, Long> {
    List<TakenProject> findAllByStudentId(Long id);
}