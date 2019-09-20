package com.cmc.recruitment.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Cv;
import com.cmc.recruitment.entity.CvStatus;
import com.cmc.recruitment.entity.CvUrl;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.CvRepository;
import com.cmc.recruitment.repository.CvUrlRepository;
import com.cmc.recruitment.service.CvService;
import com.cmc.recruitment.service.UserService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.specification.CvSpecification;
import com.cmc.recruitment.utils.UpLoadFiles;

@Service
public class CvServiceImpl implements CvService {

  @Autowired
  CvRepository cvRepository;
  @Autowired
  UserService userService;
  @Autowired
  UpLoadFiles uploadService;
  @Autowired
  CvUrlRepository cvUrlRepository;

  @Override
  public List<Cv> findAllCv() {
    return cvRepository.findAll();
  }

  @Override
  public Cv getCvInfoByEmail(Cv cv) {
    return cvRepository.findByEmail(cv.getEmail());
  }

  /**
   * NHPhong: create CV.
   */
  @Override
  public Cv addCv(Cv cv) {
    // set values
    User user = userService.getUserInfoByUserName().get(0);
    cv.setCreatedBy(user);
    cv.setStatusId(new CvStatus(1L));
    Date curentDate = new Date();
    cv.setCreatedDate(curentDate);
    cv.setProfileImg(
        uploadService.fileIsExitsInFoderCv(cv.getProfileImg()) ? cv.getProfileImg() : null);

    List<CvUrl> listCvUrl = new LinkedList<>();
    for (CvUrl elementCv : cv.getCvUrlCollection()) {
      elementCv.setCvId(cv);
      if (uploadService.fileIsExitsInFoderCv(elementCv.getUrl()))
        listCvUrl.add(elementCv);
    }
    cv.setCvUrlCollection(listCvUrl);

    Cv newCv = cvRepository.save(cv);
    return newCv;
  }

  @Override
  public void deleteCv(Cv cv) {
    cvRepository.delete(cv);
  }

  @Override
  public Cv findOne(long id) {
    return cvRepository.findOne(id);
  }

  @Override
  public Page<Cv> searchFilterCv(String input, Long hrId, Long requestId, List<Long> statusId,
      List<Long> experienceId, List<Long> skillId,List<Long> certificationId, Long requestIdOfCandidate, Pageable pageable) {
    input = (input != null) ? input.trim() : input;
    Specification<Cv> spec = new CvSpecification(input, hrId, requestId, statusId, experienceId,
        skillId, certificationId, requestIdOfCandidate);
    return cvRepository.findAll(spec, pageable);
  }

  @Override
  public Cv findByEmail(String email) {
    return cvRepository.findByEmail(email);
  }

  @Override
  public Cv findByFacebook(String facebook) {
    return cvRepository.findByFacebook(facebook);
  }

  @Override
  public Cv findByLinkedin(String linkedin) {
    return cvRepository.findByLinkedin(linkedin);
  }

  @Override
  public Cv findBySkype(String skype) {
    return cvRepository.findBySkype(skype);
  }

  @Override
  public boolean updateCv(Cv cv) throws IOException {
    // set values
    User user = userService.getUserInfoByUserName().get(0);
    cv.setEditedBy(user);
    Date curentDate = new Date();
    cv.setEditedDate(curentDate);
    // check upload Cv
    Cv oldCv = findOne(cv.getId());

    if (cv.getProfileImg() != null) {
      if (oldCv.getProfileImg() != null) {
        if (!oldCv.getProfileImg().equals(cv.getProfileImg())) {
          cv.setProfileImg(
              uploadService.fileIsExitsInFoderCv(cv.getProfileImg()) ? cv.getProfileImg() : null);
        }
      } else {
        cv.setProfileImg(
            uploadService.fileIsExitsInFoderCv(cv.getProfileImg()) ? cv.getProfileImg() : null);
      }
    }

    // 1 check collection urlCv: if element have tobeDeleted = false or null.
    List<CvUrl> listCvUrl = new LinkedList<>();
    for (CvUrl elementCvUrl : cv.getCvUrlCollection()) {
      if (elementCvUrl.getTobeDeleted() == null || !elementCvUrl.getTobeDeleted()) {
        // 2 check file have uploaded: if true save file name to list.
        elementCvUrl.setCvId(cv);
        if (uploadService.fileIsExitsInFoderCv(elementCvUrl.getUrl()))
          listCvUrl.add(elementCvUrl);
      } else {
        // 3 if tobeDeleted = true: delete file and remove record in tableUrlCv.
        if (uploadService.fileIsExitsInFoderCv(elementCvUrl.getUrl())) {
          uploadService
              .deleteFileUpload(Constants.Upload.FODER_UPLOADED_CV + elementCvUrl.getUrl());
        }
        cvUrlRepository.delete(elementCvUrl);
      }
    }
    cv.setCvUrlCollection(listCvUrl);

    return (cvRepository.save(cv) != null);
  }

}
