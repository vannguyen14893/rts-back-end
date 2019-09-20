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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.ForeignLanguage;
import com.cmc.recruitment.service.ForeignlanguageService;
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
public class ForeignlanguageController {
  @Autowired
  ForeignlanguageService foreignlanguageService;
  
  @GetMapping(ConstantsUrl.ForeignLanguage.ALL_UNLIMIT)
  public ResponseEntity<List<ForeignLanguage>> allProject(){
    List<ForeignLanguage> list = foreignlanguageService.findAll();
    return new ResponseEntity<List<ForeignLanguage>>(list, HttpStatus.OK);
  }
  
  @GetMapping(ConstantsUrl.ForeignLanguage.ALL)
  public ResponseEntity<?> findAll(Pageable pageable) {
    try {
      Page<ForeignLanguage> list = foreignlanguageService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<ForeignLanguage>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  @PostMapping(ConstantsUrl.ForeignLanguage.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated ForeignLanguage foreignLanguage,
      BindingResult result) {
    try {
      if (result.hasErrors())
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT,
            HttpStatus.NOT_ACCEPTABLE);
      foreignLanguage.setIsDelete(false);
      foreignLanguage = foreignlanguageService.createOrUpdate(foreignLanguage);
      if (foreignLanguage != null)
        return new ResponseEntity<ForeignLanguage>(foreignLanguage, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NOT_SAVE_MESSAGE,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @GetMapping(ConstantsUrl.ForeignLanguage.FIND_BY_TITLE)
  public ResponseEntity<?> getOneExperience(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
    	ForeignLanguage foreignLanguage = foreignlanguageService.findByTitle(title);
      if (foreignLanguage != null)
        return new ResponseEntity<ForeignLanguage>(foreignLanguage, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

}
