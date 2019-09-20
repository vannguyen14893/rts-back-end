package com.cmc.recruitment.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.CandidateStatus;
import com.cmc.recruitment.entity.Cv;
import com.cmc.recruitment.entity.Department;
import com.cmc.recruitment.entity.Group;
import com.cmc.recruitment.entity.Log;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.CandidateRepository;
import com.cmc.recruitment.repository.CandidateStatusRepository;
import com.cmc.recruitment.repository.CvRepository;
import com.cmc.recruitment.repository.RequestRepository;
import com.cmc.recruitment.service.CandidateService;
import com.cmc.recruitment.service.CandidateStatusService;
import com.cmc.recruitment.service.DepartmentService;
import com.cmc.recruitment.service.GroupService;
import com.cmc.recruitment.service.LogService;
import com.cmc.recruitment.service.RequestService;
import com.cmc.recruitment.service.RoleService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.specification.CandidateSpecification;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ReportByGroup;
import com.cmc.recruitment.utils.StatisticMonth;

@Service
public class CandidateServiceImpl implements CandidateService {

  @Autowired
  CandidateRepository candidateRepository;
  @Autowired
  private CandidateStatusRepository statusRepository;
  @Autowired
  private RequestRepository requestRepository;
  @Autowired
  private CvRepository cvRepository;
  @Autowired
  CandidateStatusRepository candidateStatusRepository;
  @Autowired
  UserService userService;
  @Autowired
  LogService logService;
  @Autowired
  RequestService requestService;
  @Autowired
  CandidateStatusService candidateStatusService;
  @Autowired
  DepartmentService departmentService;
  @Autowired
  RoleService roleService;
  @Autowired
  GroupService groupService;
  
  @Override
  public Candidate findOne(long id) {
    return candidateRepository.findOne(id);
  }

  // @Override
  // public List<Candidate> findAll() {
  // return candidateRepository.findAll();
  // }

  /**
   * @description: .
   * @author: PXHoang
   * @created_date: Feb 8, 2018
   * @modifier: User
   * @modifier_date: Feb 8, 2018
   * @param candidate
   */

  @Override
  public Candidate saveCandidate(Candidate candidate) {
    candidate.setRequestId(requestRepository.findById(candidate.getRequestId().getId()));
    candidate.setStatusId(statusRepository.findById(candidate.getStatusId().getId()));
    candidate.setCvId(cvRepository.findById(candidate.getCvId().getId()));
    return candidateRepository.save(candidate);
  }

  /**
   * @description: kiem tra candidate da ton tai trong database chua.
   * @author: PXHoang
   * @created_date: Feb 8, 2018
   * @modifier: User
   * @modifier_date: Feb 8, 2018
   * @param candidate
   * @return
   */

