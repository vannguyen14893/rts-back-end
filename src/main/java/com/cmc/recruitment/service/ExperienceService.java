/**
 * 
 */
package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Experience;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
public interface ExperienceService {
  List<Experience> findAll();
  
  Page<Experience> findAll(Pageable pageable);

  Experience createOrUpdate(Experience experience);
  
  Experience findByTitle(String title);
  
  Experience findOne(Long id);
}
