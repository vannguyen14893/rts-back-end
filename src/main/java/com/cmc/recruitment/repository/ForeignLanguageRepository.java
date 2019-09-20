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

import com.cmc.recruitment.entity.ForeignLanguage;

/**
 * @description: .
 * @author: LCnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Repository
public interface ForeignLanguageRepository extends JpaRepository<ForeignLanguage, Long>{
	ForeignLanguage findByTitle(String title);
	
	@Query("SELECT r FROM ForeignLanguage r WHERE r.isDelete = 0")
	  Page<ForeignLanguage> findAll(Pageable pageable);
	  
	  @Query("SELECT r FROM ForeignLanguage r WHERE r.isDelete = 0")
	  List<ForeignLanguage> findAll();
}
