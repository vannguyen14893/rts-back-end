package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.QueryConstants;

@Repository
public interface InterviewRepository
    extends JpaRepository<Interview, Long>, JpaSpecificationExecutor<Interview> {
  @Query(QueryConstants.Interview.FIND_INTERVIEW_BY_CANDIDATE)
  Page<Interview> findInterviewByCandidate(@Param(Constants.PARAM.CANDIDATE_ID_PARAM) Long candidateId,
      Pageable pageable);

  @Query(QueryConstants.Interview.FIND_INTERVIEW_BY_INTERVIEWER)
  List<Interview> findInterviewByInterviewer(@Param(Constants.PARAM.USER_ID_PARAM) Long userId);
  
}
