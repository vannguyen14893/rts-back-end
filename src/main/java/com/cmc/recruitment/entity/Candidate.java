package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "candidate")
public class Candidate implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 4197942078910386097L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "evaluate_point")
  private Float evaluatePoint;

  @Column(name = "create_date")
//  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date createDate;

  @Column(name = "source")
  private String source;

  @Column(name = "title")
  private String title;

  @JoinColumn(name = "created_by", referencedColumnName = "id")
  @ManyToOne
  private User createdBy;

  @Column(name = "onboard_date")
//  @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
  private Date onboardDate;

  @JoinColumn(name = "cv_id", referencedColumnName = "id")
  @ManyToOne
  private Cv cvId;

  @JoinColumn(name = "request_id", referencedColumnName = "id")
  @ManyToOne
  private Request requestId;

  @JoinColumn(name = "status_id", referencedColumnName = "id")
  @ManyToOne
  private CandidateStatus statusId;

  @OneToMany(mappedBy = "candidateId")
  private List<Comment> commentCollection;

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "candidateCollection")
  @JsonIgnore
  private List<Interview> interviewCollection;
  
  public Candidate(Float evaluatePoint, Date createDate, String source, String title,
      User createdBy, Cv cvId, Request requestId, CandidateStatus statusId,
      List<Comment> commentCollection, List<Interview> interviewCollection) {
    super();
    this.evaluatePoint = evaluatePoint;
    this.createDate = createDate;
    this.source = source;
    this.title = title;
    this.createdBy = createdBy;
    this.cvId = cvId;
    this.requestId = requestId;
    this.statusId = statusId;
    this.commentCollection = commentCollection;
    this.interviewCollection = interviewCollection;
  }

  public Candidate(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "demo.Candidate[ id=" + id + " ]";
  }

}
