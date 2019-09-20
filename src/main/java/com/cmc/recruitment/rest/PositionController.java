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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Position;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.PositionService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class PositionController {

  @Autowired
  PositionService positionService;

  @GetMapping(ConstantsUrl.Position.ALL_UNLIMIT)
  public ResponseEntity<?> getListPositions() {
	  List<Position> list;
		try {
			list = positionService.findAll();
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_EXIST), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Position>>(list, HttpStatus.OK);
  }
  
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER')")
  @GetMapping(ConstantsUrl.Position.DETAIL)
  public ResponseEntity<?> detailPosition(@PathVariable Long positionId) {
    Position position = new Position();
    // get Request by requestId.
    try {
    	position = positionService.findOne(positionId);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Check if request null
    if (position == null)
      return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
          Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
    return new ResponseEntity<Position>(position, HttpStatus.OK);
  }
  
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER')")
  @PostMapping(ConstantsUrl.Position.ADD)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated Position position) {
    try {
      if(position.getTitle().isEmpty()) {
    	  return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
      }
      Position result = positionService.save(position);
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
  @GetMapping(ConstantsUrl.Position.ALL)
  public ResponseEntity<?> getAllPosition(Pageable pageable) {
    Page<Position> list;
    try {
      list = positionService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<Position>>(list, HttpStatus.OK);
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
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER')")
  @GetMapping(ConstantsUrl.Position.FIND_BY_TITLE)
  public ResponseEntity<?> getOnePosition(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
      Position position = positionService.findByTitle(title);
      if (position != null)
        return new ResponseEntity<Position>(position, HttpStatus.OK);
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
   * @param position
   * @param result
   * @return
   */
  @PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER')")
  @PostMapping(ConstantsUrl.Position.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated Position position, BindingResult result) {
    try {
      if(result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
      }
      position.setIsDelete(false);
      Position savedPosition = positionService.createOrUpdate(position);
      if (savedPosition != null)
        return new ResponseEntity<Position>(savedPosition, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_CAN_NOT_SAVE, HttpStatus.BAD_GATEWAY);
  }

}
