package com.cmc.recruitment.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cmc.recruitment.entity.Cv;

public interface CvService {

  List<Cv> findAllCv();

  Cv addCv(Cv cv);
  
  boolean updateCv(Cv cv) throws IOException;

  void deleteCv(Cv cv);

  Cv findOne(long id);

  Cv getCvInfoByEmail(Cv cv);

  Cv findByEmail(String email);

  Cv findByFacebook(String faceBook);

  Cv findByLinkedin(String linkediIn);

  Cv findBySkype(String skype);

  /**
   * @description: use for get list search and filter Cv has pagination.
   * @author: NHPhong.
   * @create_date: Mar 1, 2018.
   * @param firstName
   * @param pageable
   * @return
   */
//  Page<Cv> searchFilterCv(String input, Long requestId, Long statusId, Long experienceId,
//      Long skillId, Long requestIdOfCandidate, Pageable pageable) throws Exception;

  Page<Cv> searchFilterCv(String input,Long hrId, Long requestId, List<Long> statusId,
      List<Long> experienceId, List<Long> skillId, List<Long> certificationId, Long requestIdOfCandidate, Pageable pageable);
}
