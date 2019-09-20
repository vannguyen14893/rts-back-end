package com.cmc.recruitment.utils;

public class Constants {

	public static final String APPLICATION_EMAIL = "recruitmentcmcglobal@gmail.com";
	public static final String BASE_URL = "101.99.14.196:8280/#/";
	public static final String PENDING = "Pending";
  public static final String CONTACTING = "Contacting";
  public static final String INTERVIEW = "Interview";
  public static final String PASSED = "Passed";
  public static final String OFFER = "Offer";
  public static final String ONBOARD = "Onboard";
  public static final String CLOSED = "Closed";

  public static final String ROLE_HR_MEMBER = "ROLE_HR_MEMBER";
  public static final String REQUEST_CODE = "ORD";
  public static final int FORMAT_NUMBER_OF_REQUEST_CODE = 3;
  public static final int CHARACTER_ZERO = 0;
  public static final String INTERVIEW_STATUS_NEW = "New";
	public static class RESPONSE {

		public static final int SUCCESS_STATUS = 200;
		public static final String SUCCESS_CODE = "SUCCESS";
		public static final String SUCCESS_MESSAGE = "Successful implementation!";
		// Quy
		public static final String SUBJECT_INTERVIEW = "Interview Schedule";
		public static final String SUBJECT_INTERVIEW_CANCEL = "Cancel Interview Schedule";
		public static final String EMAIL_CANCEL_DETAIL = "The interview has been cancelled";
		public static final String EMAIL_DETAIL = "Here is your interview schedule:";
		public static final String TIME_IS_IN_THE_PAST = "Start time is in the past";
		// VDHoan
		public static final String ERROR_UNAUTHORIZE = "You have not permissions";
		public static final String ERROR_SERVER = "Server's not working";
		public static final String NO_CONTENT = "No content";
		// VDHoan
		public static final String WRONG_INPUT = "Wrong input";
		public static final String NOT_RETRIVE_DATA = "Not retrive data";

		public static final int EXCEPTION_STATUS = 0;
		public static final String EXCEPTION_CODE = "EXCEPTION";
		public static final String USER_IS_NULL = "User does not exist";
		public static final String EXCEPTION_MESSAGE = "Implementation fail";
		public static final String INCORRECT_OLD_PASSWORD = "Password incorrect";
		public static final String INVALID_PASS_LENGTH = "Password length incorrect";
		public static final String SAME_OLD_PASS = "Same with old password";
		public static final String ERROR_DATA = "Error Data!";

		// NHPhong
		public static final String SERVER_ERROR = "Internal Server Error";
		public static final String ERROR_NOT_FIND = "Email not exist!";
		public static final String ERROR_NOT_SEND_EMAIL = "Not send email!";
		public static final String ERROR_CAN_NOT_SAVE = "Can not save!";
		public static final String NO_INPUT = "No input!";
		public static final String NOT_FOUND = "No found!";
		public static final String REQUEST_NOT_EXISTS = "request not exists";

		// PXHoang
		public static final int INTERNAL_SERVER_ERROR = 500;
		public static final String DUPLICATE_CODE = "FAIL";
		public static final String DUPLICATE_MESSAGE = "Error Duplicate: User already exists";
		public static final int EXITS_STATUS = 500;
		public static final int NOT_FOUND_CODE = 500;
		public static final String EXITS_CODE = "FAIL";
		public static final String EXITS_MESSAGE = "Error duplicate record in database";
		public static final int NOT_SAVE_STATUS = 500;
		public static final String NOT_SAVE_CODE = "FAIL";
		public static final String NOT_SAVE_MESSAGE = "Error not save to database";
		public static final String NOT_EXIST = "Not exist";

		// LCNGUYEN
		public static final String USER_EMAIL_EXISTED = "Email was existed";
		public static final String USER_USERNAME_EXISTED = "Username was existed";
		public static final String USER_EMAIL_INVALID = "Email invalid";
		public static final String USER_USERNAME_INVALID = "Username invalid";
		public static final String INTERNAL_ERROR_SERVICE = "Internal error service";
		public static final String USER_EMAIL_NOT_EXISTED = "Email was not existed";
		public static final String USER_USERNAME_NOT_EXISTED = "Email was not existed";
		public static final String USER_UPDATE_SUCCESS = "Update success";
		public static final String SKILL_WAS_EXISTED = "Skill was existed";
		public static final String SKILL_TITLE_NOT_BLANK = "Skill title must not blank";
		public static final String NUMBER_OF_CANDIDATE_BIG = "Number of candidate is more than request's number";
	}

	public static class CHANGE_PASS_STATUS {
		public static final int INCORRECT_OLD_PASSWORD = 1;
		public static final int INVALID_PASS_LENGTH = 2;
		public static final int SAME_OLD_PASS = 3;
		public static final int SUCCESS = 4;
	}

