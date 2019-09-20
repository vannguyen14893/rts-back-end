package com.cmc.recruitment.service;

import java.util.List;

import com.cmc.recruitment.entity.Role;

public interface RoleService {
	List<Role> findAll();
	Role findByTitle(String title);
}
