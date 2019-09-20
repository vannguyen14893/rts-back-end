/**
 * 
 */
package com.cmc.recruitment.rest;

import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.RequestAssignee;
import com.cmc.recruitment.service.RequestAssigneeService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;
import com.cmc.recruitment.utils.EmailHelper;

/**
 * @description: .
 * @author: VDHoan
 * @created_date: Apr 23, 2018
 * @modifier: User
 * @modifier_date: Apr 23, 2018
 */
@RestController
public class RequestAssigneeController {
  private static final Logger LOG = LoggerFactory.getLogger(RequestAssigneeController.class);

  @Autowired
  private RequestAssigneeService requestAssigneeService;
  @Autowired
  private EmailHelper emailHelper;

  @RequestMapping(ConstantsUrl.RequestAssignee.CHANGE_TARGET)
  public ResponseEntity<?> changeTarget(@RequestBody @Validated RequestAssignee requestAssignee) {
    try {
      RequestAssignee savedRequestAssignee = requestAssigneeService.changeTarget(requestAssignee);
      if (savedRequestAssignee != null) {
        StringBuilder text = new StringBuilder();
        text.append("Dear " + savedRequestAssignee.getAssignee().getFullName());
        text.append(" We have been changed your target of request "
            + savedRequestAssignee.getRequest().getTitle() + ", new your target is: ");
        text.append(savedRequestAssignee.getNumberOfCandidate());
        // new thread to send email
        Executors.newSingleThreadExecutor().execute(new Runnable() {
          public void run() {
            try {
              emailHelper.sendMessage(Constants.APPLICATION_EMAIL,
                  savedRequestAssignee.getAssignee().getEmail(), "Your target has been changed",
                  text.toString());
            } catch (MessagingException e) {
              LOG.error("send email in requestAssigneeService error: " + e);
            }
          }
        });
        return new ResponseEntity<RequestAssignee>(requestAssignee, HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER,
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_CAN_NOT_SAVE,
        HttpStatus.BAD_GATEWAY);
  }
}
