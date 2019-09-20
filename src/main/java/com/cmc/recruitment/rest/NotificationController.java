package com.cmc.recruitment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cmc.recruitment.entity.Notification;
import com.cmc.recruitment.service.NotificationService;
import com.cmc.recruitment.utils.Constants;

@Controller
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	/**
	 * @author duongquy
	 * @description get all notification
	 * @return list
	 */
	@GetMapping("/notifications")
	public ResponseEntity<?> findAll() {
	    List<Notification> list;
	    try {
	      list = notificationService.findAll();
	    } catch (NumberFormatException e) {
	      return new ResponseEntity<>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
	    } catch (Exception e) {
	      return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
	          HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return new ResponseEntity<List<Notification>>(list, HttpStatus.OK);
	  }

	/**
	 * @author duongquy
	 * @description get all notification by receiver
	 * @return list notification of receiver
	 */
	@GetMapping("/notifications/{userId}")
	public ResponseEntity<?> findByUserName(@PathVariable long userId) {
		List<Notification> list;
		try {
			list = notificationService.findByReceiver(userId);
		} catch (Exception e) {
			return new ResponseEntity<String>(Constants.RESPONSE.NOT_RETRIVE_DATA,
			          HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Notification>>(list, HttpStatus.OK);
	}
}
