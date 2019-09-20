package com.cmc.recruitment.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Log;
import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.service.LogService;
import com.cmc.recruitment.service.NotificationService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class LogController {

  @Autowired
  private LogService logService;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private NotificationService notificationService;

  @GetMapping(ConstantsUrl.Log.ALL)
  public ResponseEntity<?> findAll(
		  @RequestParam(value = Constants.PARAM.REQUEST_ID_PARAM, required = false) Long requestId,
		  @RequestParam(value = Constants.PARAM.CANDIDATE_ID_PARAM, required = false) Long candidateId,
	      @RequestParam(value = Constants.PARAM.CV_ID_PARAM, required = false) Long cvId,
	      @RequestParam(value = Constants.PARAM.INTERVIEW_ID_PARAM, required = false) Long interviewId,
	      @RequestParam(value = Constants.PARAM.ACTION, required = false) String action,
      Pageable pageable) {
    Page<Log> list;
    try {
    	list = logService.findAll(requestId, candidateId, cvId, interviewId, action, pageable);
    } catch (NumberFormatException e) {
      return new ResponseEntity<>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<Log>>(list, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('GROUP_LEAD','DU_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @PostMapping(ConstantsUrl.Log.ADD)
  public ResponseEntity<?> saveLog(@RequestBody String detailComment,
      @RequestParam(value = Constants.PARAM.CANDIDATE_ID_PARAM, required = false) Long candidateId,
      @RequestParam(value = Constants.PARAM.USER_ID_PARAM, required = false) Long userId,
      @RequestParam(value = Constants.PARAM.INTERVIEW_ID_PARAM, required = false) Long interviewId) {
    try {
      User user =  userService.getUserInfoByUserName().get(0);
      Log log = new Log(user, Constants.ACTIONS.COMMENT, new Date(), Constants.TABLE.CANDIDATE, detailComment, null, null, null, candidateId, null, null);
      Log result = logService.save(log);
      Notification notification = new Notification();
      notification.setContent("commented");
      notification.setNotificationType("2");
      notificationService.sendNotification(notification, candidateId, userId, interviewId);
      return new ResponseEntity<Log>(result, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Constants.RESPONSE.INTERNAL_ERROR_SERVICE,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
