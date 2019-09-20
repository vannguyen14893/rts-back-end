package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;

import com.cmc.recruitment.entity.InterviewStatus;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.User;

public interface UserService {

	List<User> listAllUser();

	List<User> getUserInfoByUserName();

	String getInfoUser(long id);

	boolean addUser(User user);

	boolean updateUser(User user);

	int updatePassword(User user, String newPassword, String oldPassword);

	boolean changeStatusUser(User user);

	void deleteUser(long userId);

	User findOne(long id);

	int resetPassword(User email) throws MailException;

	User findByUsername(String username);

	User findByEmail(String email);

	Page<User> findAllUser(Pageable pageable);

	Request getRequestById(Long requestId);

	List<User> getInterviewers();

	List<InterviewStatus> getInterviewStatuses();

	List<User> getUserIsCreator() throws Exception;

	List<User> getUserIsAssignee() throws Exception;
	
	Page<User> filterUser(Pageable pageable, Long roleId, String input);
	
	int sendEmailAfterCreateUser(User user, String password) throws MailException;

	List<User> findByDepartment(Long id);

	int sendEmailAfterUpdateUser(User user);
	
	User saveUser(User user);
	
	List<User> getUserByRole(Long roleId);
	
	List<User> findByGroup(Long groupId);

	List<User> getUserByRoleAndDepartment(Long roleId, Long departmentId);
}
