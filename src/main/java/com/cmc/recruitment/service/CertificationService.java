package com.cmc.recruitment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Certification;

public interface CertificationService {
  List<Certification> findAll();

  List<Certification> listAllCertification();

  boolean addCertification(Certification certification);

  boolean updateCertification(Certification certification);

  void deleteCertification(Certification certification);

  Certification findOne(long id);

  Certification getCertificationByTitle(String title);
  
  boolean checkIfExist(Certification certification);
  
  Page<Certification> findAll(Pageable pageable);
  
  Certification createOrUpdate(Certification certification);
}
