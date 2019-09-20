package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.QueryConstants;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Query(QueryConstants.User.FIND_BY_USERNAME_CASE_INSENSITIVE)
	User findByUserNameCaseInsensitive(@Param(Constants.PARAM.USERNAME_PARAM) String username);

	List<User> findByUsername(String userName);

	User findById(Long id);

	User findByEmail(String email);

	@Query(QueryConstants.User.FIND_USER_BY_GROUP)
	List<User> findByGroup(Long groupId);

	@Query(QueryConstants.User.FIND_ALL_USER)
	Page<User> findAllUser(Pageable pageable);

	@Query(QueryConstants.User.GET_INTERVIEWERS)
	List<User> getInterviewers();

	@Query(QueryConstants.User.GET_USER_IS_CREATOR)
	List<User> getUserIsCreator();

	@Query(QueryConstants.User.GET_USER_IS_ASSIGNEE)
	List<User> getUserIsAssignee();
	
	@Query(QueryConstants.User.FIND_USER_BY_DEPARTMENT)
	List<User> findByDepartment(@Param(Constants.PARAM.ID_PARAM) Long id);
	
	@Query(QueryConstants.User.FIND_USER_BY_ROLE)
	List<User> findUserByRole(@Param(Constants.PARAM.ROLE_ID_PARAM) Long roleId);
	
	@Query(QueryConstants.User.FIND_USER_BY_DEPARTMENR_ADN_ROLE)
	List<User> findUserByRoleAndDepartment(@Param(Constants.PARAM.ROLE_ID_PARAM) Long roleId, @Param(Constants.PARAM.DEPARTMENT_ID_PARAM) Long departmentId);

}
