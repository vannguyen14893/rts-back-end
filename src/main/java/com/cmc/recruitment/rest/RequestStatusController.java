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

import com.cmc.recruitment.entity.RequestStatus;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.RequestStatusService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class RequestStatusController {

	@Autowired
	private RequestStatusService requestStatusService;

	/**
	 * @description: get all request status.
	 * @author: PXHoang
	 * @created_date: Feb 26, 2018
	 * @modifier: User
	 * @modifier_date: Feb 26, 2018
	 * @return
	 */
	@GetMapping(ConstantsUrl.RequestStatus.ALL_UNLIMIT)
	public ResponseEntity<List<RequestStatus>> getListRequestStatus() {
		try {
			List<RequestStatus> list = requestStatusService.getAllRequestStatus();
			if (list.size() > 0)
				return new ResponseEntity<List<RequestStatus>>(list, HttpStatus.OK);
			return new ResponseEntity<List<RequestStatus>>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<List<RequestStatus>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(ConstantsUrl.RequestStatus.DETAIL)
	public ResponseEntity<?> detailPosition(@PathVariable Long requestStatusId) {
		RequestStatus requestStatus = new RequestStatus();
		// get Request by requestId.
		try {
			requestStatus = requestStatusService.findOne(requestStatusId);
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR,
					Constants.RESPONSE.EXITS_CODE, Constants.RESPONSE.NOT_EXIST), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Check if request null
		if (requestStatus == null)
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
					Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
		return new ResponseEntity<RequestStatus>(requestStatus, HttpStatus.OK);
	}

	@PostMapping(ConstantsUrl.RequestStatus.ADD)
	public ResponseEntity<?> createOrUpdate(@RequestBody @Validated RequestStatus requestStatus) {
		try {
			if (requestStatus.getTitle().isEmpty()) {
				return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
			}
			RequestStatus result = requestStatusService.save(requestStatus);
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
   * @description: /find-all.
   * @author: VDHoan
   * @created_date: Mar 20, 2018
   * @modifier: User
   * @modifier_date: Mar 20, 2018
   * @param pageable
   * @return
   */
  @GetMapping(ConstantsUrl.RequestStatus.ALL)
  public ResponseEntity<?> getAllRequestStatus(Pageable pageable) {
    Page<RequestStatus> list;
    try {
      list = requestStatusService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<RequestStatus>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: /find-by-title.
   * @author: VDHoan
   * @created_date: Mar 20, 2018
   * @modifier: User
   * @modifier_date: Mar 20, 2018
   * @param title
   * @return
   */
  @GetMapping(ConstantsUrl.RequestStatus.FIND_BY_TITLE)
  public ResponseEntity<?> getOneRequestStatus(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
      RequestStatus requestStatus = requestStatusService.findByTitle(title);
      if (requestStatus != null)
        return new ResponseEntity<RequestStatus>(requestStatus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: /create-or-update.
   * @author: VDHoan
   * @created_date: Mar 20, 2018
   * @modifier: User
   * @modifier_date: Mar 20, 2018
   * @param requestStatus
   * @param result
   * @return
   */
  @PostMapping(ConstantsUrl.RequestStatus.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated RequestStatus requestStatus, BindingResult result) {
    try {
      if(result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
      }
      RequestStatus savedRequestStatus = requestStatusService.createOrUpdate(requestStatus);
      if (savedRequestStatus != null)
        return new ResponseEntity<RequestStatus>(savedRequestStatus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_CAN_NOT_SAVE, HttpStatus.BAD_GATEWAY);
  }

}
