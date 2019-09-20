package com.cmc.recruitment.exception;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cmc.recruitment.utils.Constants;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  public CustomResponseEntityExceptionHandler() {
    super();
  }

  // 413 MultipartException - file size too big > max size in config.
  @ExceptionHandler({ MultipartException.class, SizeLimitExceededException.class,
      IllegalStateException.class })
  public ResponseEntity<String> handleSizeExceededException() {
    return new ResponseEntity<>(Constants.Upload.ERROR_SIZE_CONFIG, HttpStatus.PAYLOAD_TOO_LARGE);
  }

}
