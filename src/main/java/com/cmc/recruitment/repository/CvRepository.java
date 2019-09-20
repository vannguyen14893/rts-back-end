package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Cv;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long>, JpaSpecificationExecutor<Cv> {

  List<Cv> findByFullName(String fullName);

  Cv findById(Long id);

  Cv findByEmail(String email);

  Cv findByFacebook(String facebook);

  Cv findByLinkedin(String linkedin);

  Cv findBySkype(String skype);

  /**
   * 
   * @description: Query for seach by fistName and skillID.
   * @author: NHPhong.
   * @create_date: Feb 26, 2018.
   * @modifer: NHPhong.
   * @modifer_date: Feb 26, 2018.
   * @param fullName:
   *          if not exits = null query find all.
   * @param pageable
   * @return Page<Cv>.
   */
  @Query("SELECT DISTINCT cv FROM Cv cv "
      + "WHERE :fullName IS NULL OR LOWER(cv.fullName) like LOWER(CONCAT('%',:fullName,'%')) ")
  Page<Cv> getCvConditons(@Param("fullName") String fullName, Pageable pageable);

}
