package com.cmc.recruitment.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Cv;
import com.cmc.recruitment.entity.Group;
import com.cmc.recruitment.entity.Log;
import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.entity.Position;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.RequestAssignee;
import com.cmc.recruitment.entity.RequestStatus;
import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.AccountGlobalService;
import com.cmc.recruitment.service.CandidateService;
import com.cmc.recruitment.service.CvService;
import com.cmc.recruitment.service.DepartmentService;
import com.cmc.recruitment.service.ExchangeApiService;
import com.cmc.recruitment.service.ExperienceService;
import com.cmc.recruitment.service.GroupService;
import com.cmc.recruitment.service.LogService;
import com.cmc.recruitment.service.NotificationService;
import com.cmc.recruitment.service.PositionService;
import com.cmc.recruitment.service.PriorityService;
import com.cmc.recruitment.service.ProjectService;
import com.cmc.recruitment.service.RecruitmentTypeService;
import com.cmc.recruitment.service.RequestAssigneeService;
import com.cmc.recruitment.service.RequestService;
import com.cmc.recruitment.service.RequestStatusService;
import com.cmc.recruitment.service.RoleService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;
import com.cmc.recruitment.utils.ReportDTO;

import edu.emory.mathcs.backport.java.util.Arrays;

@RestController

public class RequestController {

  @Autowired
  RequestService requestService;

  @Autowired
  UserService userService;

  @Autowired
  RequestAssigneeService requestAssigneeService;

  @Autowired
  RequestStatusService requestStatusService;

  @Autowired
  LogService logService;

  @Autowired
  CvService cvService;

  @Autowired
  CandidateService candidateService;

  @Autowired
  RoleService roleService;

  @Autowired
  NotificationService notificationService;

  @Autowired
  PositionService positionService;

  @Autowired
  DepartmentService departmentService;

  @Autowired
  ProjectService projectService;

  @Autowired
  GroupService groupService;

  @Autowired
  RecruitmentTypeService recruitmentTypeService;

  @Autowired
  PriorityService priorityService;

  @Autowired
  ExperienceService experienceService;

  @Autowired
  AccountGlobalService accountGlobalService;

  @Autowired
  private ExchangeApiService exchangeApiService;

  private final Logger LOGGER = Logger.getLogger(this.getClass());

