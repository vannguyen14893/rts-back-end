package com.cmc.recruitment.rest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;
import com.cmc.recruitment.utils.FileCheckDTO;
import com.cmc.recruitment.utils.UpLoadFiles;

@RestController
public class UploadController {
  private final Logger LOGGER = Logger.getLogger(this.getClass());

  @Autowired
  UpLoadFiles uploadFiles;

  // NHPhong: use for check files upload exist.
  @PostMapping(ConstantsUrl.Upload.CHECK)
  public ResponseEntity<?> checkFileExist(@RequestBody String[] arrFileName) {
    if (arrFileName == null || arrFileName.length == 0)
      return new ResponseEntity<>(Constants.RESPONSE.NO_INPUT, HttpStatus.BAD_REQUEST);
    List<FileCheckDTO> listResult = uploadFiles.checkFilesExist(arrFileName,
        Constants.Upload.FODER_UPLOADED_CV);
    return (listResult.size() > 0)
        ? new ResponseEntity<List<FileCheckDTO>>(listResult, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // NHPhong: use for upload file type pdf, docx, doc, .xls, xlsx
  @PostMapping(ConstantsUrl.Upload.DOC)
  public ResponseEntity<?> FullUpLoad(
      @RequestParam(value = "doc", required = false) MultipartFile[] doc) {
    if (doc == null)
      return new ResponseEntity<>(Constants.RESPONSE.NO_INPUT, HttpStatus.BAD_REQUEST);
    LinkedList<String> listResult = uploadFiles.UploadMultiCv(doc,
        Constants.Upload.FODER_UPLOADED_CV);
    return (listResult.size() > 0) ? new ResponseEntity<>(listResult, HttpStatus.OK)
        : new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // NHPhong: use for upload file type JPG, JPEG, PNG.
  @PostMapping(ConstantsUrl.Upload.IMAGE)
  public ResponseEntity<?> upImage(
      @RequestParam(value = "image", required = false) MultipartFile[] image) {
    if (image == null) {
      return new ResponseEntity<String>(Constants.RESPONSE.NO_INPUT, HttpStatus.BAD_REQUEST);
    }
    LinkedList<String> listResult = uploadFiles.UploadMultiImage(image,
        Constants.Upload.FODER_UPLOADED_CV);
    return (listResult.size() > 0) ? new ResponseEntity<>(listResult, HttpStatus.OK)
        : new ResponseEntity<>(Constants.RESPONSE.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // NHPhong: use for upload delete file.
  @PostMapping(ConstantsUrl.Upload.DELETE)
  public ResponseEntity<?> deleteFileUpload(@RequestParam String fileNameToDelete) {
    try {
      uploadFiles.deleteFileUpload(Constants.Upload.FODER_UPLOADED_CV + fileNameToDelete);
      return new ResponseEntity<>(Constants.RESPONSE.SUCCESS_MESSAGE, HttpStatus.OK);
    } catch (IOException e) {
      LOGGER.error("Controler delete file: " + e.toString());
      return null;
    }
  }
}
