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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Certification;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.CertificationService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class CertificationController {

	@Autowired
	CertificationService certificationService;

	@GetMapping(ConstantsUrl.Certification.ALL_UNLIMIT)
	public ResponseEntity<List<Certification>> getListCertification() {
		return new ResponseEntity<List<Certification>>(certificationService.findAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@PostMapping(ConstantsUrl.Certification.ADD)
	public ResponseEntity<BaseResponse> saveCertification(@RequestBody Certification certification) {
		if(certification.getTitle().length() == 0) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SKILL_TITLE_NOT_BLANK),
					HttpStatus.BAD_REQUEST);
		}
		try {
			boolean isExist = certificationService.checkIfExist(certification);
			if(isExist == true)
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SKILL_WAS_EXISTED),
						HttpStatus.BAD_REQUEST);
			boolean result = certificationService.addCertification(certification);
			if(result == false)
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
						HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@PutMapping(ConstantsUrl.Certification.UPDATE)
	public ResponseEntity<BaseResponse> updateCertification(@RequestBody Certification certification) {
		if(certification.getTitle().length() == 0) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SKILL_TITLE_NOT_BLANK),
					HttpStatus.BAD_REQUEST);
		}
		try {
			boolean result = certificationService.addCertification(certification);
			if(result == false)
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
						HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@GetMapping(ConstantsUrl.Certification.DETAIL)
	public ResponseEntity<?> detailCertification(@PathVariable int certificationId) {
		Certification certification = null;
		try {
			certification = certificationService.findOne(certificationId);
			if (certification == null) {
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
						Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR,
					Constants.RESPONSE.EXITS_CODE, Constants.RESPONSE.NOT_EXIST), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Certification>(certification, HttpStatus.OK);
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
	@GetMapping(ConstantsUrl.Certification.ALL)
	public ResponseEntity<?> findAll(Pageable pageable) {
	  try {
	    Page<Certification> list = certificationService.findAll(pageable);
	    if (list.getSize() > 0)
	      return new ResponseEntity<Page<Certification>>(list, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
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
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@PostMapping(ConstantsUrl.Certification.SAVE)
	public ResponseEntity<?> createOrUpdate(@RequestBody @Validated Certification certification, BindingResult result) {
	  try {
	    if (result.hasErrors())
	      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
	    certification.setIsDelete(false);
	    certification = certificationService.createOrUpdate(certification);
	    if (certification != null)
	      return new ResponseEntity<Certification>(certification, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<String>(Constants.RESPONSE.NOT_SAVE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * @description: "/find-by-title".
	 * @author: VDHoan
	 * @created_date: Mar 23, 2018
	 * @modifier: User
	 * @modifier_date: Mar 23, 2018
	 * @param title
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@GetMapping(ConstantsUrl.Certification.FIND_BY_TITLE)
	public ResponseEntity<?> findByTitle(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
	  try {
		  Certification certification = certificationService.getCertificationByTitle(title);
	    if (certification != null)
	      return new ResponseEntity<Certification>(certification, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
	}
}
