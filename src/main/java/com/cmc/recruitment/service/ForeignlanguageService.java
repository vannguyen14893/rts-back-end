/**
 * 
 */
package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.ForeignLanguage;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
public interface ForeignlanguageService {
  List<ForeignLanguage> findAll();
  
  Page<ForeignLanguage> findAll(Pageable pageable);

  ForeignLanguage createOrUpdate(ForeignLanguage foreignLanguage);
  
  ForeignLanguage findByTitle(String title);

}
