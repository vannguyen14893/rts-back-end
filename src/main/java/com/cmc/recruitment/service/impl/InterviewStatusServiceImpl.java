package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.InterviewStatus;
import com.cmc.recruitment.repository.InterviewStatusRepository;
import com.cmc.recruitment.service.InterviewStatusService;

@Service
public class InterviewStatusServiceImpl implements InterviewStatusService {

  @Autowired
  InterviewStatusRepository interviewStatusRepository;

  /**
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public List<InterviewStatus> findAll() {
    // TODO Auto-generated method stub
    return interviewStatusRepository.findAll();
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
  public Page<InterviewStatus> findAll(Pageable pageable) {
    return interviewStatusRepository.findAll(pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param interviewStatus
   * @return
   */
  @Override
  public InterviewStatus createOrUpdate(InterviewStatus interviewStatus) {
    return interviewStatusRepository.save(interviewStatus);
  }
  /**
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:21/6/2018
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public InterviewStatus findByTitle(String title) {
    return interviewStatusRepository.findByTitle(title);
  }

}
