package com.cmc.recruitment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Log;
import com.cmc.recruitment.repository.LogRepository;
import com.cmc.recruitment.service.LogService;
import com.cmc.recruitment.specification.LogSpecification;

@Service
public class LogServiceImpl implements LogService {

  @Autowired
  private LogRepository logRepository;

  @Override
  public Page<Log> findAll(Long requestId, Long candidatesId, Long cvId, Long interviewId, String action, Pageable pageable) {
	    Specification<Log> spec = new LogSpecification(requestId, candidatesId, cvId, interviewId, action);
    return logRepository.findAll(spec, pageable);
  }

  @Override
  public Log save(Log log) {
    return logRepository.save(log);
  }
}
