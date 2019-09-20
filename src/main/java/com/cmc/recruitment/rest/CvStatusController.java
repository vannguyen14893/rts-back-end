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

import com.cmc.recruitment.entity.CvStatus;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.CvStatusService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class CvStatusController {
	@Autowired
	CvStatusService cvStatusService;

	@GetMapping(ConstantsUrl.CvStatus.ALL_UNLIMIT)
	public ResponseEntity<?> getListCvStatus() {
		List<CvStatus> list;
		try {
			list = cvStatusService.findAll();
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_EXIST), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<CvStatus>>(list, HttpStatus.OK);
	}

	@GetMapping(ConstantsUrl.CvStatus.DETAIL)
	public ResponseEntity<?> detailCvStatus(@PathVariable Long cvStatusId) {
		CvStatus cvStatus = new CvStatus();
		// get Request by requestId.
		try {
			cvStatus = cvStatusService.findOne(cvStatusId);
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR,
					Constants.RESPONSE.EXITS_CODE, Constants.RESPONSE.NOT_EXIST), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cvStatus == null)
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
					Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
		return new ResponseEntity<CvStatus>(cvStatus, HttpStatus.OK);
	}

	@PostMapping(ConstantsUrl.CvStatus.ADD)
	public ResponseEntity<?> createOrcvStatus(@RequestBody @Validated CvStatus cvStatus) {
		try {
			if (cvStatus.getTitle().isEmpty()) {
				return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
			}
			CvStatus result = cvStatusService.save(cvStatus);
			if (result == null)
				return new ResponseEntity<String>(Constants.RESPONSE.NOT_SAVE_MESSAGE,
						HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<String>(Constants.RESPONSE.INTERNAL_ERROR_SERVICE,
					HttpStatus.INTERNAL_SERVER_ERROR);
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
  @GetMapping(ConstantsUrl.CvStatus.ALL)
  public ResponseEntity<?> getAllCvStatus(Pageable pageable) {
    Page<CvStatus> list;
    try {
      list = cvStatusService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<CvStatus>>(list, HttpStatus.OK);
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
  @GetMapping(ConstantsUrl.CvStatus.FIND_BY_TITLE)
  public ResponseEntity<?> getOneCvStatus(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
      CvStatus cvStatus = cvStatusService.findByTitle(title);
      if (cvStatus != null)
        return new ResponseEntity<CvStatus>(cvStatus, HttpStatus.OK);
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
   * @param cvStatus
   * @param result
   * @return
   */
  @PostMapping(ConstantsUrl.CvStatus.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated CvStatus cvStatus, BindingResult result) {
    try {
      if(result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
      }
      CvStatus savedCvStatus = cvStatusService.createOrUpdate(cvStatus);
      if (savedCvStatus != null)
        return new ResponseEntity<CvStatus>(savedCvStatus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_CAN_NOT_SAVE, HttpStatus.BAD_GATEWAY);
  }
}
