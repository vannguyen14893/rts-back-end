/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "skill")
public class Skill implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 509182490486326095L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;
  
  @Column(name = "title", unique = true, nullable = false)
  private String title;
  
  @Column(name = "description")
  private String description;
  
  @ManyToMany(mappedBy = "skillCollection")
  @JsonIgnore
  private Set<Request> requestCollection;
  
  @JsonIgnore
  @ManyToMany(mappedBy = "skillCollection")
  private Set<Cv> cvCollection;
  
  @Column(name = "is_delete")
	private Boolean isDelete;

  public Skill(Long id) {
    this.id = id;
  }

  public Skill(Long id, String title) {
    this.id = id;
    this.title = title;
  }
  
  public Skill(String title) {
	    this.title = title;
	  }

  public Skill(String title, String description) {
	super();
	this.title = title;
	this.description = description;
  }
  @Override
  public String toString() {
    return "demo.Skill[ id=" + id + " ]";
  }


}