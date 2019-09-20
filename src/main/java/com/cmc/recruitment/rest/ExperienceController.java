/**
 * 
 */
package com.cmc.recruitment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Experience;
import com.cmc.recruitment.service.ExperienceService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@RestController
public class ExperienceController {
  @Autowired
  ExperienceService experienceService;
  
  @GetMapping(ConstantsUrl.Experience.ALL_UNLIMIT)
  public ResponseEntity<List<Experience>> allExperience(){
    List<Experience> list = experienceService.findAll();
    return new ResponseEntity<List<Experience>>(list, HttpStatus.OK);
  }
  /**
   * @description: "/find-all".
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param pageable
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HR_MANAGER')")
  @GetMapping(ConstantsUrl.Experience.ALL)
  public ResponseEntity<?> findAll(Pageable pageable) {
    try {
      Page<Experience> list = experienceService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<Experience>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: "/create-or-update".
   * @author: VDHoan
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param skill
   * @param result
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_HR_MANAGER')")
  @PostMapping(ConstantsUrl.Experience.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated Experience experience,
      BindingResult result) {
    try {
      if (result.hasErrors())
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT,
            HttpStatus.NOT_ACCEPTABLE);
      experience.setIsDelete(false);
      experience = experienceService.createOrUpdate(experience);
      if (experience != null)
        return new ResponseEntity<Experience>(experience, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NOT_SAVE_MESSAGE,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  /**
   * @description: "/find-by-title".
   * @author: VDHoan
   * @created_date: Mar 28, 2018
   * @modifier: User
   * @modifier_date: Mar 28, 2018
   * @param title
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER')")
  @GetMapping(ConstantsUrl.Experience.FIND_BY_TITLE)
  public ResponseEntity<?> getOneExperience(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
      Experience experience = experienceService.findByTitle(title);
      if (experience != null)
        return new ResponseEntity<Experience>(experience, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }
}
