package com.cmc.recruitment.service;

import java.util.List;

import com.cmc.recruitment.entity.CvUrl;

public interface CvUrlService {

  boolean addCv(CvUrl cvUrl);

  List<CvUrl> findAllCvUrl();

  List<CvUrl> findByCv(Long CvId);
  
  void deleteCvUrl(Long cvUrlId);
}
