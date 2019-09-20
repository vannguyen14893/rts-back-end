package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Comment;
import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.QueryConstants;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
	
	Notification findByCandidateId(Candidate candidateId);
	
	Notification findById(Long notificationId);
	
	Notification findByCommentId(Comment commentId);
	
	@Query(QueryConstants.Notification.FIND_BY_RECEIVER)
	List<Notification> findNotificationByReceiver(@Param(Constants.PARAM.USER_ID_PARAM) Long userId);
}
