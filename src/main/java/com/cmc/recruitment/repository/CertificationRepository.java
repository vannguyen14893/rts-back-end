package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {

	Certification findById(Long id);

	Certification findByTitle(String titleCertification);

	@Query("SELECT r FROM Certification r WHERE r.isDelete = 0")
	Page<Certification> findAll(Pageable pageable);

	@Query("SELECT r FROM Certification r WHERE r.isDelete = 0")
	List<Certification> findAll();
}