	// NHPhong
	public static class User {
		// NHPhong for reset password.
		public static final int VALID = 0;
		public static final int NOT_FIND_EMAIL = 1;
		public static final int NOT_SAVE = 3;
		public static final int NOT_SEND_EMAIL = 2;

		public static final String SUBJECT_RESET_PASSWORD = "Reset password";
		public static final String SUBJECT_SEND_EMAIL_AFTER_CREATE_USER = "We have created your account. Username and Password: ";
		public static final String SUBJECT_SEND_EMAIL_AFTER_UPDATE_USER = "Your account has been changed. Username: ";
		public static final String TEXT_MESSAGE_RESET = "Your password have been reset: ";
		public static final String TEXT_MESSAGE_CHANGE_ACTIVE = "Your account have been change active is: ";

	}

	public static class Pagination {
		public static final int NUM_RECORD_PER_PAGE = 20;
	}

	// NHPhong Upload
	public static class Upload {
		public static final String FODER_UPLOADED_CV = "//public//";
		public static final String FODER_UPLOADED_PROFILE_IMAGE = "//static//";

		public static final String ERROR_SIZE_CONFIG = "File size too big";
		public static final String NOT_SAVE = "Can not save file";
		public static final String FILE_EXITS = " Existed";
		public static final String UPLOAD_SUCCESS = "";
		public static final String UPLOAD_FALSE = " Upload faild";

		public static final String ERROR_SIZE_DOC = " Size is more than 5MB.";
		public static final String IS_NOT_DOC = " Invalid document's format.";
		public static final int SIZE_DOC = 5 * 1024 * 1024;
		public static final String IS_NOT_IMAGE = " Invalid image's format.";
		public static final String ERROR_SIZE_IMG = " Size is more than 3MB.";
		public static final int SIZE_IMAGE = 3 * 1024 * 1024;

		public static final String PNG = "image/png";
		public static final String JPEG = "image/jpeg";
		public static final String JPG = "image/jpg";
		public static final String PDF = "application/pdf";
		public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		public static final String DOC = "application/msword";
		public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		public static final String XLS = "application/vnd.ms-excel";
	}

	// VDHoan
	public static class PARAM {
	  
		public static final String EXPERIENCE_ID_PARAM = "experienceId";
		public static final String REQUEST_ID_PARAM = "requestId";
		public static final String STATUS_ID_PARAM = "statusId";
		public static final String FIRST_NAME_PARAM = "firstName";
		public static final String ID_PARAM = "id";
		public static final String TITLE_PARAM = "title";
		public static final String HRMEMBER_ID_PARAM = "hrmemberId";
		public static final String CANDIDATE_ID_PARAM = "candidateId";
		public static final String INTERVIEW_ID_PARAM = "interviewId";
		public static final String CV_ID_PARAM = "cvId";
		public static final String USER_ID_PARAM = "userId";
		public static final String ASSIGNEE_ID_PARAM = "assigneeId";
		public static final String DEPARTMENT_ID_PARAM = "departmentId";
		public static final String LIST_TITLE_ID_PARAM= "listTitleId";
		public static final String RECENT_YEAR_PARAM= "recentYear";
		public static final String ROLE_ID_PARAM = "roleId";
		public static final String USERNAME_PARAM = "username";
		public static final String YEAR = "year";
		public static final String ACTION = "action";
		
	}

	public static class CHECK_CONTACTS {
		public static final String FACEBOOK_EXIST = "Facebook exists.";
		public static final String EMAIL_EXIST = "Email exists.";
		public static final String LINKEDIN_EXIST = "Linkedin exists.";
		public static final String SKYPE_EXIST = "Skype exists.";

		public static final String EMAIL_PARAM = "email";
		public static final String FACEBOOK_PARAM = "facebook";
		public static final String LINKEDIN_PARAM = "linkedin";
		public static final String SKYPE_PARAM = "skype";
	}

	public static class REPLY_EMAIL {

		public static final String REQUEST_WAIT_APPROVE_SUBJECT = "Wait Approved request.";
		public static final String REQUEST_WAIT_APPROVE_CONTENT1 = "Hi! <br>"
				+ "This is an auto-generated email from RTS Application. Please do not reply.<br>"
				+ "A request has been created. You can approve or reject this request.<br>" + "You can <a href=\"";
		public static final String REQUEST_WAIT_APPROVE_CONTENT2 = "\">Click here</a> to go to the page. If you can’t, copy the link below and paste into your browser.<br>";
		public static final String REQUEST_WAIT_APPROVE_CONTENT3 = "<br>All the best!<br>RTS.";

		public static final String REQUEST_APPROVE_SUBJECT = "Approved request.";
		public static final String REQUEST_APPROVE_CONTENT1 = "Hi! <br>"
				+ "This is an auto-generated email from RTS Application. Please do not reply.<br>"
				+ "We would like to inform that your request has been approved.<br>"

