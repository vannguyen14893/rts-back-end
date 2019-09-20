package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "log")
public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710841404788747440L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@JoinColumn(name = "actor", referencedColumnName = "id")
	@ManyToOne
	private User actor;

	@Column(name = "action")
	private String action;

	@Column(name = "log_time")
	private Date log_time;

	@Column(name = "table_name")
	private String tableName;

	@Column(name = "content")
	private String content;

	@Column(name = "assignee_id")
	private Long assigneeId;

	@Column(name = "interviewer_id")
	private Long interviewerId;

	@Column(name = "request_id")
	private Long requestId;
	
	@Column(name = "candidate_id")
	private Long candidateId;
	
	@Column(name = "interview_id")
	private Long interviewId;
	
	@Column(name = "cv_id")
	private Long cvId;

	public Log(Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param actor
	 * @param action
	 * @param log_time
	 * @param tableName
	 * @param content
	 * @param itemId
	 * @param requestId
	 * @param candidateId
	 * @param cvId
	 */
	public Log(Long id, User actor, String action, Date log_time, String tableName, String content, Long assigneeId,
			Long requestId, Long candidateId, Long interviewId, Long cvId) {

		super();
		this.id = id;
		this.actor = actor;
		this.action = action;
		this.log_time = log_time;
		this.tableName = tableName;
		this.content = content;
		this.assigneeId = assigneeId;
		this.requestId = requestId;
		this.candidateId = candidateId;
		this.cvId = cvId;
	}

	public Long getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Long interviewId) {
		this.interviewId = interviewId;
	}

	/**
	 * @param actor
	 * @param action
	 * @param log_time
	 * @param tableName
	 * @param content
	 * @param assigneeId
	 * @param requestId
	 * @param candidateId
	 * @param interviewId
	 * @param cvId
	 */
	public Log(User actor, String action, Date log_time, String tableName, String content, Long assigneeId,
			Long requestId, Long candidateId, Long interviewId, Long cvId) {
		super();
		this.actor = actor;
		this.action = action;
		this.log_time = log_time;
		this.tableName = tableName;
		this.content = content;
		this.assigneeId = assigneeId;
		this.requestId = requestId;
		this.candidateId = candidateId;
		this.interviewId = interviewId;
		this.cvId = cvId;
	}
	
	/**
	 * @param actor
	 * @param action
	 * @param log_time
	 * @param tableName
	 * @param content
	 * @param assigneeId
	 * @param interviewerId
	 * @param requestId
	 * @param candidateId
	 * @param interviewId
	 * @param cvId
	 */
	public Log(User actor, String action, Date log_time, String tableName, String content, Long assigneeId,
			Long interviewerId, Long requestId, Long candidateId, Long interviewId, Long cvId) {
		super();
		this.actor = actor;
		this.action = action;
		this.log_time = log_time;
		this.tableName = tableName;
		this.content = content;
		this.assigneeId = assigneeId;
		this.interviewerId = interviewerId;
		this.requestId = requestId;
		this.candidateId = candidateId;
		this.interviewId = interviewId;
		this.cvId = cvId;
	}

}
