package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.CandidateStatus;
import com.cmc.recruitment.repository.CandidateStatusRepository;
import com.cmc.recruitment.service.CandidateStatusService;

@Service
public class CandidateStatusServiceImpl implements CandidateStatusService {

	@Autowired
	private CandidateStatusRepository candidateStatusRepository;

	@Override
	public List<CandidateStatus> getAllStatus() {
		return candidateStatusRepository.findAll();
	}

	@Override
	public CandidateStatus findOneById(Long id) {
		return candidateStatusRepository.findById(id);
	}
  
  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param pageable
   * @return
   */
  @Override
  public Page<CandidateStatus> findAll(Pageable pageable) {
    return candidateStatusRepository.findAll(pageable);
  }
  
  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param title
   * @return
   */
  @Override
  public CandidateStatus findByTitle(String title) {
    return candidateStatusRepository.findByTitle(title);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param candidateStatus
   * @return
   */
  @Override
  public CandidateStatus createOrUpdate(CandidateStatus candidateStatus) {
    return candidateStatusRepository.save(candidateStatus);
  }

}
