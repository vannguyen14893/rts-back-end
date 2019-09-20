package com.cmc.recruitment.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.UserRepository;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.RoleService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.EmailHelper;
import com.cmc.recruitment.utils.UpLoadFiles;
import com.cmc.recruitment.utils.UserDto;

@RestController
public class UserController {

  @Autowired
  UserService userService;

//  @Autowired
//  TokenStore tokenStore;
//
//  @Autowired
//  ConsumerTokenServices tokenServices;
//
//  @Autowired
//  DefaultTokenServices defaultTokenServices;

  @Autowired
  UpLoadFiles uploadFiles;
  
  @Autowired
  RoleService roleService;
  @Autowired
  private UserRepository userRepository;
  @GetMapping("/users")
  public ResponseEntity<Page<User>> getListStUserByParrent(Pageable pageable) {
    Page<User> list = userService.findAllUser(pageable);
    return new ResponseEntity<Page<User>>(list, HttpStatus.OK);
  }

  /**
   * @author vcthanh
   * @param
   * @return User Lay thong tin user dang dang nhap
   */
  @PreAuthorize("permitAll()")
  @GetMapping("/users/userInfo")
  public ResponseEntity<List<User>> getUserInfo() {
    List<User> userInfo = userService.getUserInfoByUserName();
    return new ResponseEntity<List<User>>(userInfo, HttpStatus.OK);
  }

