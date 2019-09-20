package com.cmc.recruitment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.AccountGlobal;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.AccountGlobalService;
import com.cmc.recruitment.utils.Constants;

@RestController
public class AccountGlobalController {

  @Autowired
  AccountGlobalService accountGlobalService;

  /**
   * @author nvquy1
   * @param
   * @return
   */
  @PostMapping("/accountGlobal/add")
  public ResponseEntity<?> addAccountGlobal(@RequestBody AccountGlobal account,
      BindingResult result) {
    try {
      if (result.hasErrors())
        return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT,
            HttpStatus.NOT_ACCEPTABLE);
      account.setId((long) 1);
      account = accountGlobalService.addAccountGlobal(account);
      if (account == null)
        return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
      return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  /**
   * @author nvquy1
   * @param
   * @return
   */
  @GetMapping("/accountGlobal/delete")
  public ResponseEntity<?> deleteAccountGlobal(
      @RequestParam(required = false, value = "id") Integer id) {
    try {
      accountGlobalService.deleteAccountGlobal(id);
      return new ResponseEntity<String>(Constants.RESPONSE.SUCCESS_CODE, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * @author vdhoan
   * @param
   * @return
   */
  @GetMapping("/accountGlobal")
  public ResponseEntity<?> findAccountGlobal(
      @RequestParam(required = false, value = "id") Integer id) {
    try {
      AccountGlobal ag = accountGlobalService.findOne(1L);
      if (ag == null)
        return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA, HttpStatus.OK);

      return new ResponseEntity<AccountGlobal>(ag, HttpStatus.OK);

    } catch (Exception e) {
      return new ResponseEntity<BaseResponse>(
          new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR, Constants.RESPONSE.EXITS_CODE,
              Constants.RESPONSE.NOT_EXIST),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
