/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author User
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "request")
public class Request implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2710841404788747440L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;
  
  @Column(name = "deadline")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date deadline;
  
  @Column(name = "number")
  private int number;
  
  @Column(name = "description")
  private String description;
  
  @Column(name = "major")
  private String major;
  
  @Column(name = "others")
  private String others;
  
  @Column(name = "salary")
  private String salary;
  
  @Column(name = "benefit")
  private String benefit;
  
  @Column(name = "request_code")
  private String requestCode;
  
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  @Column(name = "created_date")
  private Date createdDate;
  
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  @Column(name = "edited_date")
  private Date editedDate;
  
  @Column(name = "published_date")
//  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date publishedDate;
  
  @Column(name = "approved_date")
  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date approvedDate;
  
  public Date getApprovedDate() {
    return approvedDate;
  }

  public void setApprovedDate(Date approvedDate) {
    this.approvedDate = approvedDate;
  }

  @Column(name = "certificate")
  private String certificate;
  
  @ManyToOne
  @JoinColumn(name = "priority_id", referencedColumnName = "id")
  private Priority priorityId;

  @ManyToOne
  @JoinColumn(name = "experience_id", referencedColumnName = "id")
  private Experience experienceId;

  @Column(name = "cv_deadline")
  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date cvDeadline;

  @JoinTable(name = "request_skill", joinColumns = {
      @JoinColumn(name = "request_id", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "skill_id", referencedColumnName = "id") })
  @ManyToMany
  private Set<Skill> skillCollection;
  
  @JoinTable(name = "request_certification", joinColumns = {
	      @JoinColumn(name = "request_id", referencedColumnName = "id") }, inverseJoinColumns = {
	          @JoinColumn(name = "certification_id", referencedColumnName = "id") })
	  @ManyToMany
	  private Set<Certification> certificationCollection;

  @JoinColumn(name = "position_id", referencedColumnName = "id")
  @ManyToOne
  private Position positionId;

  @JoinColumn(name = "request_status_id", referencedColumnName = "id")
  @ManyToOne
  private RequestStatus requestStatusId;

  @OneToMany(mappedBy = "request")
  private Set<RequestAssignee> requestAssignee;

  @ManyToOne
  @JoinColumn(name = "project_id", referencedColumnName = "id")
  private Project projectId;

  @ManyToOne
  @JoinColumn(name = "groups_request_id", referencedColumnName = "id")
  private Group groupId;
  
  @ManyToOne
  @JoinColumn(name = "recruitment_type_id", referencedColumnName = "id")
  private RecruitmentType recruitmentTypeId;

  @ManyToMany
  @JoinTable(name = "request_foreign_language", joinColumns = @JoinColumn(name = "request_id"), inverseJoinColumns = @JoinColumn(name = "foreign_language_id"))
  private Set<ForeignLanguage> foreignLanguageCollection;

  @JoinColumn(name = "created_by", referencedColumnName = "id")
  @ManyToOne
  private User createdBy;

  @JoinColumn(name = "edited_by", referencedColumnName = "id")
  @ManyToOne
  private User editedBy;

  @Size(max = 255)
  @Column(name = "reject_reason")
  private String rejectReason;
  
  @JoinColumn(name = "department_id", referencedColumnName = "id")
  @ManyToOne
  private Department departmentId;
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestId")
  @JsonIgnore
  private List<Candidate> candidateCollection;

  @Transient
  private Map<String, Integer> countRequestStatus;

  @Transient
  private double lackNumber;

  @Transient
  private double ratio;
  
  @Transient
  private int percentDealine;
  
  @Transient
  private boolean isFinish;
  
  @Transient
  private String editChange;
  
  public boolean getIsFinish() {
	  return this.isFinish;
  }
  public void setIsFinish(boolean isFinish) {
	  this.isFinish = isFinish;
  }
  
  public Request(Long id) {
    this.id = id;
  }

  public Request(Long id, String title, int number, String description) {
    this.id = id;
    this.title = title;
    this.number = number;
    this.description = description;
  }

  @Override
  public String toString() {
    return "Request [id=" + id + ", title=" + title + ", deadline=" + deadline + ", number="
        + number + ", description=" + description + ", major=" + major + ", others=" + others
        + ", salary=" + salary + ", benefit=" + benefit + ", createdDate=" + createdDate
        + ", editedDate=" + editedDate + ", publishedDate=" + publishedDate + ", certificate="
        + certificate + ", priority=" + priorityId + ", yearOfExperience=" + experienceId
        + ", cvDeadline=" + cvDeadline + ", skillCollection=" + skillCollection + ", positionId="
        + positionId + ", requestStatusId=" + requestStatusId + ", assigneeId=" + requestAssignee
        + ", createdBy=" + createdBy + ", editedBy=" + editedBy + ", candidateCollection="
        + candidateCollection + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Request other = (Request) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  
  //So sánh sự thay đổi khi edit request.
  public void editChange( Request other) {
	  StringBuilder result = new StringBuilder();
	  if(!this.title.equals(other.getTitle()))
		  result.append("Title: "+this.title +"    to: "+other.getTitle()+"\n");
	  if(this.positionId.getId() != other.positionId.getId())
		  result.append("Position: "+this.positionId.getTitle() +"    to: "+other.getPositionId().getTitle()+"\n");
	  if(this.departmentId.getId() != other.departmentId.getId())
		  result.append("Department: "+this.departmentId.getTitle() +"    to: "+other.getDepartmentId().getTitle()+"\n");
	  if(this.number != other.getNumber())
		  result.append("Number: "+this.number +"to: "+other.getNumber()+"\n");
	  if(!this.deadline.toString().equals(other.getDeadline().toString()))
		  result.append("Deadline: "+this.deadline.toString() +"    to: "+other.getDeadline().toString()+"\n");
	  if(this.projectId.getId() != other.getProjectId().getId())
		  result.append("Project: "+this.projectId.getTitle() +"    to: "+other.getProjectId().getTitle()+"\n");
	  if(this.groupId.getId() != other.getGroupId().getId())
		  result.append("Group: "+this.groupId.getTitle() +"    to: "+other.getGroupId().getTitle()+"\n");
	  if(this.recruitmentTypeId.getId() != other.getRecruitmentTypeId().getId())
		  result.append("Request Type: "+this.recruitmentTypeId.getTitle() +"    to: "+other.getRecruitmentTypeId().getTitle()+"\n");
	  if(this.priorityId.getId() != other.getPriorityId().getId())
		  result.append("Priority: "+this.priorityId.getTitle() +"    to: "+other.getPriorityId().getTitle()+"\n");
	  if(this.experienceId.getId() != other.getExperienceId().getId())
		  result.append("Experience: "+this.experienceId.getTitle() +"    to: "+other.getExperienceId().getTitle()+"\n");
	  if(!this.certificate.equals(other.getCertificate()))
		  result.append("Certificate: "+this.certificate +"    to: "+other.getCertificate()+"\n");
	  if(!this.major.equals(other.getMajor()))
		  result.append("Major: "+this.major +"    to: "+other.getMajor()+"\n");
	  if(!this.others.equals(other.getOthers()))
		  result.append("Others: "+this.others +"    to: "+other.getOthers()+"\n");
	  if(!this.salary.equals(other.getSalary()))
		  result.append("Salary: "+this.salary +"    to: "+other.getSalary()+"\n");
	  this.editChange = result.toString();
  }
}
