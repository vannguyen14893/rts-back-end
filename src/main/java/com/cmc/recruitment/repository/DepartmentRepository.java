package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
  Department findByTitle(String title);
  
  @Query("SELECT r FROM Department r WHERE r.isDelete = 0")
  Page<Department> findAll(Pageable pageable);
  
  @Query("SELECT r FROM Department r WHERE r.isDelete = 0")
  List<Department> findAll();

}
