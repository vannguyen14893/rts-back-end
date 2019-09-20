/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
@Table(name = "interview")
public class Interview implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = -3834911285794041296L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
  
  @Size(max = 255)
  @NotEmpty
  @Column(name = "title")
  private String title;

  @Column(name = "start_time")
  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/M/yyyy HH:mm")
//  @JsonFormat(pattern = "dd/MM/yyyy hh:mm", timezone = "Asia/Ho_Chi_Minh")
  private Date startTime;

  @Column(name = "end_time")
  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/M/yyyy HH:mm")
//  @JsonFormat(pattern = "dd/MM/yyyy hh:mm", timezone = "Asia/Ho_Chi_Minh")
  private Date endTime;

  @Size(max = 255)
  @NotEmpty
  @Column(name = "location")
  private String location;

  @Size(max = 255)
  @Column(name = "note")
  private String note;

  @ManyToMany
  @JoinTable(name = "assignee_interview", joinColumns = {
      @JoinColumn(name = "interview_id", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "user_id", referencedColumnName = "id") })
  private Set<User> userCollection;

  @ManyToMany
  @JoinTable(name = "interview_candidate", joinColumns = @JoinColumn(name = "interview_id"), inverseJoinColumns = @JoinColumn(name = "candidate_id"))
  private List<Candidate> candidateCollection;
  
  @JsonProperty(access = Access.WRITE_ONLY)
  @OneToMany(mappedBy = "interviewId")
  private List<Comment> commentCollection;

  @JoinColumn(name = "status_id", referencedColumnName = "id")
  @ManyToOne
  private InterviewStatus statusId;

  public Interview(Long id) {
    this.id = id;
  }

  public Interview(Long id, String title) {
    this.id = id;
    this.title = title;
  }

  @Override
  public String toString() {
    return "Interview [id=" + id + ", title=" + title + ", startTime=" + startTime + ", endTime="
        + endTime + ", location=" + location + ", note=" + note + ", userCollection="
        + userCollection + ", candidateCollection=" + candidateCollection + ", commentCollection="
        + commentCollection + ", statusId=" + statusId + "]";
  }

}
