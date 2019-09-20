package com.cmc.recruitment.utils;

public class ConstantsUrl {

  public static class Candidate {
    public static final String ALL = "/candidates";
    public static final String DETAIL = ALL + "/{candidateId}";
    public static final String SAVE = "/candidates/save";
    public static final String COMMENT = ALL + "/comment/{candidateId}";
    public static final String UPDATE_STATUS = ALL + "/status/{candidateId}";
    public static final String FIND_BY_REQUEST = ALL + "/request/{requestId}";
    public static final String FIND_BY_REQUEST_AND_STATUS = ALL
        + "/request/{requestId}/status/{statusId}";
    public static final String FIND_BY_STATUS = ALL + "/status/{statusId}";
    public static final String FIND_BY_REQUEST_UNLIMIT = ALL + "/request/{requestId}/all";
    public static final String FIND_TO_MAKE_INTERVIEW = "/candidates/{requestId}/interview";
    public static final String MAKE_CANDIDATE = ALL + "/make-candidate";
    public static final String ADD = "/candidates";
    public static final String CHANGE_STATUS = "/candidates/change-status";
    public static final String CHANGE_STATUS_LIST = "/candidates/change-status-list";
    public static final String REJECT = "/candidates/reject";
    public static final String MAKE_CANDIDATE_LIST = "/candidates/make-candidate-list";
    public static final String STATISTIC_CANDIDATE_BY_GROUP = "/candidates/statistic-group";
    public static final String STATISTIC_CANDIDATE_BY_DEPARTMENT = "/candidates/statistic-department";
    public static final String REPORT_AM = "/candidates/report-am";

  }

  public static class CandidateStatus {
    public static final String ALL = "/candidate-status";
    public static final String ALL_UNLIMIT = ALL + "/all";
    public static final String FIND_BY_TITLE = ALL + "/title";
    public static final String SAVE = ALL + "/save";
    public static final String CLOSED ="Closed";
  }

  public static class Comment {
    public static final String ALL = "/comments";
    public static final String ADD = "/comments";
    public static final String BYCV = "/comments/{cvId}";
  }

  public static class CV {
    public static final String ALL = "/cvs";
    public static final String ADD = "/cvs";
    public static final String UPDATE = "/cvs";
    public static final String ALL_UNLIMIT = "/cvs/all";
    public static final String DETAIL = "/cvs/{cvId}";
    public static final String CHECK_SOCIAL = "/cvs/check";
  }

  public static class CvStatus {
    public static final String ALL_UNLIMIT = "/cv-status/all";
    public static final String ALL = "/cv-status";
    public static final String DETAIL = "/cv-status/{cvStatusId}";
    public static final String ADD = "/cv-status";
    public static final String FIND_BY_TITLE = "/cv-status/title";
    public static final String SAVE = "/cv-status/save";
  }

  public static class Department {
    public static final String ALL_UNLIMIT = "/departments/all";
    public static final String ALL = "/departments";
    public static final String FIND_BY_TITLE = "/departments/title";
    public static final String SAVE = "/departments/save";
  }

  public static class Experience {
    public static final String ALL_UNLIMIT = "/experiences/all";
    public static final String ALL = "/experiences";
    public static final String FIND_BY_TITLE = "/experiences/title";
    public static final String SAVE = "/experiences/save";
  }

  public static class ForeignLanguage {
    public static final String ALL = "/foreign-languages";
    public static final String ALL_UNLIMIT = "/foreign-languages/all";
    public static final String FIND_BY_TITLE = "/foreign-languages/title";
    public static final String SAVE = "/foreign-languages/save";

  }

  public static class Interview {
    public static final String FILTER = "/interviews/filter";
    public static final String DETAIL = "/interviews/{interviewId}";
    public static final String ADD = "/interviews";
    public static final String FIND_BY_CANDIDATE = "/interviews/candidate/{candidateId}";
    public static final String ADD_LIST_INTERVIEWER = "/interviews/interviewers";
    public static final String ADD_LIST_CANDIDATE = "/interviews/candidates";
    public static final String MEETING_REQUEST = "/interviews/meeting";
    public static final String COMMENT = "/interviews/comment";
  }

  public static class InterviewStatus {
    public static final String ALL_UNLIMIT = "/interview-status/all";
    public static final String ALL = "/interview-status";
    public static final String SAVE = "/interview-status/save";
  }

