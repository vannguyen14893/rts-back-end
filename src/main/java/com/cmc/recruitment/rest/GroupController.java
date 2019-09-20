/**
 * 
 */
package com.cmc.recruitment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Group;
import com.cmc.recruitment.service.GroupService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class GroupController {
  @Autowired
  GroupService groupService;

  /**
   * @description: "/find-all".
   * @author: nvquy1
   * @created_date: Mar 23, 2018
   * @modifier: User
   * @modifier_date: Mar 23, 2018
   * @param pageable
   * @return
   */
  @GetMapping(ConstantsUrl.Group.ALL_UNLIMIT)
  public ResponseEntity<?> findAll() {
    try {
      List<Group> list = groupService.findAll();
      if (list.size() > 0)
        return new ResponseEntity<List<Group>>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }

}
