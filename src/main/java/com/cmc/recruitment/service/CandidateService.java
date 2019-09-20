package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Cv;
import com.cmc.recruitment.utils.ReportByGroup;

public interface CandidateService {

  Candidate findOne(long id);

  // List<Candidate> findAll();

  Candidate saveCandidate(Candidate candidate);

  boolean isCandidateExist(Candidate candidate);

  // Page<Candidate> findAll(Long experienceId, Long requestId, Long statusId,
  // String fullName,
  // Pageable pageable) throws Exception;

  Page<Candidate> findCandidatesByRequestId(Long requestId, Pageable pageable) throws Exception;

  Page<Candidate> findCandidatesByRequestIdAndStatusId(Long requestId, Long statusId,
      Pageable pageable) throws Exception;

  List<Candidate> findByRequestId(Long requestId);

  List<Candidate> findByRequestIdToMakeInterview(Long requestId);

  Candidate changeStatusId(Long id, Long statusId);

  List<Candidate> changeStatus(List<Candidate> candidates, Long statusId);

  Page<Candidate> filterCandidate(Long experienceId, Long requestId, Long statusId, String fullName,
      Long hrmemberId, Pageable pageable);

  List<String> makeCandidate(Long requestId, Long[] arrayCvIds);

  List<Candidate> findByCvId(Cv cvId);

  void changeCreatedBy(Long requestId, Long asigneeIdOld, Long asigneeIdNew);

  Candidate updateCandidate(Candidate candidate);

  Page<Candidate> getReportAM(Long hrmemberId, String fromDate, String toDate, Long candidateStatusId, Pageable pageable);
  
  List<Candidate> findAll(Long experienceId, Long requestId, Long statusId, String fullName, Long hrmemberId, String fromDate, String toDate);
  
//  ReportByGroup statisticCandidateByDepartment(Long departmentId, String fromDate, String toDate);
  
  List<ReportByGroup> statisticCandidateByGroup(Long year);
}
