package com.cmc.recruitment.utils;

public class QueryConstants {

	public static class Candidate {
		public static final String FIND_ALL_BY_REQUEST_ID = "SELECT c" + " FROM Candidate c"
				+ " WHERE c.requestId.id = :requestId";
		public static final String FIND_ALL_BY_REQUEST_ID_AND_STATUS_ID = "SELECT c" + " FROM Candidate c"
				+ " WHERE c.statusId.id = :statusId AND c.requestId.id = :requestId";
		public static final String FIND_ALL_BY_REQUEST = "SELECT c" + " FROM Candidate c"
				+ " WHERE c.requestId.id = :requestId";
		public static final String FIND_ALL_BY_REQUEST_TO_MAKE_INTERVIEW = "SELECT c FROM Candidate c WHERE c.requestId.id = :requestId AND c.statusId NOT IN ("
				+ "SELECT cs FROM CandidateStatus cs WHERE cs.title IN ('Apply','Contacting','Pending'))";
		public static final String FIND_CANDIDATE_BY_CREATE_USER = "SELECT c FROM Candidate c where c.createdBy.id = :userId AND c.requestId.id = :requestId";
		
		public static final String UPDATE_STATUS_CANDIDATE = "UPDATE Candidate c SET c.statusId.id = :statusId WHERE c.id = :id";
		public static final String UPDATE_CREATED_BY_CANDIDATE = "UPDATE Candidate c SET c.createdBy.id = :asigneeIdNew WHERE c.requestId.id = :requestId AND c.createdBy.id = :asigneeIdOld AND c.statusId.id <> 5";

	}

	public static class CvUrl {
		public static final String GET_CV_URL_BY_CV_ID = "SELECT c FROM CvUrl c WHERE c.cvId = :cvId ";
	}

	public static class Interview {
		public static final String FIND_INTERVIEW_BY_INTERVIEWER = "select i from Interview i inner join i.userCollection iu where iu.id = :userId";
		public static final String FIND_INTERVIEW_BY_CANDIDATE = "select i from Interview i inner join i.candidateCollection cc where cc.id = :candidateId";
	}

	public static class RequestAssignee {
		public static final String FIND_BY_REQUEST_ID_AND_ASSIGNEE_ID = "SELECT ra FROM RequestAssignee ra WHERE ra.request.id = :requestId AND ra.assignee.id = :assigneeId";
		public static final String FIND_ALL_REQUEST_ASSIGNEE_OF_REQUEST = "SELECT ra FROM RequestAssignee ra WHERE ra.request.id = :requestId";
		public static final String DELETE_ASSIGNEE_BY_REQUEST = "delete from RequestAssignee ra WHERE ra.request.id = :requestId";
	}

	public static class Request {
		public static final String FIND_ALL_REQUEST = "SELECT R FROM Request R";
		public static final String FIND_REQUEST_OF_DEPARTMENT = "SELECT r FROM Request r WHERE r.createdBy.departmentId.id = :departmentId";
		public static final String FIND_REQUEST_BY_STATUS = "SELECT r FROM Request r WHERE r.requestStatusId.id = :statusId";
		public static final String FIND_BY_LIST_TITLE = "SELECT r FROM Request r WHERE r.requestStatusId.id in :listTitleId";
		public static final String COUNT_NUMBER_OF_REQUEST_IN_RECENT_YEAR = "SELECT count(r.id) FROM Request r WHERE YEAR(r.createdDate) = :recentYear)";
	}

	public static class User {
		public static final String FIND_BY_USERNAME_CASE_INSENSITIVE = "SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:username)";
		public static final String FIND_ALL_USER = "SELECT u FROM User u";
		public static final String FIND_USER_BY_DEPARTMENT = "select U FROM User U WHERE U.departmentId.id = :id";
		public static final String FIND_USER_BY_ROLE = "select u from User u inner join u.roleCollection ur where ur.id = :roleId";
		public static final String FIND_USER_BY_GROUP = "select u from User u inner join u.groupCollection ug where ug.id = :groupId";
		public static final String GET_INTERVIEWERS = "SELECT i FROM User i WHERE size(i.interviewCollection) > 0";
		public static final String GET_USER_IS_CREATOR = "select U FROM User U WHERE U.id IN (select distinct R.createdBy from Request R)";
		public static final String GET_USER_IS_ASSIGNEE = "FROM User u";
		public static final String FIND_USER_BY_DEPARTMENR_ADN_ROLE = "select u from User u inner join u.roleCollection ur where ur.id = :roleId and u.departmentId.id = :departmentId";
	}

	public static class Notification {
		public static final String FIND_BY_RECEIVER = "SELECT n FROM Notification n WHERE n.receiver.id = :userId ORDER by n.id DESC";
	}
}
