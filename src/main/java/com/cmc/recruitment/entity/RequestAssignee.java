package com.cmc.recruitment.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "request_assignee")
public class RequestAssignee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2955904525835081726L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JsonProperty(access = Access.WRITE_ONLY)
	@JoinColumn(name = "request_id")
	private Request request;

	@ManyToOne
	@JoinColumn(name = "assignee_id")
	private User assignee;

	@Column(name = "number_of_candidate")
	private int numberOfCandidate;
	
	@Transient
	private Map<String, Integer> countCandidateStatus;
	
	/**
	 * @param request
	 * @param assignee
	 * @param numberOfCandidate
	 */
	public RequestAssignee(Request request, User assignee, int numberOfCandidate) {
		this.request = request;
		this.assignee = assignee;
		this.numberOfCandidate = numberOfCandidate;
	}

	/**
	 * @param id
	 * @param request
	 * @param assignee
	 * @param numberOfCandidate
	 */
	public RequestAssignee(Long id, Request request, User assignee, int numberOfCandidate) {
		this.id = id;
		this.request = request;
		this.assignee = assignee;
		this.numberOfCandidate = numberOfCandidate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestAssignee other = (RequestAssignee) obj;
		if (assignee == null) {
			if (other.assignee != null)
				return false;
		} else if (!assignee.equals(other.assignee))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		return true;
	}
}
