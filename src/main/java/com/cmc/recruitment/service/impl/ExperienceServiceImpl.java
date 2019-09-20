/**
 * 
 */
package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Experience;
import com.cmc.recruitment.repository.ExperienceRepository;
import com.cmc.recruitment.service.ExperienceService;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Service
public class ExperienceServiceImpl implements ExperienceService{

  @Autowired
  private ExperienceRepository experienceRepository;
  
  /**
   * @description: .
   * @author: Lcnguyen
   * @created_date: Mar 6, 2018
   * @modifier: User
   * @modifier_date: Mar 6, 2018
   * @return
   */
  @Override
  public List<Experience> findAll() {
    return experienceRepository.findAll();
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param pageable
   * @return
   */
  @Override
  public Page<Experience> findAll(Pageable pageable) {
    return experienceRepository.findAll(pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param experience
   * @return
   */
  @Override
  public Experience createOrUpdate(Experience experience) {
    return experienceRepository.save(experience);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 28, 2018
   * @modifier: User
   * @modifier_date: Mar 28, 2018
   * @param title
   * @return
   */
  @Override
  public Experience findByTitle(String title) {
    return experienceRepository.findByTitle(title);
  }

@Override
public Experience findOne(Long id) {
	return experienceRepository.findOne(id);
}

}
