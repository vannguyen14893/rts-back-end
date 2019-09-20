package com.cmc.recruitment.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
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
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.CandidateService;
import com.cmc.recruitment.service.CandidateStatusService;
import com.cmc.recruitment.service.CommentService;
import com.cmc.recruitment.service.LogService;
import com.cmc.recruitment.service.NotificationService;
import com.cmc.recruitment.service.RequestAssigneeService;
import com.cmc.recruitment.service.RequestService;
import com.cmc.recruitment.service.RequestStatusService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;
import com.cmc.recruitment.utils.ReportByGroup;

@RestController
public class CandidateController {
  @Autowired
  private CandidateService candidateService;
  @Autowired
  private CommentService commentService;
  @Autowired
  UserService userService;
  @Autowired
  private RequestService requestService;
  @Autowired
  private RequestAssigneeService requestAssigneeService;
  @Autowired
  private RequestStatusService requestStatusService;
  @Autowired
  private LogService logService;
  @Autowired
  private CandidateStatusService candidateStatusService;
  @Autowired
  private NotificationService notificationService;

  /**
   * @description:get candidate's detail info
   * @author: lcnguyen
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER','ROLE_GROUP_LEAD','ROLE_DU_MEMBER')")
  @GetMapping(ConstantsUrl.Candidate.DETAIL)
  public ResponseEntity<?> detailCandidate(@PathVariable int candidateId) {
    Candidate candidate = new Candidate();
    try {
      candidate = candidateService.findOne(candidateId);
      if (candidate == null) {
        return new ResponseEntity<String>(Constants.RESPONSE.NOT_FOUND, HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.INTERNAL_SERVER_ERROR + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    Collections.sort(candidate.getCommentCollection(), new Comparator<Comment>() {
      @Override
      public int compare(Comment o1, Comment o2) {
        return o2.getCreateDate().compareTo(o1.getCreateDate());
      }
    });
    User user = userService.getUserInfoByUserName().get(0);
    user.getRoleCollection().contains(new Role("HR_MANAGER"));
//    if (checkRoleDU(user)) {
//      if (candidate.getRequestId().getCreatedBy().getDepartmentId().getId() != user
//          .getDepartmentId().getId()) {
//        return new ResponseEntity<String>(Constants.RESPONSE.ERROR_UNAUTHORIZE,
//            HttpStatus.UNAUTHORIZED);
//      }
//    }
    return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
  }

  /**
   * @description: create new candidate
   * @author: lcnguyen
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.Candidate.ADD)
  public ResponseEntity<?> postDetailCandidate(@RequestBody Candidate candidate) {
    User user = userService.getUserInfoByUserName().get(0);
    try {
      Candidate result = candidateService.saveCandidate(candidate);
      // Write log
      logService.save(new Log(user, Constants.ACTIONS.CREATE, new Date(), Constants.TABLE.CANDIDATE,
          "", null, null, result.getId(), null, null));
      return new ResponseEntity<>(Constants.RESPONSE.SUCCESS_CODE, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 
   * @description:
   * @author: lcnguyen
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
//  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PutMapping(ConstantsUrl.Candidate.COMMENT)
  public ResponseEntity<?> commentCandidate(@PathVariable Long candidateId,
      @RequestBody String stringComment) {
    Candidate candidate = new Candidate();
    try {
      candidate = candidateService.findOne(candidateId);
      if (candidate == null) {
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
            Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
      }
      User user = userService.getUserInfoByUserName().get(0);
      logService.save(new Log(user, Constants.ACTIONS.COMMENT, new Date(),
          Constants.TABLE.CANDIDATE, stringComment, null, null, candidateId, null, null));
      Comment comment = new Comment();
      comment.setCandidateId(candidate);
      comment.setCommentDetail(stringComment);
      Date today = new Date();
      comment.setCreateDate(today);
      comment.setUserId(user);

      commentService.save(comment);
      Collections.sort(candidate.getCommentCollection(), new Comparator<Comment>() {
        @Override
        public int compare(Comment o1, Comment o2) {
          return o2.getCreateDate().compareTo(o1.getCreateDate());
        }
      });
      return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * 
   * @description:
   * @author: lcnguyen
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PutMapping(ConstantsUrl.Candidate.UPDATE_STATUS)
  public ResponseEntity<?> updateStatusCandidate(@PathVariable int candidateId,
      @RequestBody CandidateStatus status) {
    Candidate candidate = candidateService.findOne(candidateId);
    try {
      candidate.setStatusId(status);
      candidateService.saveCandidate(candidate);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_CAN_NOT_SAVE,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
  }

  /**
   * 
   * @description:
   * @author: lcnguyen
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
//  private boolean checkRoleDU(User user) {
//    Set<Role> roles = user.getRoleCollection();
//    for (Role role : roles) {
//      if ("ROLE_DU_LEAD".equals(role.getRoleName()) || "ROLE_DU_MEMBER".equals(role.getRoleName()))
//        return true;
//    }
//    return false;
//  }

  /**
   * @description: filter candidates.
   * @author: VDHoan
   * @created_date: Feb 27, 2018
   * @modifier: User
   * @modifier_date: Feb 27, 2018
   * @param experienceId
   * @param requestId
   * @param statusId
   * @param pageable
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER','ROLE_GROUP_LEAD','ROLE_DU_MEMBER')")
  @GetMapping(ConstantsUrl.Candidate.ALL)
  public ResponseEntity<?> findAll(
      @RequestParam(value = Constants.PARAM.EXPERIENCE_ID_PARAM, required = false) String experienceId,
      @RequestParam(value = Constants.PARAM.REQUEST_ID_PARAM, required = false) String requestId,
      @RequestParam(value = Constants.PARAM.STATUS_ID_PARAM, required = false) String statusId,
      @RequestParam(value = Constants.PARAM.FIRST_NAME_PARAM, required = false) String fullName,
      @RequestParam(value = Constants.PARAM.HRMEMBER_ID_PARAM, required = false) String hrmemberId,
      Pageable pageable) {
    Page<Candidate> list;

    try {
      User user = userService.getUserInfoByUserName().get(0);
      Long newExperienceId = (!StringUtils.isEmpty(experienceId)) ? Long.parseLong(experienceId)
          : null;
      Long newRequestId = (!StringUtils.isEmpty(requestId)) ? Long.parseLong(requestId) : null;
      Long newStatusId = (!StringUtils.isEmpty(statusId)) ? Long.parseLong(statusId) : null;
      String newFullName = (!StringUtils.isEmpty(fullName)) ? fullName : null;
      Long newHrmemberId = (!StringUtils.isEmpty(hrmemberId)) ? Long.parseLong(hrmemberId) : null;
      // Neu la HrMember thi chi xem duoc candidate minh tao.
      boolean isHrMember = false;
      for (Role role : user.getRoleCollection()) {
        if (role.getRoleName().equals(Constants.ROLE_HR_MEMBER)) {
          isHrMember = true;
        }
      }
      if (isHrMember) {
        newHrmemberId = user.getId();
      }
      list = candidateService.filterCandidate(newExperienceId, newRequestId, newStatusId,
          newFullName, newHrmemberId, pageable);
      Request request = requestService.findOne(newRequestId);
      boolean isDuMember = false;
      boolean duMemberCanSeeListCandidate = false;
      for (Role role : user.getRoleCollection()) {
        if (role.getRoleName().equals(Constants.ROLE.ROLE_DU_MEMBER)) {
          isDuMember = true;
        }
      }

      Interview interview = new Interview();
      if (isDuMember) {
        Iterator<Interview> iter = user.getInterviewCollection().iterator();
        while (iter.hasNext()) {
          interview = iter.next();
          if (!interview.getCandidateCollection().isEmpty() && interview.getCandidateCollection()
              .get(0).getRequestId().getId() == request.getId()) {
            duMemberCanSeeListCandidate = true;
          }
        }
      }
      if (isDuMember && !duMemberCanSeeListCandidate) {
        return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<Page<Candidate>>(list, HttpStatus.OK);

    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description get candidate by requestId
   * @param requestId
   * @param pageable
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER','ROLE_GROUP_LEAD')")
  @GetMapping(ConstantsUrl.Candidate.FIND_BY_REQUEST)
  public ResponseEntity<?> findCandidatesByRequest(@PathVariable long requestId,
      Pageable pageable) {
    Page<Candidate> list = null;
    try {
      list = candidateService.findCandidatesByRequestId(requestId, pageable);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<Candidate>>(list, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER','ROLE_GROUP_LEAD')")
  @GetMapping(ConstantsUrl.Candidate.FIND_BY_REQUEST_AND_STATUS)
  public ResponseEntity<?> findCandidatesByStatus(@PathVariable long requestId,
      @PathVariable long statusId, Pageable pageable) {
    Page<Candidate> list = null;
    try {
      list = candidateService.findCandidatesByRequestIdAndStatusId(requestId, statusId, pageable);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<Candidate>>(list, HttpStatus.OK);
  }

  /**
   * @description get all list candidate not pageable
   * @param id
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER','ROLE_RRC_LEAD')")
  @GetMapping(ConstantsUrl.Candidate.FIND_BY_REQUEST_UNLIMIT)
  public ResponseEntity<?> findByRequest(@PathVariable long requestId) {
    List<Candidate> list;
    try {
      list = candidateService.findByRequestId(requestId);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<List<Candidate>>(list, HttpStatus.OK);
  }

  /**
   * 
   * @description: find candidate by requestId to make into interviewer, phan
   *               quyen theo role
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_DU_LEAD','ROLE_HR_MEMBER','ROLE_HR_MANAGER','ROLE_GROUP_LEAD')")
  @GetMapping(ConstantsUrl.Candidate.FIND_TO_MAKE_INTERVIEW)
  public ResponseEntity<?> findByRequest_AbleToMakeInterview(@PathVariable long requestId) {
    List<Candidate> list;
    try {
      list = candidateService.findByRequestIdToMakeInterview(requestId);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<List<Candidate>>(list, HttpStatus.OK);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 9, 2018
   * @modifier: User
   * @modifier_date: Apr 9, 2018
   * @param listCandidate
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping("/make/one-candidate")
  public ResponseEntity<?> makeCandidate(@RequestBody @Validated Candidate candidate,
      BindingResult result) {
    try {
      if (result.hasErrors() || !isOneRequestAndValidRequest(candidate))
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT,
            HttpStatus.NOT_ACCEPTABLE);
      candidate = candidateService.saveCandidate(candidate);
      if (candidate != null) {
        // Write log
        User user = userService.getUserInfoByUserName().get(0);
        logService.save(new Log(user, Constants.ACTIONS.CREATE, new Date(),
            Constants.TABLE.CANDIDATE, "", null, null, candidate.getId(), null, null));
        return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.DUPLICATE_MESSAGE,
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.DUPLICATE_MESSAGE, HttpStatus.BAD_GATEWAY);
  }

  private boolean isOneRequestAndValidRequest(Candidate candidate) throws Exception {

    User user = userService.getUserInfoByUserName().get(0);
    List<Long> requestStatusId = new ArrayList<Long>();
    requestStatusId.add(requestStatusService.findByTitle("Assign").getId());
    List<Request> list = requestService.findAssignedRequestsBelongAssignee(requestStatusId,
        user.getId());
    for (Request r : list) {
      if (r.getId() == candidate.getRequestId().getId())
        return true;
    }
    return false;
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.Candidate.CHANGE_STATUS)
  public ResponseEntity<?> changeCandidateStatus(
      @RequestParam(value = Constants.PARAM.ID_PARAM, required = false) Long id,
      @RequestParam(value = Constants.PARAM.STATUS_ID_PARAM, required = false) Long statusId) {
    Candidate candidate = null;
    try {
      candidateService.changeStatusId(id, statusId);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.Candidate.CHANGE_STATUS_LIST)
  public ResponseEntity<?> changeCandidateStatus(@RequestBody List<Candidate> candidates,
      @RequestParam(value = "candidateStatusId") Long candidateStatusId) {
    try {
      Notification notification = new Notification();
      User user = userService.getUserInfoByUserName().get(0);
      CandidateStatus candidateStatus = candidateStatusService.findOneById(candidateStatusId);
      String content = candidates.get(0).getStatusId().getTitle() + " to "
          + candidateStatus.getTitle();
      candidates = candidateService.changeStatus(candidates, candidateStatusId);
      for (Candidate candidate : candidates) {
        candidateService.saveCandidate(candidate);
        notification.setContent("changed status from " + content);
        notification.setNotificationType("1");
        notificationService.sendNotification(notification, candidate, user, null);
        // Write log
        logService.save(new Log(user, Constants.ACTIONS.CHANGE_STATUS, new Date(),
            Constants.TABLE.CANDIDATE, content, null, null, candidate.getId(), null, null));
      }
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.SUCCESS_CODE, HttpStatus.OK);
  }

  /**
   * NHPhong: function make 1 request and list Cv. - if: input null -> no input
   * status bad_request. - if: REQUEST_NOT_EXISTS -> bad request save: status ok:
   * return - list Message =null thành công toàn bộ list Message != null thông
   * báo: - Candidate exists, cv không tồn tại hoặc không thể lưu candidate với
   * các candidate vẫn thỏa mãn vẫn đc lưu vào database mà không có thông báo gì.
   * satuts của trường hợp này là badrequest
   * 
   * @param arrayCvIds
   * @param requestId
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.Candidate.MAKE_CANDIDATE_LIST)
  public ResponseEntity<?> makeCandidate(
      @RequestParam(value = "arrayCvIds", required = false) @PathVariable Long[] arrayCvIds,
      @RequestParam(value = "requestId", required = false) Long requestId) {
    List<String> message = new LinkedList<String>();
    try {
      User user = userService.getUserInfoByUserName().get(0);
      if (arrayCvIds.length == 0 || requestId == null) {
        message.add("not find requestid");
        return new ResponseEntity<List<String>>(message, HttpStatus.BAD_REQUEST);
      }
      if (requestService.findOne(requestId) == null) {
        message.add(Constants.RESPONSE.REQUEST_NOT_EXISTS);
        return new ResponseEntity<List<String>>(message, HttpStatus.BAD_REQUEST);
      }
      // kiem tra xem thang nay dc assigne chua
      if (requestAssigneeService.findByRequestIdAndAssigneeId(requestId, user.getId()) == null) {
        message.add("You cant'n make candidate because request not assign for you");
        return new ResponseEntity<List<String>>(message, HttpStatus.BAD_REQUEST);
      }
      List<String> listMessage = candidateService.makeCandidate(requestId, arrayCvIds);
      if (listMessage != null && !listMessage.isEmpty()) {
        return new ResponseEntity<List<String>>(listMessage, HttpStatus.BAD_REQUEST);
      }
      message.add(Constants.RESPONSE.SUCCESS_MESSAGE);
      return new ResponseEntity<List<String>>(message, HttpStatus.OK);
    } catch (Exception e) {
      message.add(Constants.RESPONSE.SERVER_ERROR);
      return new ResponseEntity<List<String>>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER','ROLE_DU_LEAD')")
  @GetMapping(ConstantsUrl.Candidate.REJECT)
  public ResponseEntity<?> rejectCandidate(
      @RequestParam(value = Constants.PARAM.ID_PARAM, required = false) Long id) {
    try {
      CandidateStatus candidateStatus = candidateStatusService
          .findByTitle(ConstantsUrl.CandidateStatus.CLOSED);
      Candidate candidate = candidateService.findOne(id);
      candidate.setStatusId(candidateStatus);
      candidateService.saveCandidate(candidate);
      return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(ConstantsUrl.Candidate.MAKE_CANDIDATE)
  public ResponseEntity<?> updateCandidate(@RequestBody @Validated Candidate candidate,
      BindingResult result) {
    try {
      String content = new String();
      Candidate original = candidateService.findOne(candidate.getId());
      if (!candidate.getTitle().equals(original.getTitle())
          && !candidate.getSource().equals(original.getSource())) {
        content = "Update title, source.";
      } else if (!candidate.getTitle().equals(original.getTitle())) {
        content = "Update title to " + candidate.getTitle();
      } else if (!candidate.getSource().equals(original.getSource())) {
        content = "Update source to " + candidate.getSource();
      } else {
        content = "";
      }
      candidate = candidateService.updateCandidate(candidate);
      if (candidate != null) {
        // Write log
        User user = userService.getUserInfoByUserName().get(0);
        Log log = new Log(user, Constants.ACTIONS.UPDATE, new Date(), Constants.TABLE.CANDIDATE, "",
            null, null, candidate.getId(), null, null);
        log.setAction(content);
        logService.save(log);
      }
      return new ResponseEntity<Candidate>(candidate, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(ConstantsUrl.Candidate.STATISTIC_CANDIDATE_BY_GROUP)
  public ResponseEntity<?> statisticCandidateByGroup(@RequestParam(value = Constants.PARAM.YEAR, required = false) Long year) {
    try {
      if(year==null) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        year = Long.parseLong(currentYear+"");
      }
      List<ReportByGroup> reportByGroups = candidateService.statisticCandidateByGroup(year);
      return new ResponseEntity<List<ReportByGroup>>(reportByGroups, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
    }
  }

  // Thống kê theo từng department


  /**
   * @description: report am.
   * @author: nvquy1
   * @created_date: Jun 25, 2018
   * @modifier: User
   * @modifier_date: Jun 25, 2018
   * @return list candidate for report am
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MANAGER','ROLE_GROUP_LEAD','ROLE_HR_MEMBER')")
  @GetMapping(ConstantsUrl.Candidate.REPORT_AM)
  public ResponseEntity<?> reportAM(
      @RequestParam(required = false, value = "hrmemberId") Long hrmemberId,
      @RequestParam(required = false, value = "fromDate") String fromDate,
      @RequestParam(required = false, value = "toDate") String toDate, Pageable pageable) {
    Page<Candidate> list;
    try {

      list = candidateService.getReportAM(hrmemberId, fromDate, toDate,
          candidateStatusService.findByTitle(Constants.ONBOARD).getId(), pageable);
      return new ResponseEntity<Page<Candidate>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
