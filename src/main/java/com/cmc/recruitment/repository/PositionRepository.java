package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
  Position findByTitle(String title);
  
  @Query("SELECT r FROM Position r WHERE r.isDelete = 0")
  Page<Position> findAll(Pageable pageable);
  
  @Query("SELECT r FROM Position r WHERE r.isDelete = 0")
  List<Position> findAll();
}
