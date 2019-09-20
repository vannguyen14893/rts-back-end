package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Position;
import com.cmc.recruitment.repository.PositionRepository;
import com.cmc.recruitment.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService {

	@Autowired
	PositionRepository positionRepository;

	@Override
	public List<Position> findAll() {
		return positionRepository.findAll();
	}

	@Override
	public Position findOne(Long id) {
		return positionRepository.findOne(id);
	}

	@Override
	public Position save(Position position) {
		return positionRepository.save(position);
	}

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param pageable
   * @return
   */
  @Override
  public Page<Position> findAll(Pageable pageable) {
    return positionRepository.findAll(pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param title
   * @return
   */
  @Override
  public Position findByTitle(String title) {
    return positionRepository.findByTitle(title);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param position
   * @return
   */
  @Override
  public Position createOrUpdate(Position position) {
    return positionRepository.save(position);
  }

}
