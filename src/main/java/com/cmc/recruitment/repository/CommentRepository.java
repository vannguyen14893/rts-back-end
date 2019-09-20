package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
	List<Comment> findByCandidateId(Candidate candidateId);

}
