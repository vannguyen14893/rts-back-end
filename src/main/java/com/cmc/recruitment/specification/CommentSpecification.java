package com.cmc.recruitment.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.cmc.recruitment.entity.Comment;

public class CommentSpecification implements Specification<Comment> {

  private Long cvId;

  private Long candidateId;

  private Long interviewId;

  public Long getInterviewId() {
    return interviewId;
  }

  public void setInterviewId(Long interviewId) {
    this.interviewId = interviewId;
  }

  public Long getCandidateId() {
    return candidateId;
  }

  public void setCandidateId(Long candidateId) {
    this.candidateId = candidateId;
  }

  public CommentSpecification() {
    super();
  }

  public CommentSpecification(Long cvId, Long candidateId, Long interviewId) {
    super();
    this.cvId = cvId;
    this.candidateId = candidateId;
    this.interviewId = interviewId;
  }

  public CommentSpecification(Long cvId) {
    super();
    this.cvId = cvId;
  }

  @Override
  public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    Predicate predicate = cb.conjunction();

    if (cvId != null) {
      predicate = cb.and(predicate,
          cb.equal(root.join("candidateId").join("cvId").get("id"), cvId));
      query.distinct(root.get("id") != null);
      return predicate;
    }

    if (candidateId != null) {
      predicate = cb.and(predicate, cb.equal(root.<Long>get("candidateId").get("id"), candidateId));
    }

    if (interviewId != null) {
      predicate = cb.and(predicate, cb.equal(root.<Long>get("interviewId").get("id"), interviewId));
    }

    return predicate;
  }
}
