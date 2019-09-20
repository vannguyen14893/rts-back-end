package com.cmc.recruitment.specification;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.jpa.domain.Specification;

import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.Skill;
import com.cmc.recruitment.utils.ConvertHelper;

public class RequestSpecification implements Specification<Request> {

  private List<Long> requestStatusIds;

  private Long skillId;

  private Long positionId;

  private Long daysAgo;

  private String title;

  private Long createdBy;

  private Long assigneeId;

  private List<Long> departmentIds;

  private Long priorityId;
  
  private Long groupId;
  
  private String fromDate;
  
  private String toDate;

  public RequestSpecification() {
    super();
  }

  
  
  public RequestSpecification(String fromDate, String toDate) {
    super();
    this.fromDate = fromDate;
    this.toDate = toDate;
  }



  public RequestSpecification(List<Long> requestStatusIds, Long skillId, Long positionId,
      Long daysAgo, String title, Long createdBy, Long assigneeId, List<Long> departmentId,
      Long priorityId, Long groupId) {
    super();
    this.requestStatusIds = requestStatusIds;
    this.skillId = skillId;
    this.positionId = positionId;
    this.daysAgo = daysAgo;
    this.title = title;
    this.createdBy = createdBy;
    this.assigneeId = assigneeId;
    this.departmentIds = departmentId;
    this.priorityId = priorityId;
    this.groupId = groupId;
  }

  public RequestSpecification(List<Long> requestStatusIds, Long assigneeId) {
    this.requestStatusIds = requestStatusIds;
    this.assigneeId = assigneeId;
  }

  /**
   * 
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public Predicate toPredicate(Root<Request> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    Predicate predicate = cb.conjunction();

    if (departmentIds != null && !departmentIds.isEmpty()) {
      predicate = cb.and(predicate,
          root.<Long>get("departmentId").get("id").in(departmentIds));
    }
    if (requestStatusIds != null && !requestStatusIds.isEmpty()) {
      predicate = cb.and(predicate,
          root.<Long>get("requestStatusId").get("id").in(requestStatusIds));
    }
    
    if (skillId != null) {
      predicate = cb.and(predicate,
          cb.isMember(new Skill(skillId), root.<Set<Skill>>get("skillCollection")));
    }
    if (positionId != null) {
      predicate = cb.and(predicate, cb.equal(root.<Long>get("positionId").get("id"), positionId));
    }
    if (daysAgo != null) {
      predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.<Date>get("createdDate"),
          DateUtils.addDays(new Date(), -(daysAgo.intValue()))));
    }
    if (createdBy != null) {
      predicate = cb.and(predicate, cb.equal(root.<Long>get("createdBy").get("id"), createdBy));
    }
    if (assigneeId != null) {
      predicate = cb.and(predicate,
          cb.equal(root.join("requestAssignee").get("assignee").<Long>get("id"), assigneeId));
    }
    if (priorityId != null) {
      predicate = cb.and(predicate, cb.equal(root.get("priorityId").<Long>get("id"), priorityId));
    }
    if (groupId != null) {
      predicate = cb.and(predicate, cb.equal(root.get("groupId").<Long>get("id"), groupId));
    }
    if (title != null && !title.isEmpty()) {
        predicate = cb.and(predicate, cb.like(root.<String>get("title"), '%' + title + '%'));
        return predicate;
    }
    
    // quý - lọc theo ngày approved request
    if (this.fromDate != null && !this.fromDate.isEmpty()) {
      predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.<Date>get("approvedDate"),
          ConvertHelper.toDate(this.fromDate)));
    }
    if (this.toDate != null && !this.toDate.isEmpty()) {
      predicate = cb.and(predicate,
          cb.lessThanOrEqualTo(root.<Date>get("approvedDate"), ConvertHelper.toDate(this.toDate)));
    }
    // quý end
    return predicate;
  }

}
