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

import com.cmc.recruitment.entity.Department;
import com.cmc.recruitment.service.DepartmentService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class DepartmentController {

  @Autowired
  DepartmentService departmentService;

  /**
   * 
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @GetMapping(ConstantsUrl.Department.ALL_UNLIMIT)
  public ResponseEntity<?> getListDepartments() {
    try {
      return new ResponseEntity<List<Department>>(departmentService.findAll(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @description: /find-all with pageable.
   * @author: VDHoan
   * @created_date: Mar 20, 2018
   * @modifier: User
   * @modifier_date: Mar 20, 2018
   * @param pageable
   * @return
   */
  @GetMapping(ConstantsUrl.Department.ALL)
  public ResponseEntity<?> getAllDepartment(Pageable pageable) {
    Page<Department> list;
    try {
      list = departmentService.findAll(pageable);
      if (list.getSize() > 0)
        return new ResponseEntity<Page<Department>>(list, HttpStatus.OK);
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
  @GetMapping(ConstantsUrl.Department.FIND_BY_TITLE)
  public ResponseEntity<?> getOneDepartment(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
    try {
      Department department = departmentService.findByTitle(title);
      if (department != null)
        return new ResponseEntity<Department>(department, HttpStatus.OK);
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
   * @param department
   * @param result
   * @return
   */
  @PostMapping(ConstantsUrl.Department.SAVE)
  public ResponseEntity<?> createOrUpdate(@RequestBody @Validated Department department, BindingResult result) {
    try {
      if(result.hasErrors()) {
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
      }
      department.setIsDelete(false);
      Department savedDepartment = departmentService.createOrUpdate(department);
      if (savedDepartment != null)
        return new ResponseEntity<Department>(savedDepartment, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
  }
}
