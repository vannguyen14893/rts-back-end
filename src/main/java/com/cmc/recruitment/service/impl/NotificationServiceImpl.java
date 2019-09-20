package com.cmc.recruitment.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Comment;
import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.CandidateRepository;
import com.cmc.recruitment.repository.InterviewRepository;
import com.cmc.recruitment.repository.NotificationRepository;
import com.cmc.recruitment.repository.UserRepository;
import com.cmc.recruitment.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	CandidateRepository candidateRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	InterviewRepository interviewRepository;

	@Autowired
	public SimpMessageSendingOperations messagingTemplate;
	
	private final SimpMessagingTemplate template;

	@Autowired
	NotificationServiceImpl(SimpMessagingTemplate template){
	      this.template = template;
	  }

	/**@description: send message to client
	 * @author duongquy
	 * @param message
	 */
	public void sendMessage(String message) {

		this.template.convertAndSend("/chat",  new SimpleDateFormat("HH:mm:ss").format(new Date())+" - " + message);
	}
	
	@Override
	public Notification findById(Long id) {
		return notificationRepository.findById(id);
	}

	@Override
	public List<Notification> findAll() {
		
		return notificationRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
	}
	
	@Override
	public List<Notification> findByReceiver(long userId) {
		return notificationRepository.findNotificationByReceiver(userId);
	}

	@Override
	public Notification findByCandidateId(Candidate candidateId) {
		
		return notificationRepository.findByCandidateId(candidateId);
	}

	@Override
	public Notification findByCommentId(Comment commentId) {
		return notificationRepository.findByCommentId(commentId);
	}

	/**@description: send notification when candidate's status has change
	 * @author duongquy
	 * @param 
	 */
	@Override
	public void sendNotification(Notification notification, Candidate candidate, User user, Interview interview) {
		Long userRole = new Long(4);
		List<User> hrManagers = userRepository.findUserByRole(userRole);
		for	(User hrmanager: hrManagers) {
			Notification notificationRef = new Notification();
			notificationRef.setRequestId(candidate.getRequestId());
			notificationRef.setCandidateId(candidate);
			notificationRef.setUserId(user);
			notificationRef.setInterviewId(interview);
			notificationRef.setCreateDate(new Date());
			notificationRef.setStatus(true);
			notificationRef.setReceiver(hrmanager);
			notificationRef.setContent(notification.getContent());
			notificationRef.setNotificationType(notification.getNotificationType());
			template.convertAndSend("/chat/"+ hrmanager.getUsername(), notificationRef);
			notificationRepository.save(notificationRef);
		}
	}

	/**@description: send notification when candidate has new comment
	 * @author duongquy
	 * @param 
	 */
	@Override
	public void sendNotification(Notification notification, Long candidateId, Long userId, Long interviewId) {
		Candidate candidate = candidateRepository.findById(candidateId);
		User user = userRepository.findById(userId);
		Long userRole = new Long(4);
		List<User> hrManagers = userRepository.findUserByRole(userRole);
		for (User hrmanager: hrManagers) {
			Notification notificationRef = new Notification();
			if (interviewId != null) {
				Interview interview = interviewRepository.findOne(interviewId);
				notificationRef.setInterviewId(interview);
			}
			notificationRef.setRequestId(candidate.getRequestId());
			notificationRef.setCandidateId(candidate);
			notificationRef.setCreateDate(new Date());
			notificationRef.setStatus(true);
			notificationRef.setContent(notification.getContent());
			notificationRef.setNotificationType(notification.getNotificationType());
			notificationRef.setUserId(user);
			notificationRef.setReceiver(hrmanager);
			template.convertAndSend("/chat/"+hrmanager.getUsername(), notificationRef);
			notificationRepository.save(notificationRef);
		}
	}

	
	/**@description: send notification when user create new interview
	 * @author duongquy
	 * @param interview
	 */
	@Override
	public void sendNotification(Notification notification, User user, Interview interview) {
		List<Candidate> candidates = interview.getCandidateCollection();
		for (Candidate candidate: candidates) {
			for (User receiver: interview.getUserCollection()) {
				Notification notificationRef = new Notification();
				notificationRef.setRequestId(candidate.getRequestId());
				notificationRef.setInterviewId(interview);
				notificationRef.setUserId(user);
				notificationRef.setCandidateId(candidate);
				notificationRef.setCreateDate(new Date());
				notificationRef.setContent(notification.getContent());
				notificationRef.setNotificationType(notification.getNotificationType());
				notificationRef.setReceiver(receiver);
				notificationRef.setStatus(true);
				template.convertAndSend("/chat/"+receiver.getUsername(), notificationRef);
				notificationRepository.save(notificationRef);
			}
		}		
	}

	/**@description: send notification when user create new interview
	 * @author duongquy
	 * @param request
	 */
	@Override
	public void sendNotification(Notification notification, User user, Request request, User receiver) {
		notification.setNotificationType("4");
		notification.setUserId(user);
		notification.setRequestId(request);
		notification.setReceiver(receiver);
		notification.setCreateDate(new Date());
		template.convertAndSend("/chat/"+receiver.getUsername(), notification);
		notificationRepository.save(notification);
	}

	
}
