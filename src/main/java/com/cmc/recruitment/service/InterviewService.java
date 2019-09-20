package com.cmc.recruitment.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.entity.User;

public interface InterviewService {
  Page<Interview> filterInterview(Long departmentId, Long requestId, Long interviewerId,
      Long interviewStatusId, String fromDate, String toDate, String candidateName,
      Long candidateId, String title, Pageable pageable);

  Interview findOne(Long interviewId);

  Interview save(Interview interview);

  Interview update(Interview interview);

  List<Long> validateTimeForInterviewer(Interview interview);
  
  List<User> validateTimeForInterviewerReturnUser(Interview interview);

  Page<Interview> findInterviewByCandidate(Long candidateId, Pageable pageable);
  
  Interview addCandidate(List<Candidate> candidates, Long interviewId); 
  
  int sendMailAddinterviewer(String url, String[] to, String startTime, String endTime, String location) throws MessagingException;
  
  int sendMeetingRequest(String from, String[] to, String subject, String start, String end, String location, String url);
}
