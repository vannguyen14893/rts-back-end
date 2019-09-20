package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.CvStatus;
import com.cmc.recruitment.repository.CvStatusRepository;
import com.cmc.recruitment.service.CvStatusService;

@Service
public class CvStatusServiceImpl implements CvStatusService {

  @Autowired
  CvStatusRepository cvStatusRepository;

  @Override
  public List<CvStatus> findAll() {
    return cvStatusRepository.findAll();
  }

  @Override
  public CvStatus findOne(Long id) {
    return cvStatusRepository.findById(id);
  }

  @Override
  public CvStatus save(CvStatus cvStatus) {
    return cvStatusRepository.save(cvStatus);
  }

  @Override
  public boolean updateCvStatus(CvStatus cvStatus) {
    return false;
  }

  @Override
  public void deleteCvStatus(CvStatus cvStatus) {
    cvStatusRepository.delete(cvStatus);
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
  public Page<CvStatus> findAll(Pageable pageable) {
    return cvStatusRepository.findAll(pageable);
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
  public CvStatus findByTitle(String title) {
    return cvStatusRepository.findByTitle(title);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param cvStatus
   * @return
   */
  @Override
  public CvStatus createOrUpdate(CvStatus cvStatus) {
    return cvStatusRepository.save(cvStatus);
  }

}
