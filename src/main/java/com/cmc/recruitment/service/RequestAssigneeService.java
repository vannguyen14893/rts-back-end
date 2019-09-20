package com.cmc.recruitment.service;

import java.util.List;
import java.util.Set;

import com.cmc.recruitment.entity.RequestAssignee;

public interface RequestAssigneeService {
	List<RequestAssignee> findAll();
	RequestAssignee findOne(Long id);
	RequestAssignee findByRequestIdAndAssigneeId(Long requestId, Long assigneeId);
	RequestAssignee save(RequestAssignee requestAssignee);
	Set<RequestAssignee> findAllRequestAssigneeOfRequest(Long requestId);
	void deleteRequestAssignee(RequestAssignee ra);
	RequestAssignee changeTarget(RequestAssignee ra);
	void deleteAssigneByRequest(Long requestId);
}
