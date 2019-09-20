package com.cmc.recruitment.utils;

import java.util.Set;

import com.cmc.recruitment.entity.Group;
import com.cmc.recruitment.entity.Role;

public class UserDto {
	private Long id;
	private String userName;
	private String fullName;
	private String email;
	private String avatar;
	private String token;
	private Long departmentId;
	private String departmentName;
	private Set<Role> roleCollection;
    private Long groupId;
    private Set<Group> groupCollection;
	public Set<Role> getRoleCollection() {
		return roleCollection;
	}

	public void setRoleCollection(Set<Role> roleCollection) {
		this.roleCollection = roleCollection;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Set<Group> getGroupCollection() {
		return groupCollection;
	}

	public void setGroupCollection(Set<Group> groupCollection) {
		this.groupCollection = groupCollection;
	}

}
