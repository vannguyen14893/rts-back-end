package com.cmc.recruitment.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.cmc.recruitment.entity.AccountGlobal;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.Skill;
import com.cmc.recruitment.service.AccountGlobalService;
import com.cmc.recruitment.service.ExchangeApiService;
import com.cmc.recruitment.service.RequestService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.PostContentGlobal;
import com.cmc.recruitment.utils.PublishResult;
import com.cmc.recruitment.utils.TokenGlobal;

@Service
public class ExchangeApiServiceImpl implements ExchangeApiService {

  @Autowired
  protected RestTemplate restTemplate;
  @Autowired
  private AccountGlobalService accountGlobalService;
  @Autowired
  private RequestService requestService;

  // Login vao recruit, neu thanh cong thi tra ra chuoi json, con khong thi lay
  // token da dc cap phat
  @Override
  public String loginRecruit() throws IOException {
    AccountGlobal accountGlobal = new AccountGlobal();
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

      accountGlobal = accountGlobalService.findOne(1L);
      HttpEntity<Object> requestBody = new HttpEntity<>(
          new AccountGlobal(accountGlobal.getName(), accountGlobal.getPass()), headers);
      // Gửi yêu cầu với phương thức POST.
      TokenGlobal tokenGlobal = restTemplate.postForObject(
          accountGlobal.getBaseUrl() + Constants.EXCHANGE_API.LOGIN_RECRUIT, requestBody,
          TokenGlobal.class);
      // save new tokens
      accountGlobalService.addAccountGlobal(new AccountGlobal(accountGlobal.getId(),
          accountGlobal.getName(), accountGlobal.getPass(), tokenGlobal.getCsrf_token(),
          tokenGlobal.getLogout_token(), accountGlobal.getBaseUrl()));
      return tokenGlobal.getCsrf_token();
    } catch (HttpStatusCodeException e) {
      return accountGlobal.getCsrfToken();
    } catch (Exception e) {
      throw new IOException("Server error");
    }
  }

  @Override
  public boolean logoutRecruit() throws Exception {
    AccountGlobal accountGlobal = new AccountGlobal();
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      accountGlobal = accountGlobalService.findOne(1L);
      headers.set("logout_token", accountGlobal.getLogoutToken());
      headers.set("x-csrf-token", accountGlobal.getCsrfToken());
      HttpEntity<Object> requestBody = new HttpEntity<>(null, headers);
      // Gửi yêu cầu với phương thức POST.
      restTemplate.getForObject(accountGlobal.getBaseUrl() + Constants.EXCHANGE_API.LOGOUT_RECRUIT,
          TokenGlobal.class, requestBody);
      accountGlobalService.addAccountGlobal(new AccountGlobal(accountGlobal.getId(),
          accountGlobal.getName(), accountGlobal.getPass(), accountGlobal.getBaseUrl()));
      return Boolean.TRUE;
    } catch (HttpStatusCodeException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public boolean publishRecruitContent(Long requestId) throws Exception {
    try {
      AccountGlobal accountGlobal = accountGlobalService.findOne(1L);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.set("x-csrf-token", accountGlobal.getCsrfToken());

      Request request = requestService.findOne(requestId);
      String title = request.getTitle();
      String positon = request.getPositionId().getTitle();
      int number = request.getNumber();
      String deadline = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
          .format(request.getDeadline());
      Set<Skill> skills = request.getSkillCollection();
      StringBuilder listSkill = new StringBuilder();
      for (Skill s : skills) {
        listSkill.append(s.getTitle());
        listSkill.append(", ");
      }
      String experience = request.getExperienceId().getTitle();
      String description = request.getDescription();
      String salary = request.getSalary();
      StringBuilder sb = new StringBuilder();
      sb.append(
          "<table class=\"table table-bordered table-responsive\"><tbody><tr><td><p>&nbsp;\r\n"
              + "           <strong>Title</strong></p></td><td colspan=\"5\"><p>" + title + "\r\n"
              + "         </p></td></tr><tr><td><p>&nbsp;\r\n"
              + "           <strong>Position</strong></p></td><td colspan=\"5\"><p>" + positon
              + "\r\n" + "          </p></td></tr><tr><td><p>&nbsp;\r\n"
              + "           <strong>Number</strong></p></td><td colspan=\"5\"><p>" + number + "\r\n"
              + "         </p></td></tr><tr><td><p>&nbsp;\r\n"
              + "           <strong>Deadline</strong></p></td><td colspan=\"5\"><p>" + deadline
              + "\r\n" + "          </p></td></tr><tr><td><p>&nbsp;\r\n"
              + "           <strong>Skills</strong></p></td><td colspan=\"5\"><p>"
              + listSkill.toString() + "\r\n" + "         </p></td></tr><tr><td><p>&nbsp;\r\n"
              + "           <strong>Experience</strong></p></td><td colspan=\"5\"><p>" + experience
              + "\r\n" + "          </p></td></tr><tr><td><p>&nbsp;\r\n"
              + "           <strong>Description</strong></p></td><td colspan=\"5\"><p>-&nbsp;&nbsp; &nbsp;"
              + description + "\r\n" + "            <br> &nbsp;\r\n"
              + "         </p></td></tr><tr><td><p>&nbsp;\r\n"
              + "           <strong>Salary</strong></p></td><td colspan=\"5\"><p>" + salary + "\r\n"
              + "         </p></td></tr><tr><td colspan=\"6\"><p><strong>&nbsp;Contact details</strong></p></td></tr><tr><td><p><strong>&nbsp;Attention</strong></p></td><td colspan=\"5\"><p><span style=\"\">HR Dept. of CMC Global</span></p></td></tr><tr><td><p><strong>&nbsp;Address</strong></p></td><td colspan=\"5\"><p><b><span style=\"font-size:12.0pt;line-height:115%;\r\n"
              + "font-family:&quot;Times New Roman&quot;,serif;mso-fareast-font-family:Calibri;color:#0070C0;\r\n"
              + "mso-ansi-language:EN-US;mso-fareast-language:EN-US;mso-bidi-language:AR-SA;\r\n"
              + "mso-no-proof:yes\">CMC Global, 8 </span></b><b style=\"mso-bidi-font-weight:normal\"><span style=\"font-size:12.0pt;line-height:115%;font-family:&quot;Times New Roman&quot;,serif;\r\n"
              + "mso-fareast-font-family:Calibri;color:#0070C0;mso-ansi-language:EN-US;\r\n"
              + "mso-fareast-language:EN-US;mso-bidi-language:AR-SA;mso-no-proof:yes\">Floor, CMC Tower, 11 Duy Tan, Cau Giay, Hanoi</span></b></p></td></tr><tr><td><p><strong>&nbsp;Email</strong></p></td><td colspan=\"5\"><p><span style=\"\"><a href=\"mailto:globalhr@cmc.com.vn\"><span style=\"\">globalhr@cmc.com.vn</span></a></span></p></td></tr><tr><td><p><strong>&nbsp;Skype</strong></p></td><td colspan=\"5\"><p>\r\n"
              + "           Thiều Thị Phương Thảo - thieuphuongthao\r\n"
              + "           <br>Ngô Thị Anh - ntlananh86\r\n"
              + "           <br>Phạm Thị Ngọc Anh - ngoc.anh0211\r\n"
              + "           <br>Vũ Thị Hồng Nhung - thivu28101991\r\n"
              + "         </p></td></tr></tbody></table>");

      String content = sb.toString();
      byte[] authBytes = content.getBytes(StandardCharsets.UTF_8);
      String encoded = Base64.getEncoder().encodeToString(authBytes);

      String field_email = "globalhr@cmc.com.vn";
      Date recruit_date_public = new Date();
      String field_recruit_date_public = recruit_date_public.toString();
      String field_rcid = request.getRequestCode();
      PostContentGlobal postContent = new PostContentGlobal(title, encoded, field_email,
          field_recruit_date_public, number, field_rcid);

      HttpEntity<PostContentGlobal> requestBody = new HttpEntity<>(postContent, headers);
      // Gửi yêu cầu với phương thức POST.
      PublishResult publishResult = restTemplate.postForObject(
          accountGlobal.getBaseUrl() + Constants.EXCHANGE_API.PUBLISH_RECRUIT_CONTENT, requestBody,
          PublishResult.class);
      return publishResult.isStatus();
    } catch (Exception e) {
      throw e;
    }
  }

}
