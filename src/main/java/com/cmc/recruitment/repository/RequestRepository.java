package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.QueryConstants;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {

	@Query(QueryConstants.Request.FIND_ALL_REQUEST)
	Page<Request> findAllRequest(Pageable pageable);

	@Query(QueryConstants.Request.FIND_REQUEST_OF_DEPARTMENT)
	List<Request> findRequestsOfDepartment(@Param(Constants.PARAM.DEPARTMENT_ID_PARAM) Long departmentId);

	Request findById(Long id);

	@Query(QueryConstants.Request.FIND_REQUEST_BY_STATUS)
	Page<Request> findByStatus(@Param(Constants.PARAM.STATUS_ID_PARAM) Long statusId, Pageable pageable);

	Request findByTitle(String title);

	@Query(QueryConstants.Request.FIND_BY_LIST_TITLE)
	List<Request> findByListTitle(@Param(Constants.PARAM.LIST_TITLE_ID_PARAM) Long[] listTitleId);

	@Query(QueryConstants.Request.COUNT_NUMBER_OF_REQUEST_IN_RECENT_YEAR)
	Integer countNumberOfRequestInRecentYear(@Param(Constants.PARAM.RECENT_YEAR_PARAM) Integer recentYear);

	List<Request> findByCreatedBy(User createdBy);
}
