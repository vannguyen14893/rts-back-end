/**
 * 
 */
package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Group;
import com.cmc.recruitment.repository.GroupRepository;
import com.cmc.recruitment.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

  @Autowired
  private GroupRepository groupRepository;
  /**
   * @description: .
   * @author: nvquy
   * @created_date: May 15, 2018
   * @modifier: User
   * @modifier_date: 
   */
  @Override
  public List<Group> findAll() {
    return groupRepository.findAll();
  }
@Override
public Group findOne(Long id) {
	return groupRepository.findOne(id);
}
}
