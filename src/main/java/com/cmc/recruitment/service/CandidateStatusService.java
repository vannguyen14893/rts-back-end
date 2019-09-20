package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.CandidateStatus;

public interface CandidateStatusService {
	List<CandidateStatus> getAllStatus();

	CandidateStatus findOneById(Long id);
  
  Page<CandidateStatus> findAll(Pageable pageable);
  
  CandidateStatus findByTitle(String title);
  
  CandidateStatus createOrUpdate(CandidateStatus candidateStatus);

}
