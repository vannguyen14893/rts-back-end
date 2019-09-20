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
@Table(name = "notification")
public class Notification implements Serializable {
	
	private static final long serialVersionUID = 2344474339154569238L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "notification_type")
    private String notificationType;
    
    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidateId;
    
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment commentId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    @JoinColumn(name = "interview_id", referencedColumnName = "id")
    @ManyToOne
    private Interview interviewId;
    
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @ManyToOne
    private Request requestId;
    
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @ManyToOne
    private User receiver;
    
    private Boolean status;
}
