/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cmc.recruitment.utils.CommentModel;
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
@Table(name = "cv")
public class Cv implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -7905559704545119568L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotNull
  @Size(min = 2, max = 255)
  @Column(name = "full_name")
  private String fullName;

  @Column(name = "title")
  private String title;

  @Column(name = "dob")
  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date dob;

  @Column(name = "gender")
  private Boolean gender;

  @Size(max = 20)
  @Column(name = "phone")
  private String phone;

  @Size(max = 255)
  @Column(name = "email")
  private String email;

  @Size(max = 2000)
  @Column(name = "profile_img")
  private String profileImg;

  @Size(max = 2000)
  @Column(name = "address")
  private String address;

  @Size(max = 255)
  @Column(name = "education")
  private String education;

  @ManyToOne
  @JoinColumn(name = "experience_id", referencedColumnName = "id")
  private Experience experienceId;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvId")
  private List<CvUrl> cvUrlCollection;

  @Column(name = "created_date")
  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date createdDate;

  @JoinColumn(name = "created_by", referencedColumnName = "id")
  @ManyToOne
  private User createdBy;

  @Column(name = "edited_date")
  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date editedDate;

  @JoinColumn(name = "edited_by", referencedColumnName = "id")
  @ManyToOne
  private User editedBy;

  @JoinTable(name = "cv_skill", joinColumns = {
      @JoinColumn(name = "cv_id", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "skill_id", referencedColumnName = "id") })
  @ManyToMany
  private Set<Skill> skillCollection;
  
  @JoinTable(name = "cv_certification", joinColumns = {
	      @JoinColumn(name = "cv_id", referencedColumnName = "id") }, inverseJoinColumns = {
	          @JoinColumn(name = "certification_id", referencedColumnName = "id") })
  @ManyToMany
  private Set<Certification> certificationCollection;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "cvId")
  @JsonIgnore
  private List<Candidate> candidateCollection;

  @JoinColumn(name = "status_id", referencedColumnName = "id")
  @ManyToOne
  private CvStatus statusId;

  @Size(max = 500)
  @Column(name = "facebook")
  private String facebook;

  @Size(max = 255)
  @Column(name = "skype")
  private String skype;

  @Size(max = 255)
  @Column(name = "linkedin")
  private String linkedin;

  @Size(max = 2000)
  @Column(name = "note")
  private String note;

  public Cv(Long id) {
    this.id = id;
  }

  public Cv(Long id, String skills) {
    this.id = id;
  }

  public Cv(String fullName, String title, Date dob, Boolean gender, String phone, String email,
      String profileImg, String address, String education, Experience experienceId,
      List<CvUrl> cvUrlCollection, Date createdDate, User createdBy, Date editedDate, User editedBy,
      Set<Skill> skillCollection, List<Candidate> candidateCollection, CvStatus statusId,
      String facebook, String skype, String linkedin, String note) {
    super();
    this.fullName = fullName;
    this.title = title;
    this.dob = dob;
    this.gender = gender;
    this.phone = phone;
    this.email = email;
    this.profileImg = profileImg;
    this.address = address;
    this.education = education;
    this.experienceId = experienceId;
    this.cvUrlCollection = cvUrlCollection;
    this.createdDate = createdDate;
    this.createdBy = createdBy;
    this.editedDate = editedDate;
    this.editedBy = editedBy;
    this.skillCollection = skillCollection;
    this.candidateCollection = candidateCollection;
    this.statusId = statusId;
    this.facebook = facebook;
    this.skype = skype;
    this.linkedin = linkedin;
    this.note = note;
  }

  @Transient
  private List<CommentModel> comments;

  public List<CommentModel> getComments() {
    List<Candidate> candidates = this.candidateCollection;
    List<CommentModel> commentModels = new LinkedList<>();
    if (candidates != null && candidates.size() > 0) {
      for (Candidate candidate : candidates) {

        List<Comment> comments = candidate.getCommentCollection();
        if (comments != null && comments.size() > 0) {
          for (Comment comment : comments) {
            CommentModel c = new CommentModel();

            c.setId(comment.getId());
            c.setUserName(comment.getUserId().getFullName());
            c.setCreateDate(comment.getCreateDate());
            c.setCommentDetail(comment.getCommentDetail());
            c.setInterviewTitle(comment.getInterviewId().getTitle());
            commentModels.add(c);
          }
        }
      }
    }

    return commentModels;
  }
}
