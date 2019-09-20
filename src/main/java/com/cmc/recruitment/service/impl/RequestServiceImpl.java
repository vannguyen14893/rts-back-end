package com.cmc.recruitment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.CandidateStatus;
import com.cmc.recruitment.entity.Log;
import com.cmc.recruitment.entity.Project;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.RequestAssignee;
import com.cmc.recruitment.entity.RequestStatus;
import com.cmc.recruitment.entity.Skill;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.CandidateRepository;
import com.cmc.recruitment.repository.CandidateStatusRepository;
import com.cmc.recruitment.repository.ProjectRepository;
import com.cmc.recruitment.repository.RequestRepository;
import com.cmc.recruitment.repository.RequestStatusRepository;
import com.cmc.recruitment.service.LogService;
import com.cmc.recruitment.service.ProjectService;
import com.cmc.recruitment.service.RequestService;
import com.cmc.recruitment.service.SkillService;
import com.cmc.recruitment.specification.RequestSpecification;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.EmailHelper;
import com.cmc.recruitment.utils.ReportDTO;

@Service
public class RequestServiceImpl implements RequestService {

  @Autowired
  private CandidateStatusRepository candidateStatusRepository;

  @Autowired
  private RequestRepository requestRepository;

  @Autowired
  private RequestStatusRepository requestStatusRepository;

  @Autowired
  private EmailHelper emailHelper;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  ProjectService projectService;

  @Autowired
  LogService logService;
  
  @Autowired
  SkillService skillService;

  @Override
  public Request findOne(long id) {
    return requestRepository.findOne(id);
  }