				+ "You can <a href=\"";
		public static final String REQUEST_APPROVE_CONTENT2 = "\">Click here<a/> to go to the page. If you can’t, copy the link below and paste into your browser.<br>";
		public static final String REQUEST_APPROVE_CONTENT3 = "<br>All the best!<br>RTS.";

		public static final String REQUEST_REJECT_SUBJECT = "Reject request.";
		public static final String REQUEST_REJECT_CONTENT1 = "Hi! <br>"
				+ "This is an auto-generated email from RTS Application. Please do not reply.<br>"
				+ "We would like to inform that your request has been rejected.<br>"

				+ "You can <a href=\"";
		public static final String REQUEST_REJECT_CONTENT2 = "\">Click here<a/> to go to the page. If you can’t, copy the link below and paste into your browser.<br>";
		public static final String REQUEST_REJECT_CONTENT3 = "<br>All the best,<br>RTS team";

		public static final String REQUEST_ASSIGN_SUBJECT = "Assign request";
		public static final String REQUEST_ASSIGN_CONTENT1 = "Hi! <br>"
				+ "You were assigned a request from Hr Manager!<br>" + "You can <a href=\"";
		public static final String REQUEST_ASSIGN_CONTENT2 = "\">Click here<a/> to go to the page. If you can’t, copy the link below and paste into your browser.<br>";
		public static final String REQUEST_ASSIGN_CONTENT3 = "<br>All the best!<br>RTS.";
		public static final String REQUEST_ASSIGN_DEFAULT_URL = "requests/list";

		public static final String REQUEST_CLOSE = "<br/>This Request has been closed<br/>RTS.";
		public static final String REQUEST_CLOSE_SUBJECT = "Close Request";

		public static final String REQUEST_CLOSE_COMMENT = "Request ID: ";
		
		public static final String REQUEST_URL_DETAIL = "http://localhost:8082/api/requests/";
		
		public static final String INTERVIEWER_SUBJECT = "Add interviewer";
		public static final String INTERVIEW_SUBJECT = "Interviewer";
		public static final String INTERVIEWER_CONTENT1 = "Hi! <br>"
				+ "You were invited to the following interview<br>Time: ";
		public static final String INTERVIEWER_CONTENT2 = "<br>Location: ";
		public static final String INTERVIEWER_CONTENT3 = "<br>Detail: ";
		public static final String INTERVIEWER_CONTENT4 = "<br>All the best!<br>RTS.";

	}

	public static class API_URL {
		public static final String FIND_ALL = "/find-all";
		public static final String FIND_ONE = "/find-all";
		public static final String CREATE_OR_UPDATE = "/create-or-update";
		public static final String FIND_BY_TITLE = "/find-by-title";
	}

	public static class ACTIONS {
		public static final String CREATE = "create";
		public static final String UPDATE = "update";
		public static final String APPROVED = "approved";
		public static final String REJECTED = "rejected";
		public static final String SUBMIT = "submit";
		public static final String COMMENT = "comment";
		public static final String CHANGE_STATUS = "change status";
		public static final String ASSIGN = "assign";
		public static final String ADD_CANDIDATE = "Add candidate to interview";
		public static final String ADD_INTERVIEWER = "Add interviewer";
	  public static final String MAKE_CANDIDATE = "Make candidate to request";
	  public static final String SEND_MEETING_REQUEST = "Meeting request";
	}
	
	public static class TABLE {
		public static final String REQUEST = "request";
		public static final String CANDIDATE = "candidate";
		public static final String INTERVIEW = "interview";
		public static final String CV = "cv";
	}
	
	public static class ROLE {
		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		public static final String ROLE_DU_LEAD = "ROLE_DU_LEAD";
		public static final String ROLE_DU_MEMBER = "ROLE_DU_MEMBER";
		public static final String ROLE_HR_MANAGER = "ROLE_HR_MANAGER";
		public static final String ROLE_HR_MEMBER = "ROLE_HR_MEMBER";
		public static final String ROLE_GROUP_LEAD = "ROLE_GROUP_LEAD";
	}

	public static class CANDIDATE_STATUS {
		public static final String APPLY = "Apply";
		public static final String CONTACTING = "Contacting";
		public static final String INTERVIEW = "Interview";
		public static final String OFFER = "Offer";
		public static final String ONBOARD = "Onboard";
		public static final String CLOSED = "Closed";
	}

	public static class RECRUITMENT_TYPE {
		public static final String NEW = "New";
		public static final String INSTEAD = "Replace";
	}

	public static class EXCHANGE_API {
	  public static final String LOGIN_RECRUIT = "/user/login?_format=json";
	  public static final String LOGOUT_RECRUIT = "/user/logout?_format=json";
	  public static final String PUBLISH_RECRUIT_CONTENT = "/recruit_rest_api/recruit_resource?_format=json";
	}
}
