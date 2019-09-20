package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Certification;
import com.cmc.recruitment.repository.CertificationRepository;
import com.cmc.recruitment.service.CertificationService;

@Service
public class CertificationServiceImpl implements CertificationService {

  @Autowired
  CertificationRepository certificationRepository;

  @Override
  public List<Certification> findAll() {
    return certificationRepository.findAll();
  }

  @Override
  public List<Certification> listAllCertification() {
    return certificationRepository.findAll();
  }

  @Override
  public boolean addCertification(Certification certification) {
	  Certification result = certificationRepository.save(certification);
    return result != null;
  }

  @Override
  public boolean updateCertification(Certification certification) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void deleteCertification(Certification certification) {
    // TODO Auto-generated method stub

  }

  @Override
  public Certification findOne(long id) {
    return certificationRepository.findById(id);
  }

  @Override
  public Certification getCertificationByTitle(String title) {
    return certificationRepository.findByTitle(title);
  }

@Override
public boolean checkIfExist(Certification certification) {
	List<Certification> list = certificationRepository.findAll();
	for(Certification item : list) {
		if(certification.getTitle().equals(item.getTitle()))
			return true;
	}
	return false;
}


@Override
public Page<Certification> findAll(Pageable pageable) {
  return certificationRepository.findAll(pageable);
}


@Override
public Certification createOrUpdate(Certification certification) {
  return certificationRepository.save(certification);
}
}

