package com.cmc.recruitment.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.RequestAssignee;
import com.cmc.recruitment.repository.RequestAssigneeRepository;
import com.cmc.recruitment.service.RequestAssigneeService;

@Service
public class RequestAssigneeServiceImpl implements RequestAssigneeService{

	@Autowired
	RequestAssigneeRepository requestAssigneeRepository;
	
	@Override
	public List<RequestAssignee> findAll() {
		return requestAssigneeRepository.findAll();
	}

	@Override
	public RequestAssignee findOne(Long id) {
		return requestAssigneeRepository.findOne(id);
	}

	@Override
	public RequestAssignee save(RequestAssignee requestAssignee) {
		return requestAssigneeRepository.save(requestAssignee);
	}

	@Override
	public RequestAssignee findByRequestIdAndAssigneeId(Long requestId, Long assigneeId) {
		return requestAssigneeRepository.findByRequestIdAndAssigneeId(requestId, assigneeId);
	}

	@Override
	public Set<RequestAssignee> findAllRequestAssigneeOfRequest(Long requestId) {
		return requestAssigneeRepository.findAllRequestAssigneeOfRequest(requestId);
	}

	@Override
	public void deleteRequestAssignee(RequestAssignee ra) {
		requestAssigneeRepository.delete(ra);
	}

  @Override
  public RequestAssignee changeTarget(RequestAssignee ra) {
    RequestAssignee foundRequestAssignee = requestAssigneeRepository.findOne(ra.getId());
    if (foundRequestAssignee != null) {
      foundRequestAssignee.setNumberOfCandidate(ra.getNumberOfCandidate());
      return requestAssigneeRepository.save(foundRequestAssignee);
    }
    return null;
  }

  @Override
  public void deleteAssigneByRequest(Long requestId) {
    requestAssigneeRepository.deleteAllAssigneeByRequest(requestId);
  }

}
