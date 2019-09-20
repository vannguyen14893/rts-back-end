package com.cmc.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.CvStatus;

@Repository
public interface CvStatusRepository extends JpaRepository<CvStatus, Long> {

  CvStatus findById(Long id);

  CvStatus findByTitle(String tile);
}
