package com.cmc.recruitment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Comment;
import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.CommentRepository;
import com.cmc.recruitment.service.CandidateService;
import com.cmc.recruitment.service.CommentService;
import com.cmc.recruitment.service.InterviewService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.specification.CommentSpecification;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepository commentResponsitory;
  @Autowired
  private CandidateService candidateService;
  @Autowired
  private UserService userService;
  @Autowired
  private InterviewService interviewService;

  @Override
  public Comment save(Comment comment) {
    return commentResponsitory.save(comment);
  }

  @Override
  public Comment addComment(Long candidateId, Long userId, Long interviewId, String detailComment) {
    Candidate candidate = candidateService.findOne(candidateId);
    User user = userService.findOne(userId);
    Interview interview = null;
    Comment comment = new Comment();
    if (interviewId != null) {
      interview = interviewService.findOne(interviewId);
      comment.setInterviewId(interview);
    }
    comment.setCandidateId(candidate);
    comment.setUserId(user);
    comment.setCommentDetail(detailComment);
    comment.setCreateDate(new Date());
    return commentResponsitory.save(comment);
  }

  @Override
  public List<Comment> findByCandidateId(Candidate candidateId) {
    return commentResponsitory.findByCandidateId(candidateId);
  }

  @Override
  public Page<Comment> filterComment(Long cvId, Long candidateId, Long interviewId,
      Pageable pageable) {
    Specification<Comment> specification = new CommentSpecification(cvId, candidateId, interviewId);
    return commentResponsitory.findAll(specification, pageable);
  }

  @Override
  public List<Comment> findByCvId(Long cvId) {
    Specification<Comment> specification = new CommentSpecification(cvId, null, null);
    return commentResponsitory.findAll(specification);
  }
}
