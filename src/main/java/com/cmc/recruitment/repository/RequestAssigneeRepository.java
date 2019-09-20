package com.cmc.recruitment.repository;

import java.util.Set;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.recruitment.entity.RequestAssignee;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.QueryConstants;

@Repository
public interface RequestAssigneeRepository  extends JpaRepository<RequestAssignee , Long>{
	@Query(QueryConstants.RequestAssignee.FIND_BY_REQUEST_ID_AND_ASSIGNEE_ID)
	RequestAssignee findByRequestIdAndAssigneeId(@Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId, @Param(Constants.PARAM.ASSIGNEE_ID_PARAM) Long assigneeId);
	
	@Query(QueryConstants.RequestAssignee.FIND_ALL_REQUEST_ASSIGNEE_OF_REQUEST)
	Set<RequestAssignee> findAllRequestAssigneeOfRequest(@Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId);
	
	@Transactional
  @Modifying
	@Query(QueryConstants.RequestAssignee.DELETE_ASSIGNEE_BY_REQUEST)
  void deleteAllAssigneeByRequest(@Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId);
}
