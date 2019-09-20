package com.cmc.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.CvUrl;
import com.cmc.recruitment.utils.QueryConstants;
import com.cmc.recruitment.utils.Constants;

@Repository
public interface CvUrlRepository extends JpaRepository<CvUrl, Long> {
  @Query(QueryConstants.CvUrl.GET_CV_URL_BY_CV_ID)
  List<CvUrl> getCvUrlByCvId(@Param(Constants.PARAM.CV_ID_PARAM) Long cvId);
}
