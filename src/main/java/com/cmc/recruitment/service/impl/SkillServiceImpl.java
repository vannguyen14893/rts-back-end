package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Skill;
import com.cmc.recruitment.repository.SkillRepository;
import com.cmc.recruitment.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

  @Autowired
  SkillRepository skillRepository;

  @Override
  public List<Skill> findAll() {
    return skillRepository.findAll();
  }

  @Override
  public List<Skill> listAllSkill() {
    return skillRepository.findAll();
  }

  @Override
  public boolean addSkill(Skill skill) {
	Skill result = skillRepository.save(skill);
    return result != null;
  }

  @Override
  public boolean updateSkill(Skill skill) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void deleteSkill(Skill skill) {
    // TODO Auto-generated method stub

  }

  @Override
  public Skill findOne(long id) {
    return skillRepository.findById(id);
  }

  @Override
  public Skill getSkillByTitle(String title) {
    return skillRepository.findByTitle(title);
  }

@Override
public boolean checkIfExist(Skill skill) {
	List<Skill> list = skillRepository.findAll();
	for(Skill item : list) {
		if(skill.getTitle().equals(item.getTitle()))
			return true;
	}
	return false;
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
public Page<Skill> findAll(Pageable pageable) {
  return skillRepository.findAll(pageable);
}

/**
 * @description: .
 * @author: VDHoan
 * @created_date: Mar 23, 2018
 * @modifier: User
 * @modifier_date: Mar 23, 2018
 * @param skill
 * @return
 */
@Override
public Skill createOrUpdate(Skill skill) {
  return skillRepository.save(skill);
}
}