  @Override
  public boolean isCandidateExist(Candidate candidate) {
    if (candidateRepository.findById(candidate.getId()) != null)
      return true;
    return false;
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Feb 23, 2018
   * @modifier: User
   * @modifier_date: Feb 23, 2018
   * @param search
   * @param pageable
   * @return
   */
  // @Override
  // public Page<Candidate> findAll(Long experienceId, Long requestId, Long
  // statusId, String fullName,
  // Pageable pageable) throws Exception {
  // Specification<Candidate> spec = new CandidateSpecification(experienceId,
  // requestId, statusId,
  // fullName);
  // return candidateRepository.findAll(spec, pageable);
  // }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Feb 28, 2018
   * @modifier: User
   * @modifier_date: Feb 28, 2018
   * @param requestId
   * @param pageable
   * @return
   */
  @Override
  public Page<Candidate> findCandidatesByRequestId(Long requestId, Pageable pageable)
      throws Exception {
    return candidateRepository.findAllCandidateByRequestId(requestId, pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Feb 28, 2018
   * @modifier: User
   * @modifier_date: Feb 28, 2018
   * @param statusId
   * @param pageable
   * @return
   */
  @Override
  public Page<Candidate> findCandidatesByRequestIdAndStatusId(Long requestId, Long statusId,
      Pageable pageable) throws Exception {
    return candidateRepository.findAllCandidateByRequestIdAndStatusId(requestId, statusId,
        pageable);
  }

  @Override
  public List<Candidate> findByRequestId(Long requestId) {
    return candidateRepository.findAllCandidateByRequest(requestId);
  }

  /**
   * @description: .
   * @author: nvquy1
   * @created_date: Mar 14, 2018
   * @modifier: User
   * @modifier_date: Mar 14, 2018
   * @param statusId
   * @param pageable
   * @return
   */
  @Override
  public List<Candidate> findByRequestIdToMakeInterview(Long requestId) {
    return candidateRepository.findAllCandidateByRequestToMakeInterview(requestId);
  }

  // @Override
  // public Page<Candidate> findAll(Long experienceId, Long requestId, Long
  // statusId, String fullName, Pageable pageable)
  // throws Exception {
  // // TODO Auto-generated method stub
  // return null;
  // }

  @Override
  public Page<Candidate> filterCandidate(Long experienceId, Long requestId, Long statusId,
      String fullName, Long hrmemberId, Pageable pageable) {
    Specification<Candidate> spec = new CandidateSpecification(experienceId, requestId, statusId,
        fullName, hrmemberId);
    return candidateRepository.findAll(spec, pageable);
  }
  @Override
  public Page<Candidate> getReportAM(Long hrmemberId, String fromDate, String toDate, Long candidateStatusId, Pageable pageable) {
    Specification<Candidate> spec = new CandidateSpecification(hrmemberId, fromDate, toDate, candidateStatusId);
    return candidateRepository.findAll(spec, pageable);
  }

  @Override
  public List<Candidate> changeStatus(List<Candidate> candidates, Long statusId) {
    CandidateStatus cs = candidateStatusRepository.findOne(statusId);
    if (!candidates.isEmpty()) {
      for (Candidate candidate : candidates) {
        candidate.setStatusId(cs);
      }
    }
    return candidates;
  }

  /**
   * @description: .
   * @author: nvquy1
   * @created_date: Mar 14, 2018
   * @modifier: User
   * @modifier_date: Mar 14, 2018
   * @param statusId
   * @param pageable
   * @return
   */
  @Override
  public Candidate changeStatusId(Long id, Long statusId) {
    return candidateRepository.changeCandidateStatus(statusId, id);
  }

  /**
   * NHPhong: use for create new Object candidate form requestId and CVid.
   * 
   * @param requestId
   * @param cvId
   * @return if Cv not exists: return null, else return Object candidate
   * 
   */
  private Candidate setUpCandidateObject(Long requestId, Long cvId) {
    Candidate candidate = new Candidate();
    Cv cv = cvRepository.findById(cvId);
    Request request = requestRepository.findById(requestId);
    if (cv == null)
      return null;
    candidate.setRequestId(request);
    candidate.setCvId(cv);
    User user = userService.getUserInfoByUserName().get(0);
    if (null != user)
      candidate.setCreatedBy(user);
    /**
     * If title not exists in table Candidate_Status => Exception
     */
    candidate.setStatusId(statusRepository.findByTitle("Apply"));
    return candidate;
  }

  /**
   * NHPhong
   * 
   * @param requestId
   * @param cvId
   * @return if save success do nothing. if save false add message not save
   */
  private String saveCandidate(Long requestId, Long cvId) {
    StringBuilder message = new StringBuilder();
    Candidate candidate = setUpCandidateObject(requestId, cvId);
    if (candidate == null) {
      message.append("CV_id = " + cvId + " not exists in system");
    } else {
      if (saveCandidate(candidate) == null)
        message.append("Cv of " + cvRepository.findById(cvId).getFullName() + " not save");
      else {
        // write to table log.
        User actor = userService.getUserInfoByUserName().get(0);
        Log log = new Log();
        log.setAction(Constants.ACTIONS.MAKE_CANDIDATE);
        log.setActor(actor);
        log.setLog_time(new Date());
        log.setRequestId(requestId);
        log.setCvId(cvId);
        log.setCandidateId(candidate.getId());
        log.setTableName(Constants.TABLE.CANDIDATE);
        logService.save(log);
      }
    }
    return message.toString();
  }

  @Override
  public List<String> makeCandidate(Long requestId, Long[] arrayCvIds) {
    List<String> listMessage = new LinkedList<>();
    // check requestId exists in table Candidate. if listCandiate null(chưa make
    // bao gio)-> save list
    if (candidateRepository.findAllCandidateByRequest(requestId).size() <= 0) {
      for (Long cvId : arrayCvIds) {
        String message = saveCandidate(requestId, cvId);
        if (message != null && !message.isEmpty()) {
          listMessage.add(message);
        }
      }
    } else {
      for (Long cvId : arrayCvIds) {
        // loop arrayCvIds, check Candidate exists = null -> save, else add
        // message to list
        if (candidateRepository.findCandidateByRequestAndCvId(requestId, cvId) == null) {
          String message = saveCandidate(requestId, cvId);
          if (message != null && !message.isEmpty()) {
            listMessage.add(message);
          }
        } else {
          listMessage
              .add("CV of: " + cvRepository.findById(cvId).getFullName() + " has made candidate.");
        }
      }
    }
    return listMessage;
  }

  @Override
  public List<Candidate> findByCvId(Cv cvId) {
    return candidateRepository.findByCvId(cvId);
  }

  public void changeCreatedBy(Long requestId, Long asigneeIdOld, Long asigneeIdNew) {
    candidateRepository.changeCreateByCandidate(requestId, asigneeIdOld, asigneeIdNew);
  }
  
  @Override
  public Candidate updateCandidate(Candidate candidate) {
    return candidateRepository.save(candidate);
  }

	@Override
	public List<Candidate> findAll(Long experienceId, Long requestId, Long statusId, String fullName, Long hrmemberId, String fromDate, String toDate) {
		List<Candidate> list = candidateRepository.findAll(new CandidateSpecification(experienceId, requestId, statusId, fullName, hrmemberId, fromDate, toDate));
		return list;
	}

  
  private ReportByGroup statisticCandidateByDepartment(Long departmentId, String fromDate,
      String toDate) {
    CandidateStatus onboard = candidateStatusService.findByTitle(Constants.CANDIDATE_STATUS.ONBOARD);
    Department department = departmentService.findOne(departmentId);
    Role role = roleService.findByTitle(Constants.ROLE.ROLE_DU_LEAD);
    // get ra tất cả DU Lead của department đó (Du Lead là người tạo request)
    List<User> creaters = userService.getUserByRoleAndDepartment(role.getId(), departmentId);
    Group group = creaters.get(0).getGroupCollection().iterator().next();
    ReportByGroup reportByGroup = new ReportByGroup();
    // Khai báo danh sách thống kê theo tháng (12 tháng)
    List<StatisticMonth> statisticMonths = new ArrayList<StatisticMonth>();
    for (int i = 0; i < 12; i++) {
      StatisticMonth statisticMonth = new StatisticMonth(i, 0, 0);
      statisticMonths.add(statisticMonth);
    }
    // Duyệt từng Du Lead một.
    for (User creater : creaters) {
      // tất cả request mà DU Lead đấy tạo.
      List<Request> requests = requestService.listRequestByCreator(creater);
      for (Request request : requests) {
        List<Candidate> candidates = this.findAll(null, request.getId(), onboard.getId(), null, null, fromDate, toDate);
        for (Candidate candidate : candidates) {
            // DateFormatMonth
            SimpleDateFormat dfm = new SimpleDateFormat("MM");
            String stringMonth = dfm.format(candidate.getOnboardDate());
            Long month = Long.parseLong(stringMonth);
            long index = month>3?month-4 : month+8;
            if (Constants.RECRUITMENT_TYPE.NEW.equals(request.getRecruitmentTypeId().getTitle())) {
              // Tăng số lượng candidate New lên 1
              statisticMonths.get((int) index)
                  .setNumberOfNew(statisticMonths.get((int) index).getNumberOfNew() + 1);
            }
            if (Constants.RECRUITMENT_TYPE.INSTEAD
                .equals(request.getRecruitmentTypeId().getTitle())) {
              // Tăng số lượng candidate New lên 1
              statisticMonths.get((int) index)
                  .setNumberOfTT(statisticMonths.get((int) index).getNumberOfTT() + 1);
            }
        }
      }
    }
    reportByGroup.setGroup(group.getTitle());
    reportByGroup.setDepartment(department.getTitle());
    reportByGroup.setStatisticMonth(statisticMonths);
    reportByGroup.setTotal();
    return reportByGroup;
  }

  @Override
  public List<ReportByGroup> statisticCandidateByGroup(Long year) {
    System.out.println("Year="+year);
    Long toYear = year+1;
    String fromDate = "01/04/" + year;
    String toDate = "31/03/" + toYear;
    List<Group> groups = groupService.findAll();
    List<ReportByGroup> reportByGroups = new ArrayList<ReportByGroup>();
    // Tổng tuyển mới.
    ReportByGroup totalNew = new ReportByGroup();
    totalNew.setGroup("Total New");
    List<StatisticMonth> statisticMonthsNew = new ArrayList<StatisticMonth>();
    for (int i = 0; i < 12; i++) {
        StatisticMonth statisticMonth = new StatisticMonth(i, 0, 0);
        statisticMonthsNew.add(statisticMonth);
    }
    totalNew.setStatisticMonth(statisticMonthsNew);
    // Tổng tuyển thay thế.
    ReportByGroup totalTT = new ReportByGroup();
    totalTT.setGroup("Total Instead");
    List<StatisticMonth> statisticMonthsTT = new ArrayList<StatisticMonth>();
    for (int i = 0; i < 12; i++) {
        StatisticMonth statisticMonth = new StatisticMonth(i, 0, 0);
        statisticMonthsTT.add(statisticMonth);
    }
    totalTT.setStatisticMonth(statisticMonthsNew);
    // Duyệt từng Group một.
    for (Group item : groups) {
      ReportByGroup groupTitle = new ReportByGroup();
      groupTitle.setGroup(item.getTitle());
      List<StatisticMonth> statisticMonths = new ArrayList<StatisticMonth>();
      for (int i = 0; i < 12; i++) {
        StatisticMonth statisticMonth = new StatisticMonth(i, 0, 0);
        statisticMonths.add(statisticMonth);
      }
      groupTitle.setStatisticMonth(statisticMonths);
      reportByGroups.add(groupTitle);
      // Lấy tất cả các user của group ấy.
      Set<User> users = item.getUserCollection();
      Set<Department> departments = new TreeSet<Department>();
      // Duyệt tất cả các User để lấy được hết các Department.
      for (User user : users) {
        if (user.getDepartmentId() != null) {
          departments.add(user.getDepartmentId());
        } else {
          continue;
        }
      }
      for (Department depart : departments) {
        System.out.println("Department:"+depart.getTitle());
        ReportByGroup reportByGroup = statisticCandidateByDepartment(depart.getId(), fromDate, toDate);
        // Cộng các số lượng vào ReportByGroup Title
        List<StatisticMonth> listStatisticMonth = reportByGroup.getStatisticMonth();
        for (int i = 0; i < listStatisticMonth.size(); i++) {
          statisticMonths.get(i).setNumberOfNew(statisticMonths.get(i).getNumberOfNew()
              + listStatisticMonth.get(i).getNumberOfNew());
          statisticMonthsNew.get(i).setNumberOfNew(statisticMonthsNew.get(i).getNumberOfNew()
                  + listStatisticMonth.get(i).getNumberOfNew());
          
          statisticMonths.get(i).setNumberOfTT(
              statisticMonths.get(i).getNumberOfTT() + listStatisticMonth.get(i).getNumberOfTT());
          statisticMonthsTT.get(i).setNumberOfTT(
                  statisticMonthsTT.get(i).getNumberOfTT() + listStatisticMonth.get(i).getNumberOfTT());
        }
        groupTitle.setStatisticMonth(statisticMonths);
        reportByGroups.add(reportByGroup);
        totalNew.setStatisticMonth(statisticMonthsNew);
        totalTT.setStatisticMonth(statisticMonthsTT);
      }
      groupTitle.setTotal();
      totalNew.setTotal();
      totalTT.setTotal();
    }
    reportByGroups.add(totalNew);
    reportByGroups.add(totalTT);
    
    return reportByGroups;
  }

}
