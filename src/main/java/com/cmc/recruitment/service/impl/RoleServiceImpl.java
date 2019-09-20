package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.repository.RoleRepository;
import com.cmc.recruitment.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired RoleRepository roleRepository; 
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
	@Override
	public Role findByTitle(String title) {
		return roleRepository.findByRoleName(title);
	}
}
