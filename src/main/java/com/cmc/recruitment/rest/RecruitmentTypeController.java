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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.RecruitmentType;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.RecruitmentTypeService;
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
public class RecruitmentTypeController {
  @Autowired
  RecruitmentTypeService recruitmentTypeService;
  
  @GetMapping(ConstantsUrl.RecruitmentType.ALL_UNLIMIT)
  public ResponseEntity<?> allProject(){
	  List<RecruitmentType> list;
		try {
			list = recruitmentTypeService.findAll();
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_EXIST), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<RecruitmentType>>(list, HttpStatus.OK);
  }
  
  @GetMapping(ConstantsUrl.RecruitmentType.DETAIL)
  public ResponseEntity<?> detailRecruitmentType(@PathVariable Long recruitmentTypeId) {
    RecruitmentType recruitmentType = new RecruitmentType();
    // get Request by requestId.
    try {
    	recruitmentType = recruitmentTypeService.findOne(recruitmentTypeId);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Check if request null
    if (recruitmentType == null)
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
          Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
    return new ResponseEntity<RecruitmentType>(recruitmentType, HttpStatus.OK);
  }
  
  @PostMapping(ConstantsUrl.RecruitmentType.ADD)
  public ResponseEntity<?> createOrRecruitmentType(@RequestBody @Validated RecruitmentType recruitmentType) {
    try {
      if(recruitmentType.getTitle().isEmpty()) {
    	  return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
      }
      RecruitmentType result = recruitmentTypeService.save(recruitmentType);
      if(result == null)
    	  return new ResponseEntity<String>(Constants.RESPONSE.NOT_SAVE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
    	return new ResponseEntity<String>(Constants.RESPONSE.INTERNAL_ERROR_SERVICE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param pageable
   * @return
   */
  @GetMapping(ConstantsUrl.RecruitmentType.ALL)
  public ResponseEntity<?> getAllRecruitmentType(Pageable pageable) {
    Page<RecruitmentType> list;
    try {
      list = recruitmentTypeService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<RecruitmentType>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param title
   * @return
   */
  @GetMapping(ConstantsUrl.RecruitmentType.FIND_BY_TITLE)
  public ResponseEntity<?> getOneRecruitmentType(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
      RecruitmentType recruitmentType = recruitmentTypeService.findByTitle(title);
      if (recruitmentType != null)
        return new ResponseEntity<RecruitmentType>(recruitmentType, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param recruitmentType
   * @param result
   * @return
   */
  @PostMapping(ConstantsUrl.RecruitmentType.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated RecruitmentType recruitmentType, BindingResult result) {
    try {
      if(result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
      }
      recruitmentType.setIsDelete(false);
      RecruitmentType savedRecruitmentType = recruitmentTypeService.createOrUpdate(recruitmentType);
      if (savedRecruitmentType != null)
        return new ResponseEntity<RecruitmentType>(savedRecruitmentType, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_CAN_NOT_SAVE, HttpStatus.BAD_GATEWAY);
  }

}