  /**
   * @author lcnguyen
   * @param user
   * @return add User
   */
  @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> addSecurity(@RequestBody @Validated User user, BindingResult result) {
    String pass = null;
    if (user.getUsername().length() <= 0)
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.USER_USERNAME_INVALID), HttpStatus.BAD_REQUEST);
    if (user.getEmail().length() <= 0)
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.USER_EMAIL_INVALID), HttpStatus.BAD_REQUEST);
    try {
      User checkEmail = userService.findByEmail(user.getEmail());
      if (checkEmail != null) {
        return new ResponseEntity<User>(userService.findByEmail(user.getEmail()), HttpStatus.OK);
      }
      User checkUsername = userService.findByEmail(user.getEmail());
      if (checkUsername != null) {
        return new ResponseEntity<User>(userService.findByUsername(user.getUsername()),
            HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Create random password and setPassword.
    try {
      EmailHelper help = new EmailHelper();
      pass = help.createRandomPassWord();
      user.setPassword(pass);
      user.setIsActive(true);
      userService.addUser(user);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Send mail to user.
    try {
      userService.sendEmailAfterCreateUser(user, pass);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.ERROR_NOT_SEND_EMAIL), HttpStatus.OK);
    }
    return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_STATUS,
        Constants.RESPONSE.SUCCESS_CODE, Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
  }

  /**
   * @author lcnguyen
   * @param User
   * @return ResponseEntity<Void> update info User
   */
  @PutMapping("/users/{userId}")
  public ResponseEntity<BaseResponse> updatesecurity(@RequestBody User receive, @PathVariable long userId) {
    User user = userService.findOne(userId);
    
    if (receive.getUsername().length() <= 0)
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.USER_USERNAME_INVALID), HttpStatus.BAD_REQUEST);
    if (receive.getEmail().length() <= 0)
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.USER_EMAIL_INVALID), HttpStatus.BAD_REQUEST);
    try {
      user.setUsername(receive.getUsername());
      user.setEmail(receive.getEmail());
      user.setFullName(receive.getFullName());
      user.setDepartmentId(receive.getDepartmentId());
//      if(!user.getRoleCollection().equals(receive.getRoleCollection())) {
//    	  OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext()
//    	          .getAuthentication();
//    	  String clientId = auth.getOAuth2Request().getClientId();
//    	  ArrayList<OAuth2AccessToken> listAccessToken = (ArrayList<OAuth2AccessToken>) tokenStore
//    	          .findTokensByClientIdAndUserName(clientId, user.getUsername());
//    	  for (OAuth2AccessToken i : listAccessToken) {
//    	    tokenServices.revokeToken(i.getValue());
//    	  }
//      }
      user.setRoleCollection(receive.getRoleCollection());
      user.setGroupCollection(receive.getGroupCollection());
      userService.saveUser(user);
      userService.sendEmailAfterUpdateUser(user);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_MESSAGE),
        HttpStatus.OK);
  }

  /**
   * @author lcnguyen.
   * @param String
   *          newPassword.
   * @return ResponseEntity<Void> updatePassword.
   */
  @PutMapping("/users/change-password")
  public ResponseEntity<BaseResponse> updatePassword(@RequestBody User changeForm) {
    User user = new User();
    List<User> users = userService.getUserInfoByUserName();
    user = users == null ? null : users.get(0);
    // Login user is null.
    if (user == null) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.USER_IS_NULL),
          HttpStatus.BAD_REQUEST);
    }
    // Get result from service.
    String oldPass = changeForm.getPassword().trim();
    String newPass = changeForm.getNewPassword().trim();
    if (oldPass == null || newPass == null) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_DATA), HttpStatus.OK);
    }
    changeForm.setPassword(oldPass);
    changeForm.setNewPassword(newPass);
    try {
      int result = userService.updatePassword(user, changeForm.getPassword(),
          changeForm.getNewPassword());
      switch (result) {
      case Constants.CHANGE_PASS_STATUS.INCORRECT_OLD_PASSWORD:
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS, Constants.RESPONSE.EXCEPTION_CODE,
                Constants.RESPONSE.INCORRECT_OLD_PASSWORD),
            HttpStatus.OK);
      case Constants.CHANGE_PASS_STATUS.INVALID_PASS_LENGTH:
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS, Constants.RESPONSE.EXCEPTION_CODE,
                Constants.RESPONSE.INVALID_PASS_LENGTH),
            HttpStatus.OK);
      case Constants.CHANGE_PASS_STATUS.SAME_OLD_PASS:
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS, Constants.RESPONSE.EXCEPTION_CODE,
                Constants.RESPONSE.SAME_OLD_PASS),
            HttpStatus.OK);
      default: {
//        OAuth2AccessToken newToken = null;
//        OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext()
//            .getAuthentication();
//        String clientId = auth.getOAuth2Request().getClientId();
//        ArrayList<OAuth2AccessToken> listAccessToken = (ArrayList<OAuth2AccessToken>) tokenStore
//            .findTokensByClientIdAndUserName(clientId, user.getUsername());
//        for (OAuth2AccessToken i : listAccessToken) {
//          tokenServices.revokeToken(i.getValue());
//          newToken = defaultTokenServices.createAccessToken(auth);
//        }
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_STATUS,
            Constants.RESPONSE.SUCCESS_CODE, Constants.RESPONSE.SUCCESS_MESSAGE, null),
            HttpStatus.CREATED);
      }

      }
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_DATA),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @author vcthanh.
   * @param User
   * @return ResponseEntity<Void> delete user
   */
  @PreAuthorize("permitAll()")
  @DeleteMapping("/users/{userId}")
  public ResponseEntity<BaseResponse> deleteOrgChart(@PathVariable long userId) {
	  if(userId == 0) {
		  return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
		          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_DATA),
		          HttpStatus.BAD_REQUEST);
	  }
    try {
      userService.deleteUser(userId);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.EXCEPTION_MESSAGE),
          HttpStatus.EXPECTATION_FAILED);
    }
    return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_STATUS,
        Constants.RESPONSE.SUCCESS_CODE, Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
  }

  /**
   * 
   * @description: Reset password in enter email.
   * @author: NHPhong.
   * @create_date: Jan 22, 2018.
   * @modifer: NHPhong.
   * @modifer_date: Jan 22, 2018.
   * @param passResetUser
   * @return state of response.
   */
  @PostMapping("/users/reset-password")
  public ResponseEntity<BaseResponse> resetPassword(@RequestBody User passResetUser) {
    // check input.
    if (passResetUser == null) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_DATA),
          HttpStatus.BAD_REQUEST);
    }
    // input not null; call reset password service.
    try {
      switch (userService.resetPassword(passResetUser)) {
      case Constants.User.NOT_FIND_EMAIL:
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS, Constants.RESPONSE.EXCEPTION_CODE,
                Constants.RESPONSE.ERROR_NOT_FIND),
            HttpStatus.OK);

      case Constants.User.NOT_SAVE:
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS, Constants.RESPONSE.EXCEPTION_CODE,
                Constants.RESPONSE.ERROR_CAN_NOT_SAVE),
            HttpStatus.INTERNAL_SERVER_ERROR);
      default:
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_STATUS,
            Constants.RESPONSE.SUCCESS_CODE, Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
      }
    } catch (MailException e) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_NOT_SEND_EMAIL),
          HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_DATA),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 
   * @description: user is interviewer
   * @author: vthung
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @GetMapping("/users/interviewer")
  public ResponseEntity<?> getInterviewers() {
    return ResponseEntity.status(HttpStatus.OK).body(userService.getInterviewers());
  }

  /**
   * @description: get list user is assignee
   * @author: PXHoang
   * @created_date: Feb 27, 2018
   * @modifier: User
   * @modifier_date: Feb 27, 2018
   * @return
   */
  @GetMapping("/users/assignee")
  public ResponseEntity<List<User>> getListUserIsAssignee() {

    try {
      Role role = roleService.findByTitle(Constants.ROLE.ROLE_HR_MEMBER);
      List<User> list = userService.getUserByRole(role.getId());
      if (list.size() > 0)
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
      return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description: .get user by department
   * @author: vdhoan
   * @created_date: Mar 12, 2018
   * @modifier: User
   * @modifier_date: Mar 12, 2018
   * @return
   */
  @GetMapping("/users/departments")
  public ResponseEntity<?> findAll(
      @RequestParam(value = Constants.PARAM.ID_PARAM, required = false) long id) {
    List<User> list;
    try {
      list = userService.findByDepartment(id);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<List<User>>(list, HttpStatus.OK);
  }

  /**
   * @description: get list user created request.
   * @author: PXHoang
   * @created_date: Feb 27, 2018
   * @modifier: User
   * @modifier_date: Feb 27, 2018
   * @return
   */

  @GetMapping("/users/creator")
  public ResponseEntity<List<User>> getListUserIsCreator() {
    try {
      List<User> list = userService.getUserIsCreator();
      if (list.size() > 0)
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
      return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description: filter user
   * @author: Lcnguyen
   * @created_date: Mar 09, 2018
   * @modifier: User
   * @modifier_date: Mar 09, 2018
   * @return
   */
  @GetMapping("/users/filter")
  public ResponseEntity<?> filterUser(Pageable pageable,
      @RequestParam(required = false, value = "roleId") Long roleId,
      @RequestParam(required = false, value = "input") String input) {
    Page<User> list = null;
    try {
      list = userService.filterUser(pageable, roleId, input);
      if (list.getSize() == 0) {
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
            Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<User>>(list, HttpStatus.OK);
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/users/{userId}")
  public ResponseEntity<?> detailUser(@PathVariable int userId) {
    User user = null;
    try {
      user = userService.findOne(userId);
      if (user == null) {
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
            Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<User>(user, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("/users/check-email")
  public ResponseEntity<?> checkEmail(@RequestBody String email) {
    User result = null;
    try {
      result = userService.findByEmail(email);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (result != null)
      return new ResponseEntity<Long>(result.getId(), HttpStatus.OK);
    return new ResponseEntity<BaseResponse>(
        new BaseResponse(Constants.RESPONSE.USER_EMAIL_NOT_EXISTED), HttpStatus.NOT_FOUND);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("/users/check-username")
  public ResponseEntity<?> checkUsername(@RequestBody String username) {
    User result = null;
    try {
      result = userService.findByUsername(username);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (result != null)
      return new ResponseEntity<Long>(result.getId(), HttpStatus.OK);
    return new ResponseEntity<BaseResponse>(
        new BaseResponse(Constants.RESPONSE.USER_USERNAME_NOT_EXISTED), HttpStatus.NOT_FOUND);
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PostMapping("/users/{userId}/setactive")
  public ResponseEntity<?> setActive(@RequestParam boolean isActive, @PathVariable int userId) {
    User user = new User();
    try {
      user = userService.findOne(userId);
      if (user == null)
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
            Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
      user.setIsActive(isActive);
      userService.updateUser(user);
//      OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext()
//          .getAuthentication();
//      String clientId = auth.getOAuth2Request().getClientId();
//      ArrayList<OAuth2AccessToken> listAccessToken = (ArrayList<OAuth2AccessToken>) tokenStore
//          .findTokensByClientIdAndUserName(clientId, user.getUsername());
//      for (OAuth2AccessToken i : listAccessToken) {
//        tokenServices.revokeToken(i.getValue());
//      }
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<BaseResponse>(
        new BaseResponse(Constants.RESPONSE.USER_UPDATE_SUCCESS), HttpStatus.OK);
  }

  // edit NHPhong
  @PostMapping("/users/avatar")
  public ResponseEntity<?> upImage(
      @RequestParam(value = "avatar", required = false) MultipartFile avatar) {
    if (avatar == null) {
      return new ResponseEntity<String>(Constants.RESPONSE.NO_INPUT, HttpStatus.BAD_REQUEST);
    }
    String result;
    try {
      result = uploadFiles.UploadAvatar(avatar, Constants.Upload.FODER_UPLOADED_PROFILE_IMAGE);
      return (avatar.getOriginalFilename().equals(result))
          ? new ResponseEntity<String>(result, HttpStatus.OK)
          : new ResponseEntity<String>(result, HttpStatus.BAD_REQUEST);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("permitAll()")
  @GetMapping("/users/roles/{roleId}")
  public ResponseEntity<List<User>> getListUserByRole(@PathVariable Long roleId) {

    try {
      List<User> list = userService.getUserByRole(roleId);
      if(roleId==5) {
    	  User user = userService.getUserInfoByUserName().get(0);
    	  list.add(user);
      }
      if (list.size() > 0)
        return new ResponseEntity<List<User>>(list, HttpStatus.OK);
      return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @PostMapping(value="/check-user/ams")
  public ResponseEntity<UserDto> checkUserAmsInfo(@RequestBody UserDto userDto){
	  User userOld=userRepository.loadUserByUsername(userDto.getUserName());
	  if(userOld==null) {  
				userDto=userService.updateUserLdap(userDto,"insert");
				//save
	         }
			else {
				userDto=userService.updateUserLdap(userDto,"update");
			}
		   
	  return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
  }
}
