package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Cv;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.QueryConstants;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {
	Candidate findById(Long id);
  
  @Query(QueryConstants.Candidate.FIND_ALL_BY_REQUEST_ID)
  Page<Candidate> findAllCandidateByRequestId(@Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId, Pageable pageable);
  
  @Query(QueryConstants.Candidate.FIND_ALL_BY_REQUEST_ID_AND_STATUS_ID)
  Page<Candidate> findAllCandidateByRequestIdAndStatusId(@Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId, @Param(Constants.PARAM.STATUS_ID_PARAM) Long statusId, Pageable pageable);

  @Query(QueryConstants.Candidate.FIND_ALL_BY_REQUEST)
  List<Candidate> findAllCandidateByRequest(@Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId);
  
  @Query(QueryConstants.Candidate.FIND_ALL_BY_REQUEST_TO_MAKE_INTERVIEW)
  List<Candidate> findAllCandidateByRequestToMakeInterview(@Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId);
  
  @Query("SELECT c FROM Candidate c WHERE c.requestId.id = :requestId AND c.cvId.id = :cvId")
  Candidate findCandidateByRequestAndCvId(@Param("requestId") Long requestId, @Param("cvId") Long cvId);
//  @Query
//  List<Candidate> findCandidateByRequestOfCurrentHrMember(@Param("requestId" Long requestId))
  
  @Query(QueryConstants.Candidate.FIND_CANDIDATE_BY_CREATE_USER)
  List<Candidate> findCandidateByCreateUser(@Param(Constants.PARAM.USER_ID_PARAM) Long userId, @Param(Constants.PARAM.REQUEST_ID_PARAM) Long requestId);
  
  @Query(QueryConstants.Candidate.UPDATE_STATUS_CANDIDATE)
  Candidate changeCandidateStatus(@Param(Constants.PARAM.STATUS_ID_PARAM) Long statusId, @Param(Constants.PARAM.ID_PARAM) Long id);
  
  @Query(QueryConstants.Candidate.UPDATE_CREATED_BY_CANDIDATE)
  void changeCreateByCandidate(Long requestId, Long asigneeIdOld, Long asigneeIdNew);

  List<Candidate> findByCvId(Cv cvId);
}
