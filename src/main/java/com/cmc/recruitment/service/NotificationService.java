package com.cmc.recruitment.service;

import java.util.List;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Comment;
import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.User;

public interface NotificationService {
	
	Notification findById(Long id);
	
	List<Notification> findAll();
	
	Notification findByCandidateId(Candidate candidateId);
	
	Notification findByCommentId(Comment commentId);
	
	List<Notification> findByReceiver(long userId);

	void sendMessage(String message);
		
	void sendNotification(Notification notification, Candidate candidate, User user, Interview interview);
	
	void sendNotification(Notification notification, Long candidateId, Long userId, Long interviewId);

	void sendNotification(Notification notification, User user, Interview interview);
	
	void sendNotification(Notification notification,User user, Request request, User receiver);

}
