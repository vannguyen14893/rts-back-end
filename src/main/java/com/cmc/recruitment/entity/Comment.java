/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "comment")
public class Comment implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 2344474339154569238L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
  
  @Column(name = "comment_detail")
  private String commentDetail;
  
  @Column(name = "create_date")
  private Date createDate;
  
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "candidate_id", referencedColumnName = "id")
  private Candidate candidateId;

  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @ManyToOne
  private User userId;

  @JsonProperty(access = Access.WRITE_ONLY)
  @JoinColumn(name = "interview_id", referencedColumnName = "id")
  @ManyToOne
  private Interview interviewId;

  public Comment(Long id) {
    this.id = id;
  }

  public Comment(Long id, String commentDetail) {
    this.id = id;
    this.commentDetail = commentDetail;
  }
  public Comment(String commentDetail) {
	this.commentDetail = commentDetail;
  }

  @Override
  public String toString() {
    return "demo.Comment[ id=" + id + " ]";
  }

}
