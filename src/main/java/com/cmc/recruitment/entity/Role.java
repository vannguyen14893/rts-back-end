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
@Table(name = "role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -934179924314555972L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "role_name")
    private String roleName;
    
    @ManyToMany(mappedBy = "roleCollection")
    @JsonIgnore
    private Set<User> userCollection;

    public Role(String roleName) {
		super();
		this.roleName = roleName;
	}

	public Role(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
      return "Role [id=" + id + ", roleName=" + roleName + "]";
    }  
}
