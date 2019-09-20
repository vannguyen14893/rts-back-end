package com.cmc.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.InterviewStatus;

@Repository
public interface InterviewStatusRepository extends JpaRepository<InterviewStatus, Long> {
	InterviewStatus findByTitle(String title);
}
