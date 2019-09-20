package com.cmc.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.RequestStatus;

@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {
	RequestStatus findByTitle(String title);
}
