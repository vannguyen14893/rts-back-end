/**
 * 
 */
package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.RecruitmentType;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
public interface RecruitmentTypeService {
	List<RecruitmentType> findAll();

	RecruitmentType findOne(Long id);

	RecruitmentType save(RecruitmentType recruitmentType);
  
  Page<RecruitmentType> findAll(Pageable pageable);
  
  RecruitmentType findByTitle(String title);
  
  RecruitmentType createOrUpdate(RecruitmentType recruitmentType);

}
