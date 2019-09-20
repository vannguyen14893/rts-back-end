/**
 * 
 */
package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Project;
import com.cmc.recruitment.repository.ProjectRepository;
import com.cmc.recruitment.service.ProjectService;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Service
public class ProjectServiceImpl implements ProjectService{

  @Autowired
  private ProjectRepository projectRepository;
  /**
   * @description: .
   * @author: Lcnguyen
   * @created_date: Mar 6, 2018
   * @modifier: User
   * @modifier_date: Mar 6, 2018
   * @return
   */
  @Override
  public List<Project> findAll() {
    return projectRepository.findAll();
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
  public Page<Project> findAll(Pageable pageable) {
    return projectRepository.findAll(pageable);
  }
  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param project
   * @return
   */
  @Override
  public Project createOrUpdate(Project project) {
	project.setIsDelete(false);
    return projectRepository.save(project);
  }
  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param name
   * @return
   */
  @Override
  public Project findByTitle(String title) {
    return projectRepository.findByTitle(title);
  }
@Override
public Project findOne(Long Id) {
	return projectRepository.findOne(Id);
}
  
}
