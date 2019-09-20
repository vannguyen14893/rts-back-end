/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "interview_status")
public class InterviewStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9073824974211753838L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "statusId")
    @JsonIgnore
    private List<Interview> interviewCollection;

    public InterviewStatus(Long id) {
        this.id = id;
    }

    public InterviewStatus(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public InterviewStatus(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

    @Override
    public String toString() {
        return "demo.InterviewStatus[ id=" + id + " ]";
    }
    
}