  @Override
  public List<Request> getAllRequest() {
    return requestRepository.findAll();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public boolean saveRequest(Request request) {
    try {
    	// Check project đã tồn tại chưa.
      Project projectCheck = projectService.findByTitle(request.getProjectId().getTitle().trim());
      if (projectCheck == null) {
        Project project = new Project();
        project.setTitle(request.getProjectId().getTitle());
        Project projectRef = projectService.createOrUpdate(project);
        logService.save(new Log(request.getCreatedBy(), Constants.ACTIONS.CREATE, new Date(),
            "project", "", null, projectRef.getId(), null, null, null));
        request.setProjectId(projectRef);
      } else {
        request.setProjectId(projectCheck);
      }
      // Check Skill đã tồn tại chưa.
      Set<Skill> skills = request.getSkillCollection();
      Set<Skill> saveSkills = new HashSet<Skill>();
      Iterator<Skill> iter = skills.iterator();
      while(iter.hasNext()) {
    	  Skill skill = iter.next();
    	  Skill temp = skillService.getSkillByTitle(skill.getTitle());
    	  if(temp == null) {
    		  Skill save = new Skill(skill.getTitle());
    		  skillService.addSkill(save);
    		  saveSkills.add(save);
    	  }else {
    		  saveSkills.add(temp);
    	  }
      }
      request.setSkillCollection(saveSkills);
      Request result = requestRepository.save(request);
      if (result == null) {
        return false;
      }

    } catch (Exception e) {
      return false;
    }
    return true;
  }
  
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public boolean updateRequest(Request request) {
    try {
    	// Check project đã tồn tại chưa.
      Project projectCheck = projectService.findOne(request.getProjectId().getId());
      if (projectCheck == null) {
        Project project = new Project();
        project.setTitle(request.getProjectId().getTitle());
        Project projectRef = projectService.createOrUpdate(project);
        logService.save(new Log(request.getCreatedBy(), Constants.ACTIONS.CREATE, new Date(),
            "project", "", null, projectRef.getId(), null, null, null));
        request.setProjectId(projectRef);
      } else {
        request.setProjectId(projectCheck);
      }
      // Check Skill đã tồn tại chưa.
      Set<Skill> skills = request.getSkillCollection();
      Set<Skill> saveSkills = new HashSet<Skill>();
      Iterator<Skill> iter = skills.iterator();
      while(iter.hasNext()) {
    	  Skill skill = iter.next();
    	  skill = skillService.findOne(skill.getId());
    	  Skill temp = skillService.getSkillByTitle(skill.getTitle());
    	  if(temp == null) {
    		  Skill save = new Skill(skill.getTitle());
    		  skillService.addSkill(save);
    		  saveSkills.add(save);
    	  }else {
    		  saveSkills.add(temp);
    	  }
      }
      request.setSkillCollection(saveSkills);
      Request result = requestRepository.save(request);
      if (result == null) {
        return false;
      }

    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public Page<Request> filterRequest(List<Long> requestStatusId, Long skillId, Long positionId,
      Long daysAgo, String title, Long createdBy, Long assigneeId, List<Long> departmentId, Long priority,
      Long groupId, Pageable pageable) {
    Page<Request> list = requestRepository
        .findAll(new RequestSpecification(requestStatusId, skillId, positionId, daysAgo, title,
            createdBy, assigneeId, departmentId, priority, groupId), pageable);
    List<CandidateStatus> candidateStatus = candidateStatusRepository.findAll();
    for (Request request : list.getContent()) {
      request.setCountRequestStatus(
          countRequestStatus(request.getCandidateCollection(), candidateStatus));
      if(request.getCountRequestStatus()!=null && request.getCountRequestStatus().get("Onboard") != null) {
    	  int onboard = request.getCountRequestStatus().get("Onboard");
    	  if(onboard >= request.getNumber()) {
    		  request.setIsFinish(true);
    	  }
      }
      
      // request.setCountCanidateStatusByRequestAssignee(countStatusCanidateByRequestAssignee(request.getRequestAssignee(),request.getId()));
      request.setRequestAssignee(
          updateCandidateStatusByUser(request.getRequestAssignee(), request.getId()));

      // tính % ngày đã chạy đến ngày deadline
      Date currentDate = new Date();
      if (request.getApprovedDate() == null) {
        request.setPercentDealine(0);
      } else {
        int percentDeadLine = (int) ((((double)currentDate.getTime() - request.getApprovedDate().getTime())
            / (request.getDeadline().getTime() - request.getApprovedDate().getTime())) * 100);
        request.setPercentDealine(percentDeadLine);
      }
    }
    return list;
  }

  /**
   * @description:
   * @author: Ho Dung
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return đếm các trạng thái của candidate trong 1 request
   */
  private Map<String, Integer> countRequestStatus(List<Candidate> list,
      List<CandidateStatus> candidateStatus) {
    Map<String, Integer> cout = new HashMap<String, Integer>();
    for (Candidate candi : list) {
      if (candidateStatus.contains(candi.getStatusId())) {
        if (cout.containsKey(candi.getStatusId().getTitle())) {
          int valueOld = cout.get(candi.getStatusId().getTitle());
          cout.put(candi.getStatusId().getTitle(), ++valueOld);

        } else {
          cout.put(candi.getStatusId().getTitle(), 1);
        }
      }
    }
    return cout;
  }

  /*
   * private Map<Long, Map<String,Integer>>
   * countStatusCanidateByRequestAssignee(Set<RequestAssignee>
   * listRequestAssignee, Long requestId){ Map<Long, Map<String,Integer>> cout =
   * new HashMap<Long, Map<String,Integer>>(); List<CandidateStatus>
   * candidateStatus = candidateStatusRepository.findAll(); for(RequestAssignee
   * listRequestAssi: listRequestAssignee){
   * cout.put(listRequestAssi.getAssignee().getId(),
   * countRequestStatus(candidateRepository.findCandidateByCreateUser(
   * listRequestAssi.getAssignee().getId(), requestId),candidateStatus)); } return
   * cout; }
   */

  private Set<RequestAssignee> updateCandidateStatusByUser(Set<RequestAssignee> listRequestAssignee,
      Long requestId) {
    List<CandidateStatus> candidateStatus = candidateStatusRepository.findAll();
    for (RequestAssignee listRequestAssi : listRequestAssignee) {
      listRequestAssi
          .setCountCandidateStatus(countRequestStatus(candidateRepository.findCandidateByCreateUser(
              listRequestAssi.getAssignee().getId(), requestId), candidateStatus));
    }
    return listRequestAssignee;
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
  @Override
  public Request approveRequest(Request request) {
    RequestStatus status = requestStatusRepository.findByTitle("Approved");
    request.setRequestStatusId(status);
    request.setApprovedDate(new Date());
    return requestRepository.save(request);
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
  @Override
  public Request rejectRequest(Request request) {
    RequestStatus status = requestStatusRepository.findByTitle("Rejected");
    request.setRequestStatusId(status);
    return requestRepository.save(request);
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
  @Override
  public Page<Request> findByStatus(Long statusId, Pageable pageable) {
    return requestRepository.findByStatus(statusId, pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Mar 21, 2018
   * @modifier: User
   * @modifier_date: Mar 21, 2018
   * @param request
   * @return
   */
  @Override
  public Request submitNewRequest(Request request) {
    RequestStatus status = requestStatusRepository.findByTitle("Pending");
    request.setRequestStatusId(status);
    return requestRepository.save(request);
  }

  /**
   * @description:
   * @author: NHPhong.
   * @create_date: Mar 26, 2018
   * @param listTitleRequest
   * @return
   */
  private List<Long> listStatusId(String[] listTitleStatusRequest) {
    List<Long> listStatusId = new LinkedList<Long>();
    for (String title : listTitleStatusRequest) {
      RequestStatus requestStatus = requestStatusRepository.findByTitle(title);
      listStatusId.add(requestStatus.getId());
    }
    return listStatusId;
  }

  /**
   * @description:
   * @author: NHPhong.
   * @create_date: Mar 26, 2018
   * @param listTitleRequest
   * @return
   */
  public List<Request> listRequestByTitleId(String[] listTitleRequest) {
    List<Request> listRequest = new LinkedList<Request>();
    List<Long> listStatusId = listStatusId(listTitleRequest);
    Long[] arrRequestId = listStatusId.toArray(new Long[listStatusId.size()]);
    listRequest = requestRepository.findByListTitle(arrRequestId);
    return listRequest;
  }

  @Override
  public int sendMailApproved(String url, String to) throws MessagingException {
	  String newUrl = Constants.BASE_URL+url;
    String text = Constants.REPLY_EMAIL.REQUEST_APPROVE_CONTENT1 + newUrl
        + Constants.REPLY_EMAIL.REQUEST_APPROVE_CONTENT2 + newUrl
        + Constants.REPLY_EMAIL.REQUEST_APPROVE_CONTENT3;
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      public void run() {
        try {
          emailHelper.sendMessage(Constants.APPLICATION_EMAIL, to,
              Constants.REPLY_EMAIL.REQUEST_APPROVE_SUBJECT, text);
        } catch (MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    return Constants.User.VALID;

  }

  @Override
  public int sendMailReject(String url, String comment, String to) throws MessagingException {
	  String newUrl = Constants.BASE_URL + url;
    String text = Constants.REPLY_EMAIL.REQUEST_REJECT_CONTENT1 + newUrl
        + Constants.REPLY_EMAIL.REQUEST_REJECT_CONTENT2 + newUrl + "<br>Reason is: " + comment
        + Constants.REPLY_EMAIL.REQUEST_REJECT_CONTENT3;
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      public void run() {
        try {
          emailHelper.sendMessage(Constants.APPLICATION_EMAIL, to,
              Constants.REPLY_EMAIL.REQUEST_REJECT_SUBJECT, text);
        } catch (MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    return Constants.User.VALID;
  }

  @Override
  public int sendMailAfterAssign(String url, String[] to) throws MessagingException {
    String text = Constants.REPLY_EMAIL.REQUEST_ASSIGN_CONTENT1 + url
        + Constants.REPLY_EMAIL.REQUEST_ASSIGN_CONTENT2 + url
        + Constants.REPLY_EMAIL.REQUEST_ASSIGN_CONTENT3;
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      public void run() {
        try {
          emailHelper.sendMessage(Constants.APPLICATION_EMAIL, to,
              Constants.REPLY_EMAIL.REQUEST_ASSIGN_SUBJECT, text);
        } catch (MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    return Constants.User.VALID;
  }

   public int sendMailWaitApproved(String url, String toEmail) throws MessagingException {
	   String newUrl = Constants.BASE_URL+url;
    String text = Constants.REPLY_EMAIL.REQUEST_WAIT_APPROVE_CONTENT1 + newUrl
        + Constants.REPLY_EMAIL.REQUEST_WAIT_APPROVE_CONTENT2 + newUrl
        + Constants.REPLY_EMAIL.REQUEST_WAIT_APPROVE_CONTENT3;
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      public void run() {
        try {
        	emailHelper.sendMessage(Constants.APPLICATION_EMAIL, toEmail,
              Constants.REPLY_EMAIL.REQUEST_WAIT_APPROVE_SUBJECT, text);
        } catch (MessagingException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    return Constants.User.VALID;
  }

  /**
   * @description: .
   * @author: nvquy
   * @created_date: Apr 06, 2018
   * @modifier: User
   * @modifier_date:
   * @param
   * @return
   */
  @Override
  public Request closeRequest(Request request) {
    request.setRequestStatusId(requestStatusRepository.findByTitle("Closed"));
    String toEmail = request.getCreatedBy().getEmail();
    sendMailClose(Constants.REPLY_EMAIL.REQUEST_CLOSE_COMMENT + request.getId(), toEmail);
    return requestRepository.save(request);
  }

  /**
   * @description: .
   * @author: nvquy
   * @created_date: Apr 06, 2018
   * @modifier: User
   * @modifier_date:
   * @param
   * @return
   */
   public int sendMailClose(String comment, String toEmail) throws MailException {
    String text = comment + Constants.REPLY_EMAIL.REQUEST_CLOSE;
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      public void run() {
    	  emailHelper.sendSimpleMessage(Constants.APPLICATION_EMAIL, toEmail,
            Constants.REPLY_EMAIL.REQUEST_CLOSE_SUBJECT, text);
      }
    });
    return Constants.User.VALID;
  }

  @Override
  public List<Request> findAssignedRequestsBelongAssignee(List<Long> requestStatusId,
      Long assigneeId) {
    Specification<Request> spec = new RequestSpecification(requestStatusId, assigneeId);
    return requestRepository.findAll(spec);
  }

  /**
   * @description: .
   * @author: nvquy
   * @created_date: May 04, 2018
   * @modifier: User
   * @modifier_date:
   * @param
   * @return
   */
  @Override
  public ArrayList<ReportDTO> getReport() {
    ArrayList<Project> listProject = (ArrayList<Project>) projectRepository.findAll();
    ArrayList<ReportDTO> listReportDTO = new ArrayList<>();
    for (Project project : listProject) {
      ArrayList<Request> requestCollections = new ArrayList<>();
      String title = project.getTitle();
      int orderNumber = 0;
      int orderHrNumber = 0;
      int contacting = 0;
      int interview = 0;
      int passed = 0;
      int offer = 0;
      int onboard = 0;
      int lackNumber = 0;
      double ratio = 0.0;

      for (Request request : project.getRequestCollection()) {
        updateCandidateStatusByUser(request.getRequestAssignee(), request.getId());
        orderNumber += request.getNumber();
        request.setCountRequestStatus(countRequestStatus(request.getCandidateCollection(),
            candidateStatusRepository.findAll()));
        Map<String, Integer> map = (Map<String, Integer>) request.getCountRequestStatus();
        int numberOnboardOfRecentRequest = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
          numberOnboardOfRecentRequest = 0;
          switch (entry.getKey()) {
            case Constants.CONTACTING:
              contacting += entry.getValue();
              break;
            case Constants.INTERVIEW:
              interview += entry.getValue();
              break;
            case Constants.PASSED:
              passed += entry.getValue();
              break;
            case Constants.OFFER:
              offer += entry.getValue();
              break;
            case Constants.ONBOARD:
              onboard += entry.getValue();
              numberOnboardOfRecentRequest++;
              break;
            default:
              break;
          }
        }
        request.setLackNumber(request.getNumber() - numberOnboardOfRecentRequest);
        request.setRatio(
            Math.round((double) numberOnboardOfRecentRequest / orderNumber * 100.0) / 100.0);
        requestCollections.add(request);

      }
      lackNumber = orderNumber - onboard;
      ratio = Math.round((double) onboard / orderNumber * 100.0) / 100.0;
      ReportDTO reportDTO = new ReportDTO(title, requestCollections, orderNumber, orderHrNumber,
          contacting, interview, passed, offer, onboard, lackNumber, ratio);
      listReportDTO.add(reportDTO);
    }
    return listReportDTO;
  }

  /**
   * @description: .
   * @author: nvquy
   * @created_date: May 04, 2018
   * @modifier: User
   * @modifier_date:
   * @param
   * @return
   */
  @Override
  public ArrayList<ReportDTO> getReportHrMember(Long assigneeId) {

    ArrayList<Project> listProject = (ArrayList<Project>) projectRepository.findAll();

    ArrayList<ReportDTO> listReportDTO = new ArrayList<>();

    for (Project project : listProject) {
      ArrayList<Request> requestCollections = new ArrayList<>();
      String title = project.getTitle();
      int orderNumber = 0;
      int orderHrNumber = 0;
      int contacting = 0;
      int interview = 0;
      int passed = 0;
      int offer = 0;
      int onboard = 0;
      int lackNumber = 0;
      double ratio = 0.0;
      for (Request request : project.getRequestCollection()) {
        if (request.getRequestAssignee() == null
            || !checkIfRequestContainsAssignee(request, assigneeId)) {
          continue;
        }
        removeOthersAsignee(request, assigneeId);
        updateCandidateStatusByUser(request.getRequestAssignee(), request.getId());

        orderNumber += request.getNumber();

        request.setCountRequestStatus(countRequestStatus(request.getCandidateCollection(),
            candidateStatusRepository.findAll()));
        Map<String, Integer> map = (Map<String, Integer>) request.getCountRequestStatus();
        int numberOnboardOfRecentRequest = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
          numberOnboardOfRecentRequest = 0;
          switch (entry.getKey()) {
            case Constants.CONTACTING:
              contacting += entry.getValue();
              break;
            case Constants.INTERVIEW:
              interview += entry.getValue();
              break;
            case Constants.PASSED:
              passed += entry.getValue();
              break;
            case Constants.OFFER:
              offer += entry.getValue();
              break;
            case Constants.ONBOARD:
              onboard += entry.getValue();
              numberOnboardOfRecentRequest++;
              break;
            default:
              break;
          }
        }
        request.setLackNumber(request.getNumber() - numberOnboardOfRecentRequest);
        request.setRatio(
            Math.round((double) numberOnboardOfRecentRequest / orderNumber * 100.0) / 100.0);
        requestCollections.add(request);

      }
      lackNumber = orderNumber - onboard;
      ratio = Math.round((double) onboard / orderNumber * 100.0) / 100.0;
      ReportDTO reportDTO = new ReportDTO(title, requestCollections, orderNumber, orderHrNumber,
          contacting, interview, passed, offer, onboard, lackNumber, ratio);
      listReportDTO.add(reportDTO);
    }
    return listReportDTO;
  }

  // quy
  private boolean checkIfRequestContainsAssignee(Request request, Long assigneeId) {
    for (RequestAssignee requestAssignee : request.getRequestAssignee()) {
      if (requestAssignee.getAssignee().getId().equals(assigneeId)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @description: .
   * @author: nvquy
   * @created_date: May 29, 2018
   * @modifier: User
   * @modifier_date:
   * @param
   * @return
   */
  @Override
  public void removeOthersAsignee(Request request, Long assigneeId) {
    Iterator<RequestAssignee> itr = request.getRequestAssignee().iterator();
    while (itr.hasNext()) {
      RequestAssignee requestAssignee = itr.next();
      if (!requestAssignee.getAssignee().getId().equals(assigneeId)) {
        itr.remove();
      }
    }
  }
  /**
   * @description: .
   * @author: nvquy
   * @created_date: Jun 19, 2018
   * @modifier: User
   * @modifier_date:
   * @param
   * @return
   */
  @Override
  public Integer getNumberOfRequestInRecentYear(Integer recentYear) {
    // TODO Auto-generated method stub
    return requestRepository.countNumberOfRequestInRecentYear(recentYear);
  }
  
  @Override
  public List<Request> listRequestByCreator(User creator) {
  	return requestRepository.findByCreatedBy(creator);
  }

  @Override
  public Page<Request> getReportRP(String fromDate, String toDate, Pageable pageable) {
    Specification<Request> spec = new RequestSpecification(fromDate, toDate);
    Page<Request> page = requestRepository.findAll(spec, pageable);
    for (Request request : page.getContent()) {
      request.setCountRequestStatus(countRequestStatus(request.getCandidateCollection(),
          candidateStatusRepository.findAll()));
      request.setRequestAssignee(updateCandidateStatusByUser(request.getRequestAssignee(), request.getId()));
    }
    return page;
  }

}
