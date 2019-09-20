/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2058138951259242655L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@ManyToMany
	@JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "groups_id"))
	private Set<Group> groupCollection;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "password")
	private String password;

	@Column(name = "email", unique=true)
	private String email;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "avatar_url")
	private String avatarUrl;

	@Column(name = "is_active")
	private Boolean isActive;
	@Column(name="token",columnDefinition = "text")
	private String token;
	@ManyToMany(mappedBy = "userCollection")
	@JsonIgnore
	private Set<Interview> interviewCollection;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roleCollection;

	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "createdBy")
	private List<Candidate> candidateCollection;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignee")
	private Set<RequestAssignee> requestAssignee;

	@JsonIgnore
	@OneToMany(mappedBy = "createdBy")
	private List<Request> requestCollection1;

	@JsonIgnore
	@OneToMany(mappedBy = "createdBy")
	private List<Cv> cvCollection1;

	@JsonIgnore
	@OneToMany(mappedBy = "editedBy")
	private List<Request> requestCollection2;

	@JsonIgnore
	@OneToMany(mappedBy = "editedBy")
	private List<Cv> cvCollection2;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
	private List<Comment> commentCollection;

	@JoinColumn(name = "department_id", referencedColumnName = "id", nullable=true)
	@ManyToOne
	private Department departmentId;

	@JsonIgnore
	@OneToMany(mappedBy = "actor")
	private List<Log> logCollection;
	
	@Transient
	private String newPassword;

	public User(Long id) {
		this.id = id;
	}

	public User(String password, String newPassword) {
		this.password = password;
		this.newPassword = newPassword;
	}

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public User(Long id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public User(String username, String password, String email, Boolean isActive) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + "]";
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	/**
	 * @param id
	 * @param username
	 * @param password
	 * @param email
	 * @param fullName
	 * @param groupCollection
	 * @param avatarUrl
	 * @param isActive
	 * @param roleCollection
	 * @param departmentId
	 */
	public User(Long id, String username, String password, String email, String fullName, Set<Group> groupCollection,
			String avatarUrl, Boolean isActive, Set<Role> roleCollection, Department departmentId) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullName = fullName;
		this.groupCollection = groupCollection;
		this.avatarUrl = avatarUrl;
		this.isActive = isActive;
		this.roleCollection = roleCollection;
		this.departmentId = departmentId;
	}
}
