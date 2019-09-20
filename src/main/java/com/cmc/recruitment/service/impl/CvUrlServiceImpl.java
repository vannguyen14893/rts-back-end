package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.CvUrl;
import com.cmc.recruitment.repository.CvUrlRepository;
import com.cmc.recruitment.service.CvUrlService;

@Service
public class CvUrlServiceImpl implements CvUrlService {

  @Autowired
  CvUrlRepository cvUrlRepository;

  @Override
  public boolean addCv(CvUrl cvUrl) {
    return (cvUrlRepository.save(cvUrl) != null);
  }

  @Override
  public List<CvUrl> findAllCvUrl() {
    return cvUrlRepository.findAll();
  }

  @Override
  public List<CvUrl> findByCv(Long cvId) {
    return cvUrlRepository.getCvUrlByCvId(cvId);
  }

  @Override
  public void deleteCvUrl(Long cvUrlId) {
    System.out.println("vào delete " + cvUrlId);
    cvUrlRepository.delete(cvUrlId);
  }

}
