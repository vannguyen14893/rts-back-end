package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Position;

public interface PositionService {
	List<Position> findAll();

	Position findOne(Long id);

	Position save(Position position);
  
  Page<Position> findAll(Pageable pageable);
  
  Position findByTitle(String title);
  
  Position createOrUpdate(Position position);

}
