package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.InterviewStatus;

public interface InterviewStatusService {

  List<InterviewStatus> findAll();
  
  Page<InterviewStatus> findAll(Pageable pageable);

  InterviewStatus createOrUpdate(InterviewStatus interviewStatus);
  
  InterviewStatus findByTitle(String title);
  
}
