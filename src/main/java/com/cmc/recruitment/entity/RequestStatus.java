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
@Table(name = "request_status")
public class RequestStatus implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1568200109914887803L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "requestStatusId")
    @JsonIgnore
    private List<Request> requestCollection;

    public RequestStatus(Long id) {
        this.id = id;
    }

    public RequestStatus(Long id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public RequestStatus(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

    @Override
    public String toString() {
        return "demo.RequestStatus[ id=" + id + " ]";
    }
    
}
