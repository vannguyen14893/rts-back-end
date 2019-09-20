package com.cmc.recruitment.rest;

import java.util.List;

import org.apache.log4j.Logger;
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

import com.cmc.recruitment.entity.CandidateStatus;
import com.cmc.recruitment.service.CandidateStatusService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class CandidateStatusController {

	final static Logger LOG = Logger.getLogger(CandidateStatusController.class);

	@Autowired
	private CandidateStatusService candidateStatusService;

	/**
	 * @description: get all candidateStatus without pageable
	 * @author: PXHoang
	 * @created_date: Feb 7, 2018
	 * @modifier: User
	 * @modifier_date: Feb 7, 2018
	 * @return list Status
	 */
	@GetMapping(ConstantsUrl.CandidateStatus.ALL_UNLIMIT)
	public ResponseEntity<List<CandidateStatus>> getListStatus() {
		try {
			List<CandidateStatus> list = candidateStatusService.getAllStatus();
			if (list.isEmpty()) {
				return new ResponseEntity<List<CandidateStatus>>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<CandidateStatus>>(list, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e);
			return new ResponseEntity<List<CandidateStatus>>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
  /**
   * @description: get all candidate status with pageable
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param pageable
   * @return
   */
  @GetMapping(ConstantsUrl.CandidateStatus.ALL)
  public ResponseEntity<?> getAllCandidateStatus(Pageable pageable) {
    Page<CandidateStatus> list;
    try {
      list = candidateStatusService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<CandidateStatus>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: find candidateStatus by title
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param title
   * @return
   */
  @GetMapping(ConstantsUrl.CandidateStatus.FIND_BY_TITLE)
  public ResponseEntity<?> getOneCandidateStatus(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
      CandidateStatus candidateStatus = candidateStatusService.findByTitle(title);
      if (candidateStatus != null)
        return new ResponseEntity<CandidateStatus>(candidateStatus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

  /**
   * @description: create or update candidate status.
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param candidateStatus
   * @param result
   * @return
   */
  @PostMapping(ConstantsUrl.CandidateStatus.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated CandidateStatus candidateStatus, BindingResult result) {
    try {
      if(result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
      }
      CandidateStatus savedCandidateStatus = candidateStatusService.createOrUpdate(candidateStatus);
      if (savedCandidateStatus != null)
        return new ResponseEntity<CandidateStatus>(savedCandidateStatus, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_CAN_NOT_SAVE, HttpStatus.BAD_GATEWAY);
  }
}
