package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.CvStatus;

public interface CvStatusService {

  List<CvStatus> findAll();

  CvStatus findOne(Long id);

  CvStatus save(CvStatus cvStatus);

  boolean updateCvStatus(CvStatus cvStatus);

  void deleteCvStatus(CvStatus cvStatus);
  
  Page<CvStatus> findAll(Pageable pageable);
  
  CvStatus findByTitle(String title);
  
  CvStatus createOrUpdate(CvStatus cvStatus);

}