  /**
   * 
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @PreAuthorize("hasAnyRole('DU_LEAD','GROUP_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.FILTER)
  public ResponseEntity<?> filterRequest(Pageable pageable,
      @RequestParam(required = false, value = "skillId") Long skillId,
      @RequestParam(required = false, value = "positionId") Long positionId,
      @RequestParam(required = false, value = "daysAgo") Long daysAgo,
      @RequestParam(required = false, value = "title") String title,
      @RequestParam(required = false, value = "requestStatusId") Long[] requestStatusId,
      @RequestParam(required = false, value = "createdBy") Long createdBy,
      @RequestParam(required = false, value = "assigneeId") Long assigneeId,
      @RequestParam(required = false, value = "departmentId") Long[] departmentIds,
      @RequestParam(required = false, value = "priorityId") Long priorityId,
      @RequestParam(required = false, value = "groupId") Long groupId) {
    try {

      User user = userService.getUserInfoByUserName().get(0);
      // Náº¿u User Ä‘Äƒng nháº­p lÃ  role DU thÃ¬ chá»‰ xem Ä‘Æ°á»£c nhá»¯ng request cá»§a phÃ²ng ban
      // cá»§a mÃ¬nh.
      boolean isRoleDU = false;
      for (Role role : user.getRoleCollection()) {
        if (Constants.ROLE.ROLE_DU_LEAD.equals(role.getRoleName())
            || Constants.ROLE.ROLE_DU_MEMBER.equals(role.getRoleName()))
          isRoleDU = true;
      }
      if (isRoleDU && departmentIds != null && departmentIds.length > 0) {
        departmentIds[0] = user.getDepartmentId().getId();
      }
      
      List<Long> listDepartments = null;
      if (departmentIds != null && departmentIds.length > 0) {
        listDepartments = Arrays.asList(departmentIds);
      }

      // Kiá»ƒm tra xem ngÆ°á»�i Ä‘Äƒng nháº­p cÃ³ pháº£i HrManager khÃ´ng
      boolean isRoleHrManager = false;
      for (Role role : user.getRoleCollection()) {
        if (Constants.ROLE.ROLE_HR_MANAGER.equals(role.getRoleName()))
          isRoleHrManager = true;
      }

      boolean isHrMember = false;
//      for (Role role : user.getRoleCollection()) {
//        if (role.getRoleName().equals(Constants.ROLE_HR_MEMBER)) {
//          isHrMember = true;
//        }
//      }
      if (isHrMember) {
        assigneeId = user.getId();
      }
      Page<Request> list = null;
      // Láº¥y ra táº¥t cáº£ cÃ¡c status user Ä‘Æ°á»£c quyá»�n xem
      List<Long> status = getRequestStatusByRole();
      title = (title == null || StringUtils.isEmpty(title)) ? "" : title;

      if (requestStatusId == null && isRoleHrManager) {
        status.remove(2);
        status.remove(3);
        list = requestService.filterRequest(status, skillId, positionId, daysAgo, title, createdBy,
            assigneeId, listDepartments, priorityId, groupId, pageable);
        return new ResponseEntity<Page<Request>>(list, HttpStatus.OK);
      }

      if (requestStatusId == null) {
        list = requestService.filterRequest(status, skillId, positionId, daysAgo, title, createdBy,
            assigneeId, listDepartments, priorityId, groupId, pageable);
        return new ResponseEntity<Page<Request>>(list, HttpStatus.OK);
      }
      boolean isContain = true;
      List<Long> listRequestStatusId = new ArrayList<Long>();
      if (requestStatusId.length != 0) {
        listRequestStatusId = Arrays.asList(requestStatusId);
        for (Long item : listRequestStatusId) {
          if (!status.contains(item)) {
            isContain = false;
          }
        }
      } else {
        isContain = false;
      }

      if (isContain) {
        List<Long> requestStatusIds = new ArrayList<Long>();
        for (Long item : listRequestStatusId) {
          requestStatusIds.add(item);
        }
        list = requestService.filterRequest(requestStatusIds, skillId, positionId, daysAgo, title,
            createdBy, assigneeId, listDepartments, priorityId, groupId, pageable);
      } else {
        // Náº¿u role Ä‘Äƒng nháº­p lÃ  role Hr Manager thÃ¬ máº·c Ä‘á»‹nh hiá»ƒn thá»‹ sáº½ lÃ  cÃ¡c request
        // cÃ³ tráº¡ng thÃ¡i lÃ  Approved vÃ  In-Progress (Bá»� tráº¡ng thÃ¡i Closed)
        if (isRoleHrManager) {
          status.remove(2);
          status.remove(3);
        }
        list = requestService.filterRequest(status, skillId, positionId, daysAgo, title, createdBy,
            assigneeId, listDepartments, priorityId, groupId, pageable);
      }
      if(!isHrMember && assigneeId != null) {
    	  for (Request request : list.getContent()) {
              requestService.removeOthersAsignee(request, assigneeId);
          }
      }
      if (isHrMember) {
        for (Request request : list.getContent()) {
          requestService.removeOthersAsignee(request, user.getId());
        }
      }
      return new ResponseEntity<Page<Request>>(list, HttpStatus.OK);
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
  @PreAuthorize("hasAnyRole('DU_LEAD','HR_MANAGER','GROUP_LEAD')")
  @PostMapping(ConstantsUrl.Request.ADD)
  public ResponseEntity<BaseResponse> addRequest(@RequestBody Request request) {
    boolean validated = validateInput(request);
    if (validated == false) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_DATA),
          HttpStatus.BAD_REQUEST);
    }

    else {
      // Check if can save request.
      User user = userService.getUserInfoByUserName().get(0);
      boolean isGroupLead = false;
      for (Role role : user.getRoleCollection()) {
        if (Constants.ROLE.ROLE_GROUP_LEAD.equals(role.getRoleName()))
          isGroupLead = true;
      }
      request.setCreatedBy(user);
      request.setRequestStatusId(new RequestStatus(1L));
      if (isGroupLead) {
        RequestStatus pending = requestStatusService.findByTitle(Constants.PENDING);
        request.setRequestStatusId(pending);
      }
      request.setCreatedDate(new Date());
      // start-add requestCode
      int recentYear = Calendar.getInstance().get(Calendar.YEAR);
      Integer numberOfRequestInRecentYear = requestService
          .getNumberOfRequestInRecentYear(recentYear) + 1;
      String code = new String();
      for (int i = 0; i < Constants.FORMAT_NUMBER_OF_REQUEST_CODE
          - numberOfRequestInRecentYear.toString().length(); i++) {
        code += Constants.CHARACTER_ZERO;
      }
      request
          .setRequestCode(Constants.REQUEST_CODE + recentYear + code + numberOfRequestInRecentYear);
      // end- add requestCode
      boolean result = false;
      try {
        result = requestService.saveRequest(request);
        System.out.println(result);
        logService.save(new Log(user, Constants.ACTIONS.CREATE, new Date(), Constants.TABLE.REQUEST,
            "", null, request.getId(), null, null, null));
      } catch (Exception e) {
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR,
                Constants.RESPONSE.EXITS_CODE, Constants.RESPONSE.NOT_EXIST),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }

      if (result == false)
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS, Constants.RESPONSE.EXCEPTION_CODE,
                Constants.RESPONSE.ERROR_DATA),
            HttpStatus.BAD_REQUEST);
      else
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_STATUS,
            Constants.RESPONSE.SUCCESS_CODE, Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
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
  @PreAuthorize("hasAnyRole('DU_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER','GROUP_LEAD')")
  @GetMapping(ConstantsUrl.Request.DETAIL)
  public ResponseEntity<?> detailRequest(@PathVariable int requestId) {
    Request request = null;
    // get Request by requestId.
    try {
      request = requestService.findOne(requestId);
      // Check if request null
      if (request == null) {
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
            Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
      }

      User user = userService.getUserInfoByUserName().get(0);
      // get assignee
      RequestAssignee requestAssigneeTemp = new RequestAssignee();
      for (RequestAssignee requestAssignee : request.getRequestAssignee()) {
        if (requestAssignee.getAssignee().getId() == user.getId()) {
          requestAssigneeTemp = requestAssignee;
        }
      }
      // xet role de setAssignee
      for (Role role : user.getRoleCollection()) {
        if (role.getRoleName().equals(Constants.ROLE_HR_MEMBER)) {
          HashSet<RequestAssignee> requestAssigneeSet = new HashSet<RequestAssignee>();
          requestAssigneeSet.add(requestAssigneeTemp);
          request.setRequestAssignee(requestAssigneeSet);
        }
      }

      return new ResponseEntity<Request>(request, HttpStatus.OK);
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
  @PreAuthorize("hasAnyRole('DU_LEAD','HR_MANAGER','GROUP_LEAD')")
  @PostMapping("requests/update/{requestId}")
  public ResponseEntity<BaseResponse> postUpdateRequest(@RequestBody Request request,
      @PathVariable Long requestId) {
    boolean validated = validateInput(request);
    if (validated == false) {
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS,
          Constants.RESPONSE.EXCEPTION_CODE, Constants.RESPONSE.ERROR_DATA),
          HttpStatus.BAD_REQUEST);
    } else {
      // Check if can save request.
      Request requestOld = requestService.findOne(requestId);
      User user = userService.getUserInfoByUserName().get(0);
      request.setId(requestId);
      request.setEditedBy(user);
      request.setEditedDate(new Date());
      request.setPositionId(positionService.findOne(request.getPositionId().getId()));
      request.setDepartmentId(departmentService.findOne(request.getDepartmentId().getId()));
      request.setProjectId(projectService.findOne(request.getProjectId().getId()));
      request.setGroupId(groupService.findOne(request.getGroupId().getId()));
      request.setRecruitmentTypeId(
          recruitmentTypeService.findOne(request.getRecruitmentTypeId().getId()));
      request.setPriorityId(priorityService.findOne(request.getPriorityId().getId()));
      request.setExperienceId(experienceService.findOne(request.getExperienceId().getId()));
      requestOld.editChange(request);// láº¥y nhá»¯ng sá»± thay Ä‘á»•i.
      String editChange = requestOld.getEditChange();
      request.setEditChange(editChange);
      boolean result = false;
      try {
        result = requestService.updateRequest(request);
        logService.save(new Log(user, Constants.ACTIONS.UPDATE, new Date(), Constants.TABLE.REQUEST,
            editChange, null, request.getId(), null, null, null));
      } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR,
                Constants.RESPONSE.EXITS_CODE, e.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }

      if (result == false)
        return new ResponseEntity<BaseResponse>(
            new BaseResponse(Constants.RESPONSE.EXCEPTION_STATUS, Constants.RESPONSE.EXCEPTION_CODE,
                Constants.RESPONSE.ERROR_DATA),
            HttpStatus.BAD_REQUEST);
      else
        return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_STATUS,
            Constants.RESPONSE.SUCCESS_CODE, Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
    }
  }

  @PreAuthorize("hasAnyRole('DU_LEAD','HR_MANAGER')")
  @GetMapping(ConstantsUrl.Request.CLONE)
  public ResponseEntity<Request> cloneRequest(@RequestBody Request request) {
    return new ResponseEntity<Request>(request, HttpStatus.OK);
  }

  /**
   * 
   * @description: Validate all fields from request.
   * @author: lcnguyen
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  private boolean validateInput(Request request) {
    // Validate request must not null.
    if (request == null) {
      return false;
    }
    // Required fields.
    String title = request.getTitle();
    Position position = request.getPositionId();
    Date deadline = request.getDeadline();
    Integer number = request.getNumber();
    String description = request.getDescription();

    // Validate required fields is not null.
    if (title == null || position == null || deadline == null || number == null
        || description == null) {
      return false;
    }
    // Validate required fields is not blank.
    if (title.isEmpty() || number <= 0 || description.isEmpty()) {
      return false;
    }
    // Check length of all fields.
    if (request.getTitle().length() > 255 || request.getNumber() > 1000000
        || request.getDescription().length() > 3000 || request.getCertificate().length() > 255
        || request.getMajor().length() > 3000 || request.getOthers().length() > 3000) {
      return false;
    }
    return true;
  }

  @PreAuthorize("hasAnyRole('RRC_LEAD','GROUP_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.ALL)
  public ResponseEntity<List<Request>> getListRequestByParrent() {
    List<Request> list = requestService.getAllRequest();
    return new ResponseEntity<List<Request>>(list, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('DU_LEAD')")
  @PostMapping("/send-approve-request")
  public ResponseEntity<?> waitApproveRequest(@RequestBody Request request,
      @RequestParam String url) {
    try {
      String toEmail = request.getCreatedBy().getEmail();
      request.setApprovedDate(new Date());
      requestService.sendMailWaitApproved(url, toEmail);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Request>(request, HttpStatus.OK);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 19, 2018
   * @modifier: User
   * @modifier_date: Mar 19, 2018
   * @param id
   * @return
   */
  @PreAuthorize("hasAnyRole('GROUP_LEAD')")
  @PostMapping(ConstantsUrl.Request.APPROVE)
  public ResponseEntity<?> approveRequest(@RequestBody Request request, @RequestParam String url) {
    try {
      User user = userService.getUserInfoByUserName().get(0);
      request = requestService.approveRequest(request);
      if (request == null)
        return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
      String toMail = request.getCreatedBy().getEmail();
      logService.save(new Log(user, Constants.ACTIONS.APPROVED, new Date(), Constants.TABLE.REQUEST,
          "", null, request.getId(), null, null, null));
      requestService.sendMailApproved(url, toMail);

    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Request>(request, HttpStatus.OK);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 19, 2018
   * @modifier: User
   * @modifier_date: Mar 19, 2018
   * @param request
   * @return
   */
  @PreAuthorize("hasAnyRole('GROUP_LEAD','HR_MANAGER')")
  @PostMapping(ConstantsUrl.Request.REJECT)
  public ResponseEntity<?> rejectRequest(@RequestBody Request request, @RequestParam String url) {
    try {
      User user = userService.getUserInfoByUserName().get(0);
      request = requestService.rejectRequest(request);
      if (request == null)
        return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
      String toMail = request.getCreatedBy().getEmail();
      String comment = request.getRejectReason();
      logService.save(new Log(user, Constants.ACTIONS.REJECTED, new Date(), Constants.TABLE.REQUEST,
          comment, null, request.getId(), null, null, null));
      requestService.sendMailReject(url, comment, toMail);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Request>(request, HttpStatus.OK);
  }

  /**
   * @description: .
   * @author: nvquy
   * @created_date: Mar 19, 2018
   * @modifier: User
   * @modifier_date: Mar 19, 2018
   * @param request
   * @return
   */
  @PreAuthorize("hasAnyRole('HR_MANAGER','DU_LEAD')")
  @PostMapping(ConstantsUrl.Request.CLOSE)
  public ResponseEntity<?> closeRequest(@RequestBody Request request) {
    try {
      request = requestService.closeRequest(request);
      if (request == null) {
        return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
      }

    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Request>(request, HttpStatus.OK);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 19, 2018
   * @modifier: User
   * @modifier_date: Mar 19, 2018
   * @param pageable
   * @return
   */
  @PreAuthorize("hasAnyRole('GROUP_LEAD')")
  @GetMapping(ConstantsUrl.Request.FIND_BY_STATUS)
  public ResponseEntity<?> findByPendingStatus(@PathVariable long statusId, Pageable pageable) {
    Page<Request> list;
    try {
      list = requestService.findByStatus(statusId, pageable);
      if (list.getSize() == 0)
        return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
    } catch (NumberFormatException e) {
      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Page<Request>>(list, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('DU_LEAD','HR_MANAGER')")
  @PostMapping(ConstantsUrl.Request.SUBMIT)
  public ResponseEntity<?> submitNewRequest(@RequestBody Request request,
      @RequestParam String url) {
    try {
      if (!"New".equals(request.getRequestStatusId().getTitle()))
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT,
            HttpStatus.NOT_ACCEPTABLE);
      User user = userService.getUserInfoByUserName().get(0);
      Role role = roleService.findByTitle(Constants.ROLE.ROLE_GROUP_LEAD);
      List<User> recipients = userService.getUserByRole(role.getId());
      String toEmail = new String();
      for (User item : recipients) {
        for (Group group : item.getGroupCollection()) {
          if (group.getId() == request.getGroupId().getId()) {
            toEmail = item.getEmail();
            break;
          }
        }
      }

      requestService.sendMailWaitApproved(url, toEmail);
      request = requestService.submitNewRequest(request);
      requestService.saveRequest(request);
      if (request != null) {
        logService.save(new Log(user, Constants.ACTIONS.SUBMIT, new Date(), Constants.TABLE.REQUEST,
            "", null, request.getId(), null, null, null));
        return new ResponseEntity<Request>(request, HttpStatus.OK);
      }

    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  @PreAuthorize("hasAnyRole('HR_MANAGER')")
  @PostMapping(ConstantsUrl.Request.ASSIGN)
  public ResponseEntity<?> assignRequest(
      @RequestBody List<RequestAssignee> requestAssigneeCollection,
      @RequestParam(value = "requestId", required = true) Long requestId) {

    Request request = requestService.findOne(requestId);
    try {
      Set<RequestAssignee> newSet = new HashSet<RequestAssignee>();
      requestAssigneeService.deleteAssigneByRequest(requestId);
      for (RequestAssignee ra : requestAssigneeCollection) {
        RequestAssignee tmp = requestAssigneeService.save(ra);
        newSet.add(tmp);
      }
      request.setRequestAssignee(newSet);
      requestService.saveRequest(request);
      User user = userService.getUserInfoByUserName().get(0);
      RequestStatus requestStatus = requestStatusService.findByTitle("In-Progress");
      request.setRequestStatusId(requestStatus);
      requestService.saveRequest(request);
      for (RequestAssignee ra : requestAssigneeCollection) {
        RequestAssignee raTemp = requestAssigneeService.findByRequestIdAndAssigneeId(requestId,
            ra.getAssignee().getId());
        if (raTemp == null) {// TÃ¬m tháº¥y RequestAssignee trong database thÃ¬ sáº½ sá»­a.
          requestAssigneeService.save(ra);
          logService
              .save(new Log(user, Constants.ACTIONS.ASSIGN, new Date(), Constants.TABLE.REQUEST, "",
                  ra.getAssignee().getId(), ra.getRequest().getId(), null, null, null));
        } else {
          ra.setId(raTemp.getId());
          requestAssigneeService.save(ra);
          logService
              .save(new Log(user, Constants.ACTIONS.ASSIGN, new Date(), Constants.TABLE.REQUEST, "",
                  ra.getAssignee().getId(), ra.getRequest().getId(), null, null, null));
        }
      }
      String toEmail[] = new String[requestAssigneeCollection.size()];
      Notification notification = new Notification();
      notification.setContent("assigned");
      for (int i = 0; i < requestAssigneeCollection.size(); i++) {
        User itemAssignee = userService
            .findOne(requestAssigneeCollection.get(i).getAssignee().getId());
        toEmail[i] = itemAssignee.getEmail();
        notificationService.sendNotification(notification, user, request, itemAssignee);
      }
      requestService.sendMailAfterAssign(Constants.BASE_URL + "request/" + request.getId(),
          toEmail);

      // Set<RequestAssignee> allRequestAssigneeOfRequestInDB = requestAssigneeService
      // .findAllRequestAssigneeOfRequest(request.getId());
      // for (RequestAssignee ra : allRequestAssigneeOfRequestInDB) {
      // if (!requestAssigneeCollection.contains(ra)) {
      // requestAssigneeService.deleteRequestAssignee(ra);
      // }
      // }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Request>(request, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('HR_MANAGER','HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.ASSIGNEE)
  public ResponseEntity<?> getAllRequestAssignee() {
    try {
      List<RequestAssignee> list = requestAssigneeService.findAll();
      if (list.size() > 0)
        return new ResponseEntity<List<RequestAssignee>>(list, HttpStatus.OK);
      return new ResponseEntity<List<RequestAssignee>>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<List<RequestAssignee>>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description: get all request by array status request.
   * @author: NHPhong.
   * @create_date: Mar 27, 2018
   * @param arrayTitleSatusRequest
   * @return
   */
  @PreAuthorize("hasAnyRole('GROUP_LEAD','DU_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.ALL_BY_STATUS_REQUEST)
  public ResponseEntity<?> getAllRequestByTitle(
      @RequestParam(value = "arraySatusRequest", required = false) String[] arraySatusRequest) {
    try {
      if (arraySatusRequest.length > 0) {
        List<Request> listRequest = requestService.listRequestByTitleId(arraySatusRequest);
        return (listRequest.size() > 0) ? new ResponseEntity<>(listRequest, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(Constants.RESPONSE.NO_INPUT, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      LOGGER.error(" error get request by list status " + e);
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description: hr member or hr manager find all assigned request those belong
   *               to them.
   * @author: VDHoan
   * @created_date: Apr 6, 2018
   * @modifier: User
   * @modifier_date: Apr 6, 2018
   * @param pageable
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.Request.BELONG_TO_ASSINGEE)
  public ResponseEntity<?> findAssignedRequestsBelongAssignee() {
    try {
      User user = userService.getUserInfoByUserName().get(0);
      List<Long> requestStatusIds = new ArrayList<Long>();
      requestStatusIds.add(requestStatusService.findByTitle("In-Progress").getId());
      List<Request> list = requestService.findAssignedRequestsBelongAssignee(requestStatusIds,
          user.getId());
      if (list.size() > 0)
        return new ResponseEntity<List<Request>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
  }

  private ArrayList<Long> getRequestStatusByRole() {
    User user = userService.getUserInfoByUserName().get(0);
    ArrayList<Long> result = new ArrayList<Long>();
    Set<Role> status = user.getRoleCollection();
    for (Role id : status) {
      switch (id.getRoleName()) {
      case "ROLE_HR_MANAGER":
        result.add(1L);
        result.add(2L);
        result.add(4L);
        result.add(5L);
        result.add(6L);
        break;

      case "ROLE_HR_MEMBER":
    	  result.add(1L);
          result.add(2L);
          result.add(3L);
          result.add(4L);
          result.add(5L);
          result.add(6L);
        break;

      case "ROLE_GROUP_LEAD":
        result.add(2L);
        result.add(3L);
        result.add(4L);
        result.add(5L);
        result.add(6L);
        break;

      case "ROLE_DU_LEAD":
        result.add(1L);
        result.add(2L);
        result.add(3L);
        result.add(4L);
        result.add(5L);
        result.add(6L);
        break;

      case "ROLE_DU_MEMBER":
        result.add(2L);
        break;
      }
    }
    return result;
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
  @PreAuthorize("hasAnyRole('ROLE_GROUP_LEAD','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.Request.REPORT_GROUP_LEADER)
  public ResponseEntity<?> viewReportForRRCLead() {
    ArrayList<ReportDTO> list = null;
    try {
      list = requestService.getReport();
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<List<ReportDTO>>(list, HttpStatus.OK);
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
  @PreAuthorize("hasRole('ROLE_HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.REPORT_HR_MEMBER)
  public ResponseEntity<?> viewReportForHrMember() {
    ArrayList<ReportDTO> list = null;
    try {
      User user = userService.getUserInfoByUserName().get(0);
      list = requestService.getReportHrMember(user.getId());
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<List<ReportDTO>>(list, HttpStatus.OK);
  }

  @GetMapping("/requests/get-by-cv")
  public ResponseEntity<?> getListRequestByCvId(
      @RequestParam(required = false, value = "cvId") Long cvId) {
    try {
      Cv cv = cvService.findOne(cvId);
      if (cv == null) {
        return new ResponseEntity<String>(Constants.RESPONSE.NOT_FOUND, HttpStatus.NOT_FOUND);
      }
      List<Candidate> candidates = candidateService.findByCvId(cv);
      if (candidates == null) {
        return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
      }
      List<Request> requests = new ArrayList<Request>();
      for (Candidate c : candidates) {
        requests.add(c.getRequestId());
      }
      return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description: Sprint 9, EDIT ASIGNEEE
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @PreAuthorize("hasAnyRole('HR_MANAGER')")
  @PostMapping(ConstantsUrl.Request.EDIT_ASSIGN)
  public ResponseEntity<?> editAssignRequest(
      @RequestBody List<RequestAssignee> requestAssigneeCollectionNew,
      @RequestParam(value = "requestId", required = true) Long requestId) {
    Request request = requestService.findOne(requestId);
    try {
      for (RequestAssignee requestAssigneeNew : requestAssigneeCollectionNew) {
        RequestAssignee requestAssigneeOld = requestAssigneeService
            .findOne(requestAssigneeNew.getId());

        if (requestAssigneeOld == null) {
          requestAssigneeService.save(requestAssigneeNew);
        } else {
          changeCreatedByOfCandidate(request.getId(), requestAssigneeOld.getId(),
              requestAssigneeNew.getId());
          requestAssigneeService.save(requestAssigneeNew);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(Constants.RESPONSE.SUCCESS_MESSAGE, HttpStatus.OK);
  }

  /**
   * @description: Sprint 9, EDIT ASIGNEEE
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  private void changeCreatedByOfCandidate(Long requestId, Long asigneeIdOld, Long asigneeIdNew) {
    candidateService.changeCreatedBy(requestId, asigneeIdOld, asigneeIdNew);
  }

  /**
   * 
   * @description:
   * @author: nvquy1
   * @create_date: 29/06/2018
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @PreAuthorize("hasAnyRole('DU_LEAD','GROUP_LEAD','DU_MEMBER','HR_MANAGER','HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.REPORT_RP)
  public ResponseEntity<?> getReportRP(
      @RequestParam(required = false, value = "fromDate") String fromDate,
      @RequestParam(required = false, value = "toDate") String toDate, Pageable pageable) {
    try {
      Page<Request> list = requestService.getReportRP(fromDate, toDate, pageable);
      return new ResponseEntity<Page<Request>>(list, HttpStatus.OK);
    }

    catch (Exception e) {

      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // API get token.
  @GetMapping(ConstantsUrl.Request.GET_TOKEN_GLOBAL)
  public ResponseEntity<?> getTokenFromGlobal() {
    try {
      String tokenGlobal = exchangeApiService.loginRecruit();
      return new ResponseEntity<String>(tokenGlobal, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(ConstantsUrl.Request.PUBLISH_REQUEST)
  public ResponseEntity<?> publishRequest(
      @RequestParam(required = false, value = "requestId") Long requestId) {
    try {
      exchangeApiService.loginRecruit();
      Boolean status = exchangeApiService.publishRecruitContent(requestId);
      return new ResponseEntity<Boolean>(status, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR + e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}