/**
 * 
 */
package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Priority;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
public interface PriorityService {
  List<Priority> findAll();

  Page<Priority> findAll(Pageable pageable);

  Priority createOrUpdate(Priority priority);
  
  Priority findByTitle(String title);
  
  Priority findOne(Long id);
}
