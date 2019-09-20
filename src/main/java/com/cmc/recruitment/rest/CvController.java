package com.cmc.recruitment.rest;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Cv;
import com.cmc.recruitment.service.CvService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;
import com.cmc.recruitment.utils.UpLoadFiles;

@RestController
public class CvController {

  final static Logger LOGGER = Logger.getLogger(CvController.class);
  @Autowired
  private CvService cvService;

  @Autowired
  private UpLoadFiles uploadFiles;

  /**
   * @description: use for search by full name, email and phone. filter by
   *               parameter input.
   * @author: NHPhong.
   * @create_date: Mar 27, 2018
   * @param input
   * @param requestId
   * @param experienceId
   * @param statusId
   * @param skillId
   * @param pageable
   * @return: Page<Cv> listCv.
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @GetMapping(value = ConstantsUrl.CV.ALL)
  public ResponseEntity<?> searchFilterCv(
      @RequestParam(value = "input", required = false) String input,
      @RequestParam(value = "hrId", required = false) Long hrId,
      @RequestParam(value = "requestId", required = false) Long requestId,
      @RequestParam(value = "experienceId", required = false) List<Long> experienceId,
      @RequestParam(value = "statusId", required = false) List<Long> statusId,
      @RequestParam(value = "skillId", required = false) List<Long> skillId,
      @RequestParam(value = "certificationId", required = false) List<Long> certificationId,
      @RequestParam(value = "requestIdOfCandidate", required = false) Long requestIdOfCandidate,
      Pageable pageable) {

    Page<Cv> listCv;
    try {
      listCv = cvService.searchFilterCv(input, hrId, requestId, statusId, experienceId, skillId, certificationId,
          requestIdOfCandidate, pageable);
      return (listCv.getContent().size() > 0) ? new ResponseEntity<>(listCv, HttpStatus.OK)
          : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error(" Error view List and filter Cv " + e);
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description NHPhong. use for get all CV.
   * @param pageable
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.CV.ALL_UNLIMIT)
  public ResponseEntity<?> getListAllCv(Pageable pageable) {
    try {
      List<Cv> list = cvService.findAllCv();
      return (list.size() > 0) ? new ResponseEntity<List<Cv>>(list, HttpStatus.OK)
          : new ResponseEntity<List<Cv>>(list, HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      LOGGER.error(e);
      return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description NHPhong. function use for add a CV.
   * @param cv
   * @param result
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.CV.ADD)
  public ResponseEntity<?> addCv(@RequestBody @Validated Cv cv, BindingResult result) {
    try {
      if (result.hasErrors()) {
        return new ResponseEntity<String>("{ message: " + result.getAllErrors().toString() + "}",
            HttpStatus.BAD_REQUEST);
      }
      if (cv == null) {
        return new ResponseEntity<String>("{ message: " + Constants.RESPONSE.WRONG_INPUT + "}",
            HttpStatus.BAD_REQUEST);
      }
      Cv newCv = cvService.addCv(cv);
      if (newCv != null) {
        return new ResponseEntity<Cv>(newCv, HttpStatus.CREATED);
      } else {
        // not save CV => delete file img; send message error.
        uploadFiles.deleteFileUpload(Constants.Upload.FODER_UPLOADED_CV + cv.getProfileImg());
        uploadFiles.deleteCvUrl(cv.getCvUrlCollection(), Constants.Upload.FODER_UPLOADED_CV);
        return new ResponseEntity<String>(Constants.RESPONSE.ERROR_DATA, HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
      try {
        // server error not save CV: delete cvUrl, delete file img
        uploadFiles.deleteFileUpload(Constants.Upload.FODER_UPLOADED_CV + cv.getProfileImg());
        uploadFiles.deleteCvUrl(cv.getCvUrlCollection(), Constants.Upload.FODER_UPLOADED_CV);
      } catch (IOException ex) {
        LOGGER.error(" Can not delete file: " + ex);
      }
      return new ResponseEntity<String>("{ message: " + Constants.RESPONSE.ERROR_DATA + "}",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description: get a CV
   * @author: PXHoang
   * @created_date: Feb 8, 2018
   * @modifier: User
   * @modifier_date: Feb 8, 2018
   * @param cvId
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.CV.DETAIL)
  public ResponseEntity<?> findOne(@PathVariable long cvId) {
    Cv cv = new Cv();
    try {
      cv = cvService.findOne(cvId);
      if (cv == null) {
        return new ResponseEntity<String>(Constants.RESPONSE.ERROR_NOT_FIND, HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<Cv>(cv, HttpStatus.OK);
  }

  /**
   * @description: function use check exist contacts of CV.
   * @author: NHPhong
   * @created_date:
   * @modifier:
   * @modifier_date:
   * @param
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.CV.CHECK_SOCIAL)
  public ResponseEntity<?> checkContact(
      @RequestParam(value = Constants.CHECK_CONTACTS.EMAIL_PARAM, required = false) String email,
      @RequestParam(value = Constants.CHECK_CONTACTS.FACEBOOK_PARAM, required = false) String facebook,
      @RequestParam(value = Constants.CHECK_CONTACTS.LINKEDIN_PARAM, required = false) String linkedin,
      @RequestParam(value = Constants.CHECK_CONTACTS.SKYPE_PARAM, required = false) String skype) {
    try {
      Cv cv = new Cv();
      if (email != null) {
        cv = cvService.findByEmail(email);
        return (cv != null) ? new ResponseEntity<>(cv.getId(), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if (facebook != null) {
        cv = cvService.findByFacebook(facebook);
        return (cv != null) ? new ResponseEntity<>(cv.getId(), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if (linkedin != null) {
        cv = cvService.findByLinkedin(linkedin);
        return (cv != null) ? new ResponseEntity<>(cv.getId(), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      if (skype != null) {
        cv = cvService.findBySkype(skype);
        return (cv != null) ? new ResponseEntity<>(cv.getId(), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<String>(Constants.RESPONSE.NO_INPUT, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      LOGGER.error(e);
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_HR_MEMBER','ROLE_HR_MANAGER')")
  @PutMapping(ConstantsUrl.CV.UPDATE)
  public ResponseEntity<?> updateCv(@RequestBody @Validated Cv cv, BindingResult result) {
    try {
      if (result.hasErrors()) {
        return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
      }
      if (cv == null) {
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.BAD_REQUEST);
      }
      if (cvService.updateCv(cv)) {
        return new ResponseEntity<>(Constants.RESPONSE.SUCCESS_CODE, HttpStatus.CREATED);
      } else {
        // not save CV => delete file img; send message error.
        uploadFiles.deleteFileUpload(Constants.Upload.FODER_UPLOADED_CV + cv.getProfileImg());
        uploadFiles.deleteCvUrl(cv.getCvUrlCollection(), Constants.Upload.FODER_UPLOADED_CV);
        return new ResponseEntity<String>(Constants.RESPONSE.ERROR_DATA, HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
      try {
        // server error not save CV: delete cvUrl, delete file img
        uploadFiles.deleteFileUpload(Constants.Upload.FODER_UPLOADED_CV + cv.getProfileImg());
        uploadFiles.deleteCvUrl(cv.getCvUrlCollection(), Constants.Upload.FODER_UPLOADED_CV);
      } catch (IOException ex) {
        LOGGER.error(" Can not delete file: " + ex);
      }
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_DATA,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
