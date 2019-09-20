/**
 * 
 */
package com.cmc.recruitment.service;

import java.util.List;

import com.cmc.recruitment.entity.Group;

public interface GroupService {
  Group findOne(Long id);
  List<Group> findAll();
}
