package com.cmc.recruitment.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.utils.ConvertHelper;

public class InterviewSpecification implements Specification<Interview> {
  private Long departmentId;
  private Long interviewerId;
  private Long requestId;
  private Long interviewStatusId;
  private String fromDate;
  private String toDate;
  private String candidateName;
  private Long candidateId;
  private String title;

  public InterviewSpecification() {
    super();
  }

  public InterviewSpecification(Long departmentId, Long interviewerId, Long requestId,
      Long interviewStatusId, String fromDate, String toDate, String candidateName,
      Long candidateId, String title) {
    
    super();
    this.departmentId = departmentId;
    this.interviewerId = interviewerId;
    this.requestId = requestId;
    this.interviewStatusId = interviewStatusId;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.candidateName = candidateName;
    this.candidateId = candidateId;
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCandidateName() {
    return candidateName;
  }

  public void setCandidateName(String candidateName) {
    this.candidateName = candidateName;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public Long getInterviewerId() {
    return interviewerId;
  }

  public void setInterviewerId(Long interviewerId) {
    this.interviewerId = interviewerId;
  }

  public Long getRequestId() {
    return requestId;
  }

  public void setRequestId(Long requestId) {
    this.requestId = requestId;
  }

  public Long getInterviewStatusId() {
    return interviewStatusId;
  }

  public void setInterviewStatusId(Long interviewStatusId) {
    this.interviewStatusId = interviewStatusId;
  }

  public String getFromDate() {
    return fromDate;
  }

  public void setFromDate(String fromDate) {
    this.fromDate = fromDate;
  }

  public String getToDate() {
    return toDate;
  }

  public void setToDate(String toDate) {
    this.toDate = toDate;
  }

  public Long getCandidateId() {
    return candidateId;
  }

  public void setCandidateId(Long candidateId) {
    this.candidateId = candidateId;
  }

  /**
   * @description:
   * @author: vthung
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public Predicate toPredicate(Root<Interview> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    // init
    Predicate predicate = cb.conjunction();
    // query by interview title
    if (title != null) {
      if (!title.isEmpty()) {
        predicate = cb.and(predicate, cb.like(root.<String>get("title"), '%' + title + '%'));
      }
    }
    
    // query by candidate name
    if (candidateName != null && !candidateName.isEmpty()) {
      predicate = cb.and(predicate,
          cb.like(root.join("candidateCollection").get("cvId").<String>get("fullName"),
              '%' + candidateName + '%'));
    }

    // Expression<Long> exp = root.<Long>get("requestStatusId").get("id");
    // predicate = cb.and(exp.in(requestStatusIds));

    // query interviews belong to department of current user
    if (this.departmentId != null) {
      predicate = cb.and(predicate, cb.equal(root.join("candidateCollection").<Long>get("requestId")
          .get("createdBy").get("departmentId").get("id"), this.departmentId));
    }
    // // query interviews has interviewer satisfy filter criteria by
    // interviewer
//    if (this.interviewerId != null) {
//      System.out.println("0");
//      predicate = cb.and(predicate,
//          cb.equal(root.join("Candidate_.userCollection").get("User_.id"), this.interviewerId));
//    }
    // query by request
    if (requestId != null) {
      predicate = cb.and(predicate,
          cb.equal(root.join("candidateCollection").<Long>get("requestId").get("id"), requestId));
    }
    // query interviews has status satisfy filter criteria by interview status
    if (this.interviewStatusId != null) {
      predicate = cb.and(predicate,
          cb.equal(root.<Long>get("statusId").get("id"), this.interviewStatusId));
    }
    // query interviews from date
    if (this.fromDate != null && !this.fromDate.isEmpty()) {
      predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.<Date>get("startTime"),
          ConvertHelper.toDate(this.fromDate)));
    }
    // query interviews to date
    if (this.toDate != null && !this.toDate.isEmpty()) {
//      Date dt = ConvertHelper.toDate(this.toDate);
//      Calendar c = Calendar.getInstance();
//      c.setTime(dt);
//      c.add(Calendar.DATE, 1);
//      dt = c.getTime();
      predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.<Date>get("endTime"), ConvertHelper.toDate(this.toDate)));
    }
    // query.distinct(true);
    return predicate;
  }
}
