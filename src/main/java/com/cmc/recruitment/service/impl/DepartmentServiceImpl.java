package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Department;
import com.cmc.recruitment.repository.DepartmentRepository;
import com.cmc.recruitment.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  @Autowired
  DepartmentRepository departmentRepository;

  @Override
  public Department findOne(long id) {
    return departmentRepository.findOne(id);
  }
  
  /**
   * 
   * @description:
   * @author: nvquy
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @params
   */
  @Override
  public List<Department> findAll() {
    return departmentRepository.findAll();
  }

  /**
   * @description: Find all departments .
   * @author: VDHoan
   * @created_date: Mar 20, 2018
   * @modifier: User
   * @modifier_date: Mar 20, 2018
   * @param pageable
   * @return
   */
  @Override
  public Page<Department> findAll(Pageable pageable) {
    return departmentRepository.findAll(pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 20, 2018
   * @modifier: User
   * @modifier_date: Mar 20, 2018
   * @param title
   * @return
   */
  @Override
  public Department findByTitle(String title) {
    return departmentRepository.findByTitle(title);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 20, 2018
   * @modifier: User
   * @modifier_date: Mar 20, 2018
   * @param department
   * @return
   */
  @Override
  public Department createOrUpdate(Department department) {
    return departmentRepository.save(department);
  }

}
