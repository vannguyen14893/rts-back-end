package com.cmc.recruitment.service;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Log;

public interface LogService {
	public Page<Log> findAll(Long requestId, Long candidatesId, Long cvId, Long interviewId, String action, Pageable pageable);

  public Log save(Log log);
}
