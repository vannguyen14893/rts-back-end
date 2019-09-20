package com.cmc.recruitment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cv_url")
public class CvUrl implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -2144931429070596992L;
  // Phong
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
  
  @Column(name = "url")
  private String url;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "cv_id", referencedColumnName = "id", nullable = false)
  private Cv cvId;
  
  @Transient
  private Boolean tobeDeleted;
  
  public CvUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "CvUrl [id=" + id + ", url=" + url + ", cv=" + cvId + "]";
  }


}
