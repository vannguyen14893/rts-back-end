package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.RequestStatus;

public interface RequestStatusService {
	List<RequestStatus> getAllRequestStatus() throws Exception;

	RequestStatus findOne(Long id);

	RequestStatus save(RequestStatus requestStatus);
	
	RequestStatus findByTitle(String title);
  
  Page<RequestStatus> findAll(Pageable pageable);
  
  RequestStatus createOrUpdate(RequestStatus requestStatus);

}
