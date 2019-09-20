package com.cmc.recruitment.service.impl;

import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.InterviewStatus;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.InterviewStatusRepository;
import com.cmc.recruitment.repository.RequestRepository;
import com.cmc.recruitment.repository.UserRepository;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.specification.UserSpecification;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.EmailHelper;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  public EmailHelper emailHelper;

  // @Autowired
  // private InterviewRepository interviewRepository;

  @Autowired
  private RequestRepository requestRepository;

  @Autowired
  private InterviewStatusRepository interviewStatusRepository;

  @Override
  public Page<User> findAllUser(Pageable pageable) {
    return userRepository.findAllUser(pageable);
  }

  @Override
  public String getInfoUser(long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean addUser(User user) {
    List<User> resultUsername = userRepository.findByUsername(user.getUsername());
    List<User> resultEmail = userRepository.findByUsername(user.getEmail());

    if (resultUsername.size() != 0 || resultEmail.size() != 0) {
      return false;
    } else {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return true;
    }
  }

  @Override
  public boolean updateUser(User user) {
    User thisUser = userRepository.findById(user.getId());
    user.setPassword(thisUser.getPassword());
    User result = userRepository.save(user);
    if (result != null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean changeStatusUser(User user) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<User> getUserInfoByUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication == null ? "null" : authentication.getName();
    List<User> result = userRepository.findByUsername(userName);
    return result;
  }

  @Override
  public void deleteUser(long userId) {
    userRepository.delete(userId);
  }

  @Override
  public int updatePassword(User user, String oldPassword, String newPassword) {
    if (!passwordEncoder.matches(oldPassword, user.getPassword()))
      return Constants.CHANGE_PASS_STATUS.INCORRECT_OLD_PASSWORD;
    if (newPassword.length() < 8 || newPassword.length() > 32) {
      return Constants.CHANGE_PASS_STATUS.INVALID_PASS_LENGTH;
    }
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
      return Constants.CHANGE_PASS_STATUS.SAME_OLD_PASS;
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
    return Constants.CHANGE_PASS_STATUS.SUCCESS;
  }

  @Override
  public User findOne(long id) {
    return userRepository.findOne(id);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.cmc.recruitment.service.UserService#resetPassword(com.cmc.recruitment.
   * entity.User)
   */
  @Override
  public int resetPassword(User passResetUser) throws MailException {
    User result = findByEmail(passResetUser.getEmail());
    if (result == null)
      return Constants.User.NOT_FIND_EMAIL;
    // result not null;
    String newPassword = emailHelper.createRandomPassWord();
    result.setPassword(passwordEncoder.encode(newPassword));
    if (userRepository.save(result) == null)
      return Constants.User.NOT_SAVE;
    /**
     * new thread for send email, function always return 0 is VALID . if has
     * mailException cannot send email, but password changed.
     */
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      @Override
      public void run() {
        emailHelper.sendSimpleMessage(Constants.APPLICATION_EMAIL, passResetUser.getEmail(),
            Constants.User.SUBJECT_RESET_PASSWORD, Constants.User.TEXT_MESSAGE_RESET + newPassword);
      }
    });
    return Constants.User.VALID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.cmc.recruitment.service.UserService#findByEmail(java.lang.String)
   */
  public User findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username).get(0);
  }

  @Override
  public List<User> listAllUser() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Request getRequestById(Long requestId) {
    return requestRepository.findOne(requestId);
  }

  @Override
  public List<User> getInterviewers() {
    return userRepository.getInterviewers();
  }

  @Override
  public List<InterviewStatus> getInterviewStatuses() {
    return interviewStatusRepository.findAll();
  }

  @Override
  public List<User> getUserIsCreator() throws Exception {
    return userRepository.getUserIsCreator();
  }

  @Override
  public List<User> getUserIsAssignee() throws Exception {
    return userRepository.getUserIsAssignee();
  }

  @Override
  public Page<User> filterUser(Pageable pageable, Long roleId, String input) {
    return userRepository.findAll(new UserSpecification(roleId, input), pageable);
  }

  /**
   * @description: .
   * @author: lcnguyen
   * @created_date: Mar 13, 2018
   * @modifier: User
   * @modifier_date: Mar 13, 2018
   * @param id
   * @return
   */

  @Override
  public int sendEmailAfterCreateUser(User user, String password) throws MailException {
    User result = findByEmail(user.getEmail());
    if (result == null)
      return Constants.User.NOT_FIND_EMAIL;
    /**
     * edit NHPhong: new thread for send email, function always return VALID .
     * if has mailException cannot send email, but password changed.
     */
    // result not null;
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      @Override
      public void run() {
        emailHelper.sendSimpleMessage(Constants.APPLICATION_EMAIL, user.getEmail(),
            Constants.User.SUBJECT_SEND_EMAIL_AFTER_CREATE_USER,
            user.getUsername() + "/" + password);
      }
    });
    return Constants.User.VALID;
  }

  /**
   * @description: .
   * @author: lcnguyen
   * @created_date: Mar 13, 2018
   * @modifier: User
   * @modifier_date: Mar 13, 2018
   * @param id
   * @return
   */
  @Override
  public int sendEmailAfterUpdateUser(User user) throws MailException {
    User result = findByEmail(user.getEmail());
    if (result == null)
      return Constants.User.NOT_FIND_EMAIL;
    String active = user.getIsActive() == true ? "Active" : "Not Active";
    /**
     * edit NHPhong: new thread for send email, function always return VALID .
     * if has mailException cannot send email, but password changed.
     */
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      public void run() {
        emailHelper.sendSimpleMessage(Constants.APPLICATION_EMAIL, user.getEmail(),
            Constants.User.SUBJECT_SEND_EMAIL_AFTER_UPDATE_USER,
            user.getUsername() + "     " + Constants.User.TEXT_MESSAGE_CHANGE_ACTIVE + active);
      }
    });
    return Constants.User.VALID;
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 13, 2018
   * @modifier: User
   * @modifier_date: Mar 13, 2018
   * @param id
   * @return
   */
  @Override
  public List<User> findByDepartment(Long id) {
    return userRepository.findByDepartment(id);
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public List<User> getUserByRole(Long roleId) {
    return userRepository.findUserByRole(roleId);
  }
  @Override
  public List<User> findByGroup(Long groupId) {
  	return userRepository.findByGroup(groupId);
  }
  
  @Override
  public List<User> getUserByRoleAndDepartment(Long roleId, Long departmentId) {
  	return userRepository.findUserByRoleAndDepartment(roleId, departmentId);
  }
}
