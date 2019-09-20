package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.RequestStatus;
import com.cmc.recruitment.repository.RequestStatusRepository;
import com.cmc.recruitment.service.RequestStatusService;

@Service
public class RequestStatusServiceImpl implements RequestStatusService {

	@Autowired
	RequestStatusRepository requestStatusRepository;

	@Override
	public List<RequestStatus> getAllRequestStatus() throws Exception {
		return requestStatusRepository.findAll();
	}

	@Override
	public RequestStatus findOne(Long id) {
		return requestStatusRepository.findOne(id);
	}

	@Override
	public RequestStatus save(RequestStatus requestStatus) {
		return requestStatusRepository.save(requestStatus);
	}

	@Override
	public RequestStatus findByTitle(String title) {
		return requestStatusRepository.findByTitle(title);
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
  public Page<RequestStatus> findAll(Pageable pageable) {
    return requestStatusRepository.findAll(pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param requestStatus
   * @return
   */
  @Override
  public RequestStatus createOrUpdate(RequestStatus requestStatus) {
    return requestStatusRepository.save(requestStatus);
  }

}
