package com.cmc.recruitment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.service.RoleService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class RoleController {
	@Autowired
	RoleService roleService;

	@GetMapping(ConstantsUrl.Role.ALL)
	public ResponseEntity<?> getAllRole() {
		try {
			List<Role> list = roleService.findAll();
			if (list.size() > 0)
				return new ResponseEntity<List<Role>>(list, HttpStatus.OK);
			return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
