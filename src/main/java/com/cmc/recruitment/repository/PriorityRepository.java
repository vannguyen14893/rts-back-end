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

import com.cmc.recruitment.entity.Priority;

/**
 * @description: .
 * @author: VDHoan
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
  Priority findByTitle(String title);
  
  @Query("SELECT r FROM Priority r WHERE r.isDelete = 0")
  Page<Priority> findAll(Pageable pageable);
  
  @Query("SELECT r FROM Priority r WHERE r.isDelete = 0")
  List<Priority> findAll();
}
