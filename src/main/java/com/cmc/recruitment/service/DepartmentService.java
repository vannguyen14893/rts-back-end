package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Department;

public interface DepartmentService {
  
  Department findOne(long id);
  
  List<Department> findAll();
  
  Page<Department> findAll(Pageable pageable);
  
  Department findByTitle(String title);
  
  Department createOrUpdate(Department department);
}
