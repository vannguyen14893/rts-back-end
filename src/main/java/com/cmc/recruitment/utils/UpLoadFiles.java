package com.cmc.recruitment.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cmc.recruitment.entity.CvUrl;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.service.UserService;

@Component
public class UpLoadFiles {

  @Autowired
  UserService userService;

  private final Logger LOGGER = Logger.getLogger(this.getClass());

  @Value("${path.file.upload.stored}")
  private String parentPath;

  /**
   * @description: upload file: if success return file name, else return the
   *               file name includes the cause of the error.
   * @author: NHPhong.
   * @create_date: Mar 16, 2018.
   * @param listMultipartFile
   *          is an array file upload.
   * @param path
   *          is the directory path upload.
   */
  public LinkedList<String> UploadMultiCv(MultipartFile[] listMultipartFile, String path) {
    // check path. if null create folder
    if (!new File(parentPath + path).exists()) {
      new File(parentPath + path).mkdirs();
    }
    LinkedList<String> listMessage = new LinkedList<String>();
    for (MultipartFile file : listMultipartFile) {
      StringBuffer message = new StringBuffer();
      String messageError = checkFileInput(file, path);
      if (messageError.length() != 0) {
        message.append(file.getOriginalFilename());
        message.append(messageError);
      } else {
        try {
          message.append(saveFile2(file, path, this.createKeyForUpload(file, path)));
        } catch (IOException e) {
          message.append(Constants.Upload.UPLOAD_FALSE);
          LOGGER.error(e.toString());
        }
      }
      listMessage.add(message.toString());
    }
    return listMessage;
  }

  /**
   * 
   * @param file
   * @param path:
   *          is folder public for save Cv, folder static for save avatar of
   *          user.
   * @return một key để việc lưu file cùng tên không bị trùng.
   */
  private String createKeyForUpload(MultipartFile file, String path) {
    StringBuilder key = new StringBuilder();
    int i = 0;
    while ((new File(parentPath + path + key.toString() + file.getOriginalFilename())).exists()) {
      i += 1;
      key.setLength(0);
      key.append(i);
    }
    return key.toString();
  }

  /**
   * @description: use for upload Image.
   * @author NHPhong.
   * @create_date: Mar 21, 2018
   * @param listMultipartFile
   * @param path
   * @return
   */
  public LinkedList<String> UploadMultiImage(MultipartFile[] listMultipartFile, String path) {
    // check path. if null create folder
    if (!new File(parentPath + path).exists()) {
      new File(parentPath + path).mkdirs();
    }
    LinkedList<String> listMessage = new LinkedList<String>();
    for (MultipartFile file : listMultipartFile) {
      StringBuffer message = new StringBuffer();
      String messageError = checkImageInput(file, path);
      if (messageError.length() != 0) {
        message.append(file.getOriginalFilename());
        message.append(messageError);
      } else {
        try {
          message.append(saveFile(file, path, this.createKeyForUpload(file, path))
              ? Constants.Upload.UPLOAD_SUCCESS : Constants.Upload.UPLOAD_FALSE);
        } catch (IOException e) {
          message.append(Constants.Upload.UPLOAD_FALSE);
          LOGGER.error(e);
        }
      }
      listMessage.add(message.toString());
    }
    return listMessage;
  }

  /**
   * @description: using save file.
   * @author: NHPhong.
   * @create_date: Mar 1, 2018.
   * @param multipartFile.
   * @param path
   *          path upload folder off cv or user avatar.
   * @param stringKey
   *          is string unique: user name or mail cv.
   * 
   * @return true if save file success.
   * @throws IOException
   */
  private boolean saveFile(MultipartFile multipartFile, String path, String key)
      throws IOException {
    if (multipartFile == null) {
      return false;
    } else {
      // save file.
      if (key == null)
        key = "";
      File file = new File(parentPath + path + key + multipartFile.getOriginalFilename());
      multipartFile.transferTo(file);
      return file.exists();
    }
  }

  private String saveFile2(MultipartFile multipartFile, String path, String key)
      throws IOException {
    // save file.
    if (key == null)
      key = "";
    File file = new File(parentPath + path + key + multipartFile.getOriginalFilename());
    multipartFile.transferTo(file);
    return file.exists() ? file.getName() + Constants.Upload.UPLOAD_SUCCESS
        : multipartFile.getOriginalFilename() + Constants.Upload.UPLOAD_FALSE;
  }

  /**
   * 
   * @description: file has type of jpg, jpeg, png is true.
   * @author: NHPhong.
   * @create_date: Mar 1, 2018.
   * @param multipartFile.
   * @return
   */
  public boolean filterImage(MultipartFile multipartFile) {
    String[] typerImage = { Constants.Upload.JPG, Constants.Upload.JPEG, Constants.Upload.PNG };
    for (int i = 0; i < typerImage.length; i++) {
      if (typerImage[i].equals(multipartFile.getContentType()))
        return true;
    }
    return false;
  }

