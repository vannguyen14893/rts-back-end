package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Comment;

public interface CommentService {
  Comment save(Comment comment);
  
  Comment addComment(Long candidateId, Long userId, Long interviewId, String detailComment);

  List<Comment> findByCandidateId(Candidate candidateId);

  Page<Comment> filterComment(Long cvId ,Long candidateId, Long interviewId, Pageable pageable);
  
  List<Comment> findByCvId(Long candidateId);
}
