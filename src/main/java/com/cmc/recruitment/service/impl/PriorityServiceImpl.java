/**
 * 
 */
package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Priority;
import com.cmc.recruitment.repository.PriorityRepository;
import com.cmc.recruitment.service.PriorityService;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Service
public class PriorityServiceImpl implements PriorityService{

  @Autowired
  PriorityRepository priorityRepository;
  /**
   * @description: .
   * @author: Lcnguyen
   * @created_date: Mar 6, 2018
   * @modifier: User
   * @modifier_date: Mar 6, 2018
   * @return
   */
  @Override
  public List<Priority> findAll() {
    return priorityRepository.findAll();
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
  public Page<Priority> findAll(Pageable pageable) {
    return priorityRepository.findAll(pageable);
  }
  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param priority
   * @return
   */
  @Override
  public Priority createOrUpdate(Priority priority) {
    return priorityRepository.save(priority);
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
  public Priority findByTitle(String title) {
    return priorityRepository.findByTitle(title);
  }
@Override
public Priority findOne(Long id) {
	return priorityRepository.findOne(id);
}

}
