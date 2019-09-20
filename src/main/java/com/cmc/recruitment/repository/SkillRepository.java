package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

  Skill findById(Long id);

  Skill findByTitle(String titleSkill);
  
  @Query("SELECT r FROM Skill r WHERE r.isDelete = 0")
  Page<Skill> findAll(Pageable pageable);
  
  @Query("SELECT r FROM Skill r WHERE r.isDelete = 0")
  List<Skill> findAll();
}
