package com.cmc.recruitment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Comment;
import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.service.CommentService;
import com.cmc.recruitment.service.NotificationService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class CommentController {

  @Autowired
  private CommentService commentService;

  @Autowired
  private NotificationService notificationService;

  @PreAuthorize("hasAnyRole('RRC_LEAD','DU_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @GetMapping(ConstantsUrl.Comment.ALL)
  public ResponseEntity<?> findAll(
      @RequestParam(value = Constants.PARAM.CANDIDATE_ID_PARAM, required = false) Long candidateId,
      @RequestParam(value = Constants.PARAM.INTERVIEW_ID_PARAM, required = false) Long interviewId,
      Pageable pageable) {
    Page<Comment> list;
    try {
      list = commentService.filterComment(null, candidateId, interviewId, pageable);
    } catch (NumberFormatException e) {
      return new ResponseEntity<>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<Comment>>(list, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('GROUP_LEAD','DU_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @PostMapping(ConstantsUrl.Comment.ADD)
  public ResponseEntity<?> saveComment(@RequestBody String detailComment,
      @RequestParam(value = Constants.PARAM.CANDIDATE_ID_PARAM, required = false) Long candidateId,
      @RequestParam(value = Constants.PARAM.USER_ID_PARAM, required = false) Long userId,
      @RequestParam(value = Constants.PARAM.INTERVIEW_ID_PARAM, required = false) Long interviewId) {
    try {
      Comment comment = commentService.addComment(candidateId, userId, interviewId, detailComment);
      Notification notification = new Notification();
      notification.setContent("commented");
      notificationService.sendNotification(notification, candidateId, userId, interviewId);
      return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Constants.RESPONSE.INTERNAL_ERROR_SERVICE,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAnyRole('RRC_LEAD','DU_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @GetMapping(ConstantsUrl.Comment.BYCV)
  public ResponseEntity<?> findByCvId(@PathVariable Long cvId) {
    List<Comment> list;
    try {
      list = commentService.findByCvId(cvId);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
  }
}
