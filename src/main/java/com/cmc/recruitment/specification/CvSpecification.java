package com.cmc.recruitment.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Cv;

public class CvSpecification implements Specification<Cv> {

  private String input;
  private Long hrId;
  private Long requestId;
  private List<Long> statusId;
  private List<Long> experienceId;
  private List<Long> skillId;
  private List<Long> certificationId;
  private Long requestIdOfCandidate;

  public CvSpecification(String input,Long hrId, Long requestId, List<Long>  statusId, List<Long>  experienceId,
      List<Long>  skillId,List<Long> certificationId, Long requestIdOfCandidate) {
    super();
    this.input = input;
    this.hrId = hrId;
    this.requestId = requestId;
    this.statusId = statusId;
    this.experienceId = experienceId;
    this.skillId = skillId;
    this.certificationId = certificationId;
    this.requestIdOfCandidate = requestIdOfCandidate;
  }

  @Override
  public Predicate toPredicate(Root<Cv> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    Predicate predicate = criteriaBuilder.conjunction();
    if (this.input != null && !input.isEmpty()) {
      predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.<String>get("fullName"), '%' + input + '%'));
      predicate = criteriaBuilder.or(predicate, criteriaBuilder.like(root.<String>get("email"), '%' + input + '%'));
      predicate = criteriaBuilder.or(predicate, criteriaBuilder.like(root.<String>get("phone"), '%' + input + '%'));
    }
    // filter.
    if (this.hrId != null) {
      predicate = criteriaBuilder.and(predicate,criteriaBuilder.equal(root.get("createdBy").<Long>get("id"), hrId));
      query.distinct(root.get("id") != null);
    }
    if (this.requestId != null) {
      predicate = criteriaBuilder.and(predicate,
          criteriaBuilder.equal(root.join("skillCollection").join("requestCollection").get("id"), requestId));
      query.distinct(root.get("id") != null);
    }
    if (this.statusId != null) {
      predicate = criteriaBuilder.and(predicate, root.get("statusId").<Long>get("id").in(statusId));
    }
    if (this.experienceId != null) {
      predicate = criteriaBuilder.and(predicate, root.get("experienceId").<Long>get("id").in(experienceId));
    }
    if (this.skillId != null) {
      predicate = criteriaBuilder.and(predicate, root.join("skillCollection").get("id").in(skillId));
      query.distinct(root.get("id") != null);
    }
    if (this.certificationId != null) {
      predicate = criteriaBuilder.and(predicate, root.join("certificationCollection").get("id").in(certificationId));
      query.distinct(root.get("id") != null);
    }
    // Select * FROM CV cvs WHERE NOT EXISTS
    if (this.requestIdOfCandidate != null && requestIdOfCandidate!=0) {
      //CriteriaQuery<Candidate> criteriaQuery = cb.createQuery(Candidate.class);
      Subquery<Candidate> subquery = criteriaBuilder.createQuery(Candidate.class).subquery(Candidate.class);
      Root<Candidate> rootSubCv = subquery.from(Candidate.class);
      subquery.select(rootSubCv)
          .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), rootSubCv.get("cvId").<Long>get("id")),
              criteriaBuilder.equal(rootSubCv.get("requestId").<Long>get("id"), this.requestIdOfCandidate)));
      predicate = criteriaBuilder.and(predicate, criteriaBuilder.not(criteriaBuilder.exists(subquery)));
    }
    query.orderBy(criteriaBuilder.desc(root.get("createdDate")));
    return predicate;
  }
}
