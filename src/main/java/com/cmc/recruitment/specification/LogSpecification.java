package com.cmc.recruitment.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cmc.recruitment.entity.Log;

public class LogSpecification implements Specification<Log> {

  private Long requestId;
	
  private Long candidateId;

  private Long cvId;

  private Long interviewId;
  
  private String action;

  public LogSpecification() {
    super();
  }

  public LogSpecification(Long requestId, Long candidateId, Long cvId, Long interviewId, String action) {	
    super();
    this.requestId = requestId;
    this.candidateId = candidateId;
    this.cvId = cvId;
    this.cvId = interviewId;
    this.action = action;
  }

  
  public Long getRequestId() {
	return requestId;
  }

  public void setRequestId(Long requestId) {
	this.requestId = requestId;
  }
  public Long getCandidateId() {
	return candidateId;
  }

  public void setCandidateId(Long candidateId) {
	this.candidateId = candidateId;
  }

  public Long getInterviewId() {
	    return interviewId;
  }
  public String getAction() {
	return action;
  }

  public void setAction(String action) {
	this.action = action;
  }
  public void setInterviewId(Long interviewId) {
	this.interviewId = interviewId;
  }
  public Long getCvId() {
	    return cvId;
  }

  public void setCvId(Long cvId) {
	    this.cvId = cvId;
  }

  @Override
  public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    Predicate predicate = cb.conjunction();

    // filter by candidate
    if (requestId != null) {
        predicate = cb.and(predicate, cb.equal(root.<Long>get("requestId"), requestId));
    }
    if (candidateId != null) {
        predicate = cb.and(predicate, cb.equal(root.<Long>get("candidateId"), candidateId));
    }
    if (cvId != null) {
        predicate = cb.and(predicate, cb.equal(root.<Long>get("cvId"), cvId));
    }
    if (interviewId != null) {
        predicate = cb.and(predicate, cb.equal(root.<Long>get("interviewId"), interviewId));
    }
    if (action != null) {
        predicate = cb.and(predicate, cb.equal(root.<Long>get("action"), action));
    }
    return predicate;
  }

}
