/**
 * 
 */
package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Project;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
public interface ProjectService {
  List<Project> findAll();
  
  Page<Project> findAll(Pageable pageable);
  
  Project createOrUpdate(Project project);
  
  Project findByTitle(String title);
  
  Project findOne(Long Id);
}
