package com.cmc.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.CandidateStatus;

@Repository
public interface CandidateStatusRepository extends JpaRepository<CandidateStatus, Long> {

  CandidateStatus findById(Long id);
  
  CandidateStatus findByTitle(String title);
}
