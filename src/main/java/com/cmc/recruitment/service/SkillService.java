package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Skill;

public interface SkillService {
  List<Skill> findAll();

  List<Skill> listAllSkill();

  boolean addSkill(Skill skill);

  boolean updateSkill(Skill skill);

  void deleteSkill(Skill skill);

  Skill findOne(long id);

  Skill getSkillByTitle(String title);
  
  boolean checkIfExist(Skill skill);
  
  Page<Skill> findAll(Pageable pageable);
  
  Skill createOrUpdate(Skill skill);
}
