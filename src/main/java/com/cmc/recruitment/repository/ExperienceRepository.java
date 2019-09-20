/**
 * 
 */
package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Experience;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Repository
public interface ExperienceRepository extends JpaRepository<Experience , Long>{
	Experience findByTitle(String title);
	
	@Query("SELECT r FROM Experience r WHERE r.isDelete = 0")
	  Page<Experience> findAll(Pageable pageable);
	  
	  @Query("SELECT r FROM Experience r WHERE r.isDelete = 0")
	  List<Experience> findAll();
}
