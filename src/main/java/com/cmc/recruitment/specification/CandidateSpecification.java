/**
 * 
 */
package com.cmc.recruitment.specification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.utils.ConvertHelper;

/**
 * @description: .
 * @author: VDHoan
 * @created_date: Feb 23, 2018
 * @modifier: User
 * @modifier_date: Feb 23, 2018
 */
public class CandidateSpecification implements Specification<Candidate> {
  private Long experienceId;
  private Long requestId;
  private Long statusId;
  private String fullName;
  private Long hrmemberId;

  private String fromDate;
  private String toDate;

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Feb 23, 2018
   * @modifier: User
   * @modifier_date: Feb 23, 2018
   * @param root
   * @param query
   * @param cb
   * @return
   */
  @Override
  public Predicate toPredicate(Root<Candidate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    // init to find all
    Predicate predicate = cb.conjunction();
    
    // query by HrMember
    if (this.hrmemberId != null) {
      predicate = cb.and(predicate, cb.equal(root.<Long>get("createdBy").get("id"), hrmemberId));
    }

    if (this.fullName != null) {
      predicate = cb.and(predicate,
          cb.like(root.<String>get("cvId").get("fullName"), '%' + this.fullName + '%'));
    }
    // query by request
    if (this.requestId != null) {
      predicate = cb.and(predicate, cb.equal(root.<Long>get("requestId").get("id"), requestId));
    }

    // query by candidate status
    if (this.statusId != null) {
      predicate = cb.and(predicate, cb.equal(root.<Long>get("statusId").get("id"), this.statusId));
    }

    if (this.experienceId != null) {
      predicate = cb.and(predicate,
          cb.equal(root.<Long>get("cvId").get("experienceId").get("id"), this.experienceId));
    }
    // quý - lọc theo ngày onboard ứng viên
    if (this.fromDate != null && !this.fromDate.isEmpty()) {
      predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.<Date>get("onboardDate"),
          ConvertHelper.toDate(this.fromDate)));
    }
    if (this.toDate != null && !this.toDate.isEmpty()) {
      predicate = cb.and(predicate,
          cb.lessThanOrEqualTo(root.<Date>get("onboardDate"), ConvertHelper.toDate(this.toDate)));
    }
    // quý end
    return predicate;
  }

  public Long getRequestId() {
    return requestId;
  }

  public void setRequestId(Long requestId) {
    this.requestId = requestId;
  }

  public Long getStatusId() {
    return statusId;
  }

  public void setStatusId(Long statusId) {
    this.statusId = statusId;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public Long getHrmemberId() {
    return hrmemberId;
  }

  public void setHrmemberId(Long hrmemberId) {
    this.hrmemberId = hrmemberId;
  }

  /**
   * @param experienceId
   * @param requestId
   * @param statusId
   * @param fullName
   * @param hrmemberId
   */
  public CandidateSpecification(Long experienceId, Long requestId, Long statusId, String fullName,
      Long hrmemberId) {
    super();
    this.experienceId = experienceId;
    this.requestId = requestId;
    this.statusId = statusId;
    this.fullName = fullName;
    this.hrmemberId = hrmemberId;
  }

  public CandidateSpecification(Long hrmemberId, String fromDate, String toDate, Long candidateStatusId) {
    super();
    this.hrmemberId = hrmemberId;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.statusId = candidateStatusId;
  }

  public CandidateSpecification(Long experienceId, Long requestId, Long statusId, String fullName,
	      Long hrmemberId, String fromDate, String toDate) {
	    super();
	    this.experienceId = experienceId;
	    this.requestId = requestId;
	    this.statusId = statusId;
	    this.fullName = fullName;
	    this.hrmemberId = hrmemberId;
	    this.fromDate = fromDate;
	    this.toDate = toDate;
	  }
}
