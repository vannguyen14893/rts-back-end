package com.cmc.recruitment.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.CandidateStatus;
import com.cmc.recruitment.entity.Comment;
import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.entity.Log;
import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.CandidateService;
import com.cmc.recruitment.service.CandidateStatusService;
import com.cmc.recruitment.service.CommentService;
import com.cmc.recruitment.service.InterviewService;
import com.cmc.recruitment.service.InterviewStatusService;
import com.cmc.recruitment.service.LogService;
import com.cmc.recruitment.service.NotificationService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class InterviewController {
  @Autowired
  InterviewService interviewService;
  
  @Autowired
  InterviewStatusService interviewStatusService;

  @Autowired
  CandidateService candidateService;

  @Autowired
  CandidateStatusService candidateStatusService;

  @Autowired
  UserService userService;

  @Autowired
  private LogService logService;

  @Autowired
  private NotificationService notificationService;
  
  @Autowired
  private CommentService commentService;

  /**
   * 
   * @description:
   * @author: vthung
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @GetMapping(ConstantsUrl.Interview.FILTER)
  public ResponseEntity<?> filterInterviewDu(Pageable pageable,
      @RequestParam(required = false, value = "interviewerId") Long interviewerId,
      @RequestParam(required = false, value = "requestId") Long requestId,
      @RequestParam(required = false, value = "statusId") Long interviewStatusId,
      @RequestParam(required = false, value = "startDate") String startDate,
      @RequestParam(required = false, value = "endDate") String endDate,
      @RequestParam(required = false, value = "candidateName") String candidateName,
      @RequestParam(required = false, value = "candidateId") Long candidateId,
      @RequestParam(required = false, value = "title") String title,
      @RequestParam(required = false, value = "departmentId") Long departmentId) {
    Page<Interview> list;
    try {
      String newStartDate = (!StringUtils.isEmpty(startDate)) ? startDate : null;
      String newEndDate = (!StringUtils.isEmpty(endDate)) ? endDate : null;
      list = interviewService.filterInterview(departmentId, requestId, interviewerId,
          interviewStatusId, newStartDate, newEndDate, candidateName, candidateId, title, pageable);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<Interview>>(list, HttpStatus.OK);
  }

  /**
   * 
   * @description: get a interview
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @GetMapping(ConstantsUrl.Interview.DETAIL)
  public ResponseEntity<?> findOne(@PathVariable long interviewId) {
    Interview list;
    try {
      list = interviewService.findOne(interviewId);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Interview>(list, HttpStatus.OK);
  }

  /**
   * 
   * @description: add a interview
   * @author: nvquy1
   * @create_date: 12/3/2018
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.Interview.ADD)
  public ResponseEntity<?> Add(@RequestBody Interview interview, @Validated BindingResult result) {
    try {
      User user = userService.getUserInfoByUserName().get(0);
      if (result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.EXCEPTION_CODE,
            HttpStatus.BAD_REQUEST);
      }
      if (interview.getStartTime().before(new Date())) {
        return new ResponseEntity<String>(Constants.RESPONSE.TIME_IS_IN_THE_PAST,
            HttpStatus.BAD_REQUEST);
      }
      if (interview.getUserCollection() != null
          && !interviewService.validateTimeForInterviewer(interview).isEmpty()) {
        return new ResponseEntity<>(interviewService.validateTimeForInterviewer(interview),
            HttpStatus.BAD_REQUEST);
      }
      interview.setStatusId(interviewStatusService.findByTitle(Constants.INTERVIEW_STATUS_NEW));
      Interview save = interviewService.save(interview);
      if (!save.getUserCollection().isEmpty()) {
        String toEmail[] = new String[save.getUserCollection().size()];
        int index = 0;
        for (User tmp : save.getUserCollection()) {
          toEmail[index] = tmp.getEmail();
          index++;
        }
        interviewService.sendMailAddinterviewer(
            Constants.REPLY_EMAIL.REQUEST_URL_DETAIL
                + interview.getCandidateCollection().get(0).getRequestId().getId(),
            toEmail, interview.getStartTime().toString(), interview.getEndTime().toString(),
            interview.getLocation());
      }

      Notification notification = new Notification();
      notification.setContent("created interview ");
      notification.setNotificationType("3");
      notificationService.sendNotification(notification, user, interview);
      logService.save(new Log(user, Constants.ACTIONS.CREATE, new Date(), Constants.TABLE.INTERVIEW,
          "", null, null, null, save.getId(), null));
      return new ResponseEntity<Interview>(save, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.EXCEPTION_CODE,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 
   * 
   * @description: update 1 interview
   * @author: nvquy1
   * @create_date: 15/3/2018
   * @modifer:
   * @modifer_date: 9/7/2018
   * @param
   * @return
   */
  @PutMapping("interviews/update")
  public ResponseEntity<?> Update(@RequestBody Interview interview,
      @Validated BindingResult result) {
    try {
      if (result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.EXCEPTION_CODE,
            HttpStatus.BAD_REQUEST);
      }
      if (!interviewService.validateTimeForInterviewer(interview).isEmpty()) {
        return new ResponseEntity<>(interviewService.validateTimeForInterviewer(interview),
            HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<Interview>(interviewService.update(interview), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.EXCEPTION_CODE, HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * @description:
   * @author: pxhoang
   * @create_date: 23/4/2018
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @GetMapping(ConstantsUrl.Interview.FIND_BY_CANDIDATE)
  public ResponseEntity<?> findInterviews(Pageable pageable, @PathVariable long candidateId) {
    Page<Interview> list;
    try {
      list = interviewService.findInterviewByCandidate(candidateId, pageable);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<Interview>>(list, HttpStatus.OK);
  }

  /**
   * @description: .
   * @author: PXHoang
   * @created_date: Apr 24, 2018
   * @modifier: User
   * @modifier_date: Apr 24, 2018
   * @param interviewers
   * @param interviewId
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.Interview.ADD_LIST_INTERVIEWER)
  public ResponseEntity<?> addInterviewer(@RequestBody Long[] userIds,
      @RequestParam(required = false, value = "interviewId") Long interviewId)
      throws MessagingException {
    User user = userService.getUserInfoByUserName().get(0);
    List<User> interviewers = new ArrayList<User>();
    for (Long id : userIds) {
      User tmp = userService.findOne(id);
      if (tmp != null) {
        interviewers.add(tmp);
      }
    }
    Interview interview = interviewService.findOne(interviewId);
    Set<User> assigneeCollection = interview.getUserCollection();
    for (User tmp : interviewers) {
      assigneeCollection.add(tmp);
    }
    interview.setUserCollection(assigneeCollection);
//    List<User> invalidUser = interviewService.validateTimeForInterviewerReturnUser(interview);
    List<User> invalidUser = new ArrayList<>();
    String toEmail[] = new String[interviewers.size()];
    for (int i = 0; i < interviewers.size(); i++) {
      toEmail[i] = interviewers.get(i).getEmail();
    }
    if (invalidUser.isEmpty()) {
      for (User u : interviewers) {
        logService.save(new Log(user, Constants.ACTIONS.ADD_INTERVIEWER, new Date(),
            Constants.TABLE.INTERVIEW, "", null, u.getId(), null, null, interviewId, null));
      }
      interviewService.sendMailAddinterviewer(
          Constants.BASE_URL+"request/"
              + interview.getCandidateCollection().get(0).getRequestId().getId(),
          toEmail, interview.getStartTime().toString(), interview.getEndTime().toString(),
          interview.getLocation());
      return new ResponseEntity<Interview>(interviewService.save(interview), HttpStatus.OK);
    }
    return new ResponseEntity<List<User>>(invalidUser, HttpStatus.BAD_REQUEST);
  }

  /**
   * @description: .
   * @author: PXHoang
   * @created_date: Apr 24, 2018
   * @modifier: User
   * @modifier_date: Apr 24, 2018
   * @param candidates
   * @param interviewId
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.Interview.ADD_LIST_CANDIDATE)
  public ResponseEntity<?> addCandidate(@RequestBody List<Candidate> candidates,
      @RequestParam(value = "interviewId") Long interviewId) {
    Interview interview;
    try {
      User user = userService.getUserInfoByUserName().get(0);
      CandidateStatus cs = candidateStatusService.findByTitle("Interview");
      candidates = candidateService.changeStatus(candidates, cs.getId());
      interview = interviewService.addCandidate(candidates, interviewId);
      interviewService.save(interview);
      for (Candidate can : candidates) {
        logService.save(new Log(user, Constants.ACTIONS.ADD_CANDIDATE, new Date(),
            Constants.TABLE.INTERVIEW, "", null, null, can.getId(), interviewId, null));
      }
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Interview>(interview, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.Interview.MEETING_REQUEST)
  public ResponseEntity<?> sendMeetingRequest(@RequestBody Interview interview) {
    try {
      Interview interviewCurrent = interviewService.findOne(interview.getId());
      Set<User> interviewers = interviewCurrent.getUserCollection();
      String toMail[] = new String[interviewers.size()];
      int index=0;
      for (User user: interviewers) {
        toMail[index++] = user.getEmail();
      }

      interviewService.sendMeetingRequest(Constants.APPLICATION_EMAIL, toMail,
          Constants.ACTIONS.SEND_MEETING_REQUEST, interviewCurrent.getStartTime().toString(),
          interviewCurrent.getEndTime().toString(), interviewCurrent.getLocation(), "");
      return new ResponseEntity<Interview>(interviewCurrent, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @PostMapping(ConstantsUrl.Interview.COMMENT)
  public ResponseEntity<?> commentInterview(
		  @RequestParam(required = false, value = "candidateId") Long candidateId,
		  @RequestParam(required = false, value = "interviewId") Long interviewId,
		  @RequestBody String stringComment
     ) {
    Interview interview = new Interview();
    try {
    	Candidate candidate = candidateService.findOne(candidateId);
    	interview = interviewService.findOne(interviewId);
        if (candidate == null || interview == null) {
          return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
              Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
        }
        User user = userService.getUserInfoByUserName().get(0);
        logService.save(new Log(user, Constants.ACTIONS.COMMENT, new Date(),
            Constants.TABLE.INTERVIEW, stringComment, null, null, candidateId, interviewId, null));
        List<Comment>listComment = candidate.getCommentCollection();
        Comment comment = commentService.addComment(candidateId, user.getId(), interviewId, stringComment);
        listComment.add(comment);
        candidate.setCommentCollection(listComment);
        candidateService.updateCandidate(candidate);
      return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