  /**
   * 
   * @description: file has type of PDF, DOC, DOCX is true.
   * @author: NHPhong.
   * @create_date: Mar 1, 2018.
   * @param multipartFile.
   * @return
   */
  public boolean filterDoc(MultipartFile multipartFile) {
    String[] typerDoc = { Constants.Upload.PDF, Constants.Upload.DOCX, Constants.Upload.DOC,
        Constants.Upload.XLS, Constants.Upload.XLSX };
    for (int i = 0; i < typerDoc.length; i++) {
      if (typerDoc[i].equals(multipartFile.getContentType()))
        return true;
    }
    return false;
  }

  /**
   * @description:
   * @author: NHPhong.
   * @create_date: Mar 26, 2018
   * @param multipartFile
   * @param path
   * @return If the file violates the request, return the message, otherwise
   *         return null
   */
  private String checkFileInput(MultipartFile multipartFile, String path) {
    StringBuffer message = new StringBuffer();
    if ((multipartFile.getSize() > Constants.Upload.SIZE_DOC))
      message.append(Constants.Upload.ERROR_SIZE_DOC);
    if (!filterDoc(multipartFile))
      message.append(Constants.Upload.IS_NOT_DOC);
    return message.toString();
  }

  /**
   * @author: NHPhong.
   * @param multipartFile
   * @param path
   * @return message: if the file violates the request, return the message,
   *         otherwise return null.
   */
  public String checkImageInput(MultipartFile multipartFile, String path) {
    StringBuffer message = new StringBuffer();
    if ((multipartFile.getSize() > Constants.Upload.SIZE_IMAGE))
      message.append(Constants.Upload.ERROR_SIZE_IMG);
    if (!filterImage(multipartFile))
      message.append(Constants.Upload.IS_NOT_IMAGE);
    return message.toString();
  }

  /**
   * @description:
   * @author: NHPhong.
   * @create_date: Mar 18, 2018
   * @param path
   * @return true if file existed.
   */
  public boolean fileIsExitsInFoderCv(String path) {
    return ((new File(parentPath + Constants.Upload.FODER_UPLOADED_CV + path).exists()) ? true
        : false);
  }

  /**
   * @description: use for check array file exist.
   * @author: NHPhong.
   * @create_date: Mar 20, 2018
   * @param arrFileName:
   *          array file input.
   * @param path.
   * @return
   */
  public List<FileCheckDTO> checkFilesExist(String[] arrFileName, String path) {
    List<FileCheckDTO> listResult = new LinkedList<>();
    for (String strFileName : arrFileName) {
      FileCheckDTO file = new FileCheckDTO();
      file.setFileName(strFileName);
      file.setExist((new File(parentPath + path + strFileName).exists()) ? true : false);
      listResult.add(file);
    }
    return listResult;
  }

  /**
   * 
   * @description: using delete file.
   * @author: NHPhong.
   * @create_date: Mar 1, 2018.
   * @param part:
   *          is the absolute path of the file.
   * @return true if delete success.
   * @throws IOException
   */
  public boolean deleteFileUpload(String path) throws IOException {
    File file = new File(parentPath + path);
    if (file.exists()) {
      return file.delete();
    }
    return false;
  }

  // NHPhong delete array Cv
  public void deleteCvUrl(List<CvUrl> list, String path) throws IOException {
    for (CvUrl urlCv : list) {
      if (urlCv.getCvId() == null)
        deleteFileUpload(parentPath + path + urlCv.getUrl());
    }
  }

  // NHPhong. function use for change avatar.
  public String UploadAvatar(MultipartFile image, String path) throws IOException {
    // check path. if null create folder
    if (!new File(parentPath + path).exists()) {
      new File(parentPath + path).mkdirs();
    }

    StringBuffer message = new StringBuffer();
    message.append(image.getOriginalFilename());
    String messageError = checkImageInput(image, path);
    // if messageError's not null => return message
    if (messageError.length() > 0) {
      message.append(messageError);
    } else {
      // if message not null, file accept, save file.
      User user = new User();
      List<User> users = userService.getUserInfoByUserName();
      user = users == null ? null : users.get(0);
      // delete file by file name and avatar url of currently login user
      if (user.getAvatarUrl() != null)
        deleteFileUpload(path + user.getAvatarUrl());
      if (this.saveFile(image, path, user.getUsername() + "_")) {
        user.setAvatarUrl(user.getUsername() + "_" + image.getOriginalFilename());
        // update for user. if false delete file.
        if (userService.saveUser(user) == null) {
          message.append(" can not update avatar, delete file upload.");
          deleteFileUpload(path + user.getAvatarUrl());
        }
      } else {
        message.append(Constants.Upload.UPLOAD_FALSE);
      }
    }
    return message.toString();
  }

}