  public static class Log {
    public static final String ALL = "/logs";
    public static final String ADD = "/logs";
  }

  public static class Position {
    public static final String ALL_UNLIMIT = "/positions/all";
    public static final String ALL = "/positions";
    public static final String DETAIL = "/positions/{positionId}";
    public static final String ADD = "/positions";
    public static final String FIND_BY_TITLE = "/positions/title";
    public static final String SAVE = "/positions/save";
  }

  public static class Priority {
    public static final String ALL_UNLIMIT = "/priorities/all";
    public static final String ALL = "/priorities";
    public static final String FIND_BY_TITLE = "/priorities/title";
    public static final String SAVE = "/priorities/save";
  }

  public static class Project {
    public static final String ALL_UNLIMIT = "/projects/all";
    public static final String ALL = "/projects";
    public static final String SAVE = "/projects/save";
    public static final String FIND_BY_TITLE = "/projects/title";
  }

  public static class RecruitmentType {
    public static final String ALL_UNLIMIT = "/recruitment-types/all";
    public static final String ALL = "/recruitment-types";
    public static final String DETAIL = "/recruitment-types/{recruitmentTypeId}";
    public static final String ADD = "/recruitment-types";
    public static final String FIND_BY_TITLE = "/recruitment-types/title";
    public static final String SAVE = "/recruitment-types/save";
  }

  public static class RequestAssignee {
    public static final String CHANGE_TARGET = "/request-assignee/change-target";
  }

  public static class Request {
    public static final String FILTER = "/requests/filter";
    public static final String ADD = "/requests";
    public static final String DETAIL = "/requests/{requestId}";
    public static final String CLONE = "/requests/clone";
    public static final String ALL = "/requests";
    public static final String APPROVE = "/requests/approve";
    public static final String REJECT = "/requests/reject";
    public static final String CLOSE = "/requests/close";
    public static final String FIND_BY_STATUS = "/requests/status/{statusId}";
    public static final String SUBMIT = "/requests/submit";
    public static final String ASSIGN = "/requests/assign";
    public static final String ALL_BY_STATUS_REQUEST = "/requests/status";
    public static final String ASSIGNEE = "/requests/assignee";
    public static final String BELONG_TO_ASSINGEE = "/requests/assigned";
    public static final String REPORT_GROUP_LEADER = "/requests/report-rrclead";
    public static final String REPORT_HR_MEMBER = "/requests/report-hrmember";
    public static final String EDIT_ASSIGN = "/requests/edit-assignee";
    public static final String REPORT_RP = "/requests/report-rp";
    public static final String DOWNLOAD = "/download.xls";
    public static final String DOWNLOAD_AM = "/download-am-report";
    public static final String DOWNLOAD_DU = "/download-du-report";
    public static final String GET_TOKEN_GLOBAL = "/requests/get-token";
    public static final String PUBLISH_REQUEST = "/requests/publish";
    
  }

  public static class RequestStatus {
    public static final String ALL_UNLIMIT = "/request-status/all";
    public static final String ALL = "/request-status";
    public static final String DETAIL = "/request-status/{requestStatusId}";
    public static final String ADD = "/request-status";
    public static final String FIND_BY_TITLE = "/request-status/title";
    public static final String SAVE = "/request-status/save";
  }

  public static class Role {
    public static final String ALL = "/roles";
  }

  public static class Upload {
    public static final String CHECK = "/upload/check";
    public static final String DOC = "/upload/doc";
    public static final String IMAGE = "/upload/image";
    public static final String DELETE = "/upload/delete";
  }

  public static class Skill {
    public static final String ALL_UNLIMIT = "/skill/all";
    public static final String ALL = "/skills";
    public static final String ADD = "/skills";
    public static final String UPDATE = "/skills";
    public static final String DETAIL = "/skills/{skillId}";
    public static final String SAVE = "/skills/save";
    public static final String FIND_BY_TITLE = "/skills/title";
  }
  
  public static class Certification {
	    public static final String ALL_UNLIMIT = "/certifications/all";
	    public static final String ALL = "/certifications";
	    public static final String ADD = "/certifications";
	    public static final String UPDATE = "/certifications";
	    public static final String DETAIL = "/certifications/{certificationId}";
	    public static final String SAVE = "/certifications/save";
	    public static final String FIND_BY_TITLE = "/certifications/title";
	  }
  public static class Group {
    public static final String ALL_UNLIMIT = "/groups/all";
    
  }

}
