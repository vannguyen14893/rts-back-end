package com.cmc.recruitment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.InterviewStatus;
import com.cmc.recruitment.service.InterviewStatusService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class InterviewStatusController {

  @Autowired
  InterviewStatusService interviewStatusService;

  /**
   * 
   * @description: get all interviewStatus without pageable
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return
   */
  @GetMapping(ConstantsUrl.InterviewStatus.ALL_UNLIMIT)
  public ResponseEntity<List<InterviewStatus>> getListPositions() {
    return new ResponseEntity<List<InterviewStatus>>(interviewStatusService.findAll(),
        HttpStatus.OK);
  }
  
  /**
   * @description: get interviewStatus with pageable
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param pageable
   * @return
   */
  @GetMapping(ConstantsUrl.InterviewStatus.ALL)
  public ResponseEntity<?> findAll(Pageable pageable) {
    try {
      Page<InterviewStatus> list = interviewStatusService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<InterviewStatus>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: create-or-update.
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param skill
   * @param result
   * @return
   */
  @PostMapping(ConstantsUrl.InterviewStatus.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated InterviewStatus interviewStatus,
      BindingResult result) {
    try {
      if (result.hasErrors())
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT,
            HttpStatus.NOT_ACCEPTABLE);
      interviewStatus = interviewStatusService.createOrUpdate(interviewStatus);
      if (interviewStatus != null)
        return new ResponseEntity<InterviewStatus>(interviewStatus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NOT_SAVE_MESSAGE,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
