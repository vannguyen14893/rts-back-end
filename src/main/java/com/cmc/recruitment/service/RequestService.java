package com.cmc.recruitment.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.utils.ReportDTO;

public interface RequestService {

  Request findOne(long id);

  List<Request> getAllRequest();

  boolean saveRequest(Request request);
  
  boolean updateRequest(Request request);

  public Request approveRequest(Request request);

  public Request rejectRequest(Request request);

  Page<Request> findByStatus(Long statusId, Pageable pageable);

  Request submitNewRequest(Request request);

  public List<Request> listRequestByTitleId(String[] listTitleRequest);

  int sendMailWaitApproved(String url, String toEmail) throws MessagingException;

  int sendMailApproved(String url, String to) throws MessagingException;

  int sendMailReject(String url, String comment, String to) throws MessagingException;

  int sendMailAfterAssign(String url, String[] to) throws MessagingException;

  Request closeRequest(Request request);

  Page<Request> filterRequest(List<Long> requestStatusId, Long skillId, Long positionId,
      Long daysAgo, String title, Long createdBy, Long assigneeId, List<Long> departmentId,
      Long priorityId, Long groupId, Pageable pageable);

  List<Request> findAssignedRequestsBelongAssignee(List<Long> requestStatusId, Long assigneeId);

  ArrayList<ReportDTO> getReport();

  ArrayList<ReportDTO> getReportHrMember(Long id);
  
  void removeOthersAsignee(Request request, Long assigneeId);
  
  Integer getNumberOfRequestInRecentYear(Integer recentYear);
  
  List<Request> listRequestByCreator(User creator);

  Page<Request> getReportRP(String fromDate, String toDate, Pageable pageable);
}
