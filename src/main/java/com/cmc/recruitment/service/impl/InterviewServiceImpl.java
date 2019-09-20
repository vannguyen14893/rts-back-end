/**
 * 
 */
package com.cmc.recruitment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Interview;
import com.cmc.recruitment.entity.User;
import com.cmc.recruitment.repository.CandidateRepository;
import com.cmc.recruitment.repository.CandidateStatusRepository;
import com.cmc.recruitment.repository.InterviewRepository;
import com.cmc.recruitment.service.InterviewService;
import com.cmc.recruitment.specification.InterviewSpecification;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.EmailHelper;

/**
 * @description: .
 * @author: VDHoan
 * @created_date: Mar 12, 2018
 * @modifier: User
 * @modifier_date: Mar 12, 2018
 */
@Service
public class InterviewServiceImpl implements InterviewService {
  @Autowired
  InterviewRepository interviewRepository;

  @Autowired
  CandidateStatusRepository candidateStatusRepository;

  @Autowired
  CandidateRepository candidateRepository;

  @Autowired
  EmailHelper emailHelper;

  /**
   * @description: .
   * @author: vthung
   * @created_date: Mar 12, 2018
   * @modifier: User
   * @modifier_date: Mar 12, 2018
   * @param departmentId
   * @param interviewerId
   * @param requestId
   * @param interviewStatusId
   * @param fromDate
   * @param toDate
   * @param pageable
   * @return
   */
  @Override
  public Page<Interview> filterInterview(Long departmentId, Long requestId, Long interviewerId,
      Long interviewStatusId, String fromDate, String toDate, String candidateName,
      Long candidateId, String title, Pageable pageable) {
    return interviewRepository.findAll(new InterviewSpecification(departmentId, interviewerId,
        requestId, interviewStatusId, fromDate, toDate, candidateName, candidateId, title),
        pageable);
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date:
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public Interview findOne(Long interviewId) {
    // TODO Auto-generated method stub
    return interviewRepository.findOne(interviewId);
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date: 15/3/2018
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public Interview save(Interview interview) {
    updateCandidateStatusToInterviewAndSendEmail(interview, interview.getCandidateCollection());
    return interviewRepository.save(interview);
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date: 15/3/2018
   * @modifer:
   * @modifer_date: 20/3/2018
   * @param
   * @return a predicate include query clauses satisfy request
   */
  @Override
  public Interview update(Interview interview) {
    List<Candidate> listNewCandidate = interview.getCandidateCollection();

    List<Candidate> listOldCandidate = interviewRepository.findOne(interview.getId())
        .getCandidateCollection();

    listOldCandidate.removeAll(listNewCandidate);
    updateCandidateStatusToClosedAndSendEmail(interview, listOldCandidate);
    updateCandidateStatusToInterviewAndSendEmail(interview, listNewCandidate);
    return interviewRepository.save(interview);

  }

  /**
   * @description: Xét trong cuộc phỏng vấn mới các user trong cuộc pv đó có bị
   *               trùng về mặt thời gian so với các cuộc phỏng vấn khác đã có
   *               của user đó không. Dùng được cho cả khi create và update
   *               interview.
   * @author: nvquy1
   * @create_date: 20/3/2018
   * @modifer:
   * @modifer_date: 3/4/2018
   * @param
   * @return Danh sách tên các user không thể tham gia (ko hợp lệ) khi tạo/sửa
   *         cuộc pv này. Do đã bị trùng giờ với cuộc pv khác của họ.
   */

  @Override
  public List<Long> validateTimeForInterviewer(Interview interview) {

    // Khởi tạo một list chứa tên các User không thỏa mãn.
    List<Long> listIdOfInvalidUser = new ArrayList<>();

    Date startTimeExpect = interview.getStartTime();

    Date endTimeExpect = interview.getEndTime();

    /*
     * Kiểm tra từng User xem có hợp lệ không.
     */
    for (User user : interview.getUserCollection()) {

      // Lấy các cuộc phỏng vấn đã có của user đang xét.
      List<Interview> listInterviewOfUser = interviewRepository
          .findInterviewByInterviewer(user.getId());
      /*
       * Duyệt từng cuộc phỏng vấn (interview) của user đang xét. So sánh với
       * cuộc phỏng vấn mới xem có bị trùng về mặt thời gian không.
       */
      for (Interview interviewSample : listInterviewOfUser) {

        /*
         * Trong trường hợp người dùng *UPDATE* cuộc pv. Trong list
         * interviewSample *CÓ THỂ* sẽ có 1 interview trùng id với interview mới
         * (Tức là cái cũ và chính nó sau khi sửa đổi một vài thuộc tính lúc
         * người dùng update). Khi update thì cái cũ sẽ trở thành cái mới =>
         * Không phải xét 2 cái có trùng lặp không, vì nó là 1 => mình sẽ cho
         * pass luôn xét thằng interview tiếp theo, không xét thằng interview
         * này nữa.
         * 
         */
        if (interviewSample.getId().equals(interview.getId())) {
          continue;
        }
        /*
         * Kiểm tra startTime của cuộc pv mới có thuộc khoảng [start, end) của
         * cuộc pv đang xét không. Nếu có => User này không thỏa mãn => thêm tên
         * của user vào list tên => break khỏi vòng lặp để xét user khác.
         */
        if (startTimeExpect.equals(interviewSample.getStartTime()) || isBetween(startTimeExpect,
            interviewSample.getStartTime(), interviewSample.getEndTime())) {
          listIdOfInvalidUser.add(user.getId());
          break;
        }
        /*
         * Kiểm tra endTime của cuộc pv mới có thuộc khoảng (start, end) của
         * cuộc pv đang xét không. Nếu có => User này không thỏa mãn => thêm tên
         * của user vào list tên => break khỏi vòng lặp để xét user khác.
         */
        if (isBetween(endTimeExpect, interviewSample.getStartTime(),
            interviewSample.getEndTime())) {
          listIdOfInvalidUser.add(user.getId());
          break;
        }
        /*
         * Kiểm tra startTime của cuộc pv đang xét có thuộc khoảng (startTime,
         * endTime) của cuộc pv mới. Nếu có => User này không thỏa mãn => thêm
         * tên của user vào list tên => break khỏi vòng lặp để xét user khác.
         */
        if (isBetween(interviewSample.getStartTime(), startTimeExpect, endTimeExpect)) {
          listIdOfInvalidUser.add(user.getId());
          break;
        }
      }
    }

    return listIdOfInvalidUser;
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date: 20/3/2018
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  private boolean isBetween(Date checkingDate, Date startDate, Date endDate) {
    if (checkingDate.after(startDate) && checkingDate.before(endDate)) {
      return true;
    }
    return false;
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date: 20/3/2018
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  private void updateCandidateStatusToClosedAndSendEmail(Interview interview, List<Candidate> listCandidate) {
    for (Candidate candidate : listCandidate) {
      candidate.setStatusId(candidateStatusRepository.findByTitle(Constants.CLOSED));
      candidateRepository.save(candidate);
      Executors.newSingleThreadExecutor().execute(new Runnable() {
        public void run() {
          emailHelper.sendSimpleMessage(Constants.APPLICATION_EMAIL, candidate.getCvId().getEmail(),
              Constants.RESPONSE.SUBJECT_INTERVIEW, Constants.RESPONSE.EMAIL_CANCEL_DETAIL);
        }
      });
    }
  }

  /**
   * @description:
   * @author: nvquy1
   * @create_date: 20/3/2018
   * @modifer:
   * @modifer_date:
   * @param
   * @return a predicate include query clauses satisfy request
   */
  private void updateCandidateStatusToInterviewAndSendEmail(Interview interview, List<Candidate> listCandidate) {
    for (Candidate candidate : listCandidate) {
      candidate.setStatusId(candidateStatusRepository.findByTitle(Constants.INTERVIEW));
      candidateRepository.save(candidate);
      Executors.newSingleThreadExecutor().execute(new Runnable() {
        public void run() {
          String text = Constants.REPLY_EMAIL.INTERVIEWER_CONTENT1 + interview.getStartTime() +" to " +interview.getEndTime() + Constants.REPLY_EMAIL.INTERVIEWER_CONTENT2 +interview.getLocation() +Constants.REPLY_EMAIL.INTERVIEWER_CONTENT4;

          emailHelper.sendSimpleMessage(Constants.APPLICATION_EMAIL, candidate.getCvId().getEmail(),
              Constants.RESPONSE.SUBJECT_INTERVIEW, text);
        }
      });
    }
  }

  @Override
  public Page<Interview> findInterviewByCandidate(Long candidateId, Pageable pageable) {
    return interviewRepository.findInterviewByCandidate(candidateId, pageable);
  }
  
  @Override
  public List<User> validateTimeForInterviewerReturnUser(Interview interview) {
    // Khởi tạo một list chứa tên các User không thỏa mãn.
    List<User> listInvalidUser = new ArrayList<User>();

    Date startTimeExpect = interview.getStartTime();

    Date endTimeExpect = interview.getEndTime();

    /*
     * Kiểm tra từng User xem có hợp lệ không.
     */
    for (User user : interview.getUserCollection()) {

      // Lấy các cuộc phỏng vấn đã có của user đang xét.
      List<Interview> listInterviewOfUser = interviewRepository
          .findInterviewByInterviewer(user.getId());
      /*
       * Duyệt từng cuộc phỏng vấn (interview) của user đang xét. So sánh với cuộc
       * phỏng vấn mới xem có bị trùng về mặt thời gian không.
       */
      for (Interview interviewSample : listInterviewOfUser) {

        /*
         * Trong trường hợp người dùng *UPDATE* cuộc pv. Trong list interviewSample *CÓ
         * THỂ* sẽ có 1 interview trùng id với interview mới (Tức là cái cũ và chính nó
         * sau khi sửa đổi một vài thuộc tính lúc người dùng update). Khi update thì cái
         * cũ sẽ trở thành cái mới => Không phải xét 2 cái có trùng lặp không, vì nó là
         * 1 => mình sẽ cho pass luôn xét thằng interview tiếp theo, không xét thằng
         * interview này nữa.
         * 
         */
        if (interviewSample.getId().equals(interview.getId())) {
          continue;
        }
        /*
         * Kiểm tra startTime của cuộc pv mới có thuộc khoảng [start, end) của cuộc pv
         * đang xét không. Nếu có => User này không thỏa mãn => thêm tên của user vào
         * list tên => break khỏi vòng lặp để xét user khác.
         */
        if (startTimeExpect.equals(interviewSample.getStartTime()) || isBetween(startTimeExpect,
            interviewSample.getStartTime(), interviewSample.getEndTime())) {
          listInvalidUser.add(user);
          break;
        }
        /*
         * Kiểm tra endTime của cuộc pv mới có thuộc khoảng (start, end) của cuộc pv
         * đang xét không. Nếu có => User này không thỏa mãn => thêm tên của user vào
         * list tên => break khỏi vòng lặp để xét user khác.
         */
        if (isBetween(endTimeExpect, interviewSample.getStartTime(),
            interviewSample.getEndTime())) {
          listInvalidUser.add(user);
          break;
        }
        /*
         * Kiểm tra startTime của cuộc pv đang xét có thuộc khoảng (startTime, endTime)
         * của cuộc pv mới. Nếu có => User này không thỏa mãn => thêm tên của user vào
         * list tên => break khỏi vòng lặp để xét user khác.
         */
        if (isBetween(interviewSample.getStartTime(), startTimeExpect, endTimeExpect)) {
          listInvalidUser.add(user);
          break;
        }
      }
    }
    return listInvalidUser;
  }

  @Override
  public Interview addCandidate(List<Candidate> candidates, Long interviewId) {
    Interview interview = interviewRepository.findOne(interviewId);
    List<Candidate> list = interview.getCandidateCollection();
    for (Candidate candidate : candidates) {
      list.add(candidate);
    }
    interview.setCandidateCollection(list);
    return interview;
  }
  @Override
	public int sendMailAddinterviewer(String url, String[] to, String startTime, String endTime, String location)
			throws MessagingException {
		String text = Constants.REPLY_EMAIL.INTERVIEWER_CONTENT1 + startTime +" to " +endTime + Constants.REPLY_EMAIL.INTERVIEWER_CONTENT2 +location +Constants.REPLY_EMAIL.INTERVIEWER_CONTENT3 +url +Constants.REPLY_EMAIL.INTERVIEWER_CONTENT4;
		Executors.newSingleThreadExecutor().execute(new Runnable() {
		      public void run() {
		        try {
		          emailHelper.sendMessage(Constants.APPLICATION_EMAIL, to,
		              Constants.REPLY_EMAIL.INTERVIEWER_SUBJECT, text);
		        } catch (MessagingException e) {
		          e.printStackTrace();
		        }
		      }
		    });
		return Constants.User.VALID;
	}

@Override
public int sendMeetingRequest(String from, String[] to, String subject, String start, String end,
		String location, String url) {
	String text = Constants.REPLY_EMAIL.INTERVIEWER_CONTENT1 + start +" to " +end+ Constants.REPLY_EMAIL.INTERVIEWER_CONTENT2 +location +Constants.REPLY_EMAIL.INTERVIEWER_CONTENT3 +url +Constants.REPLY_EMAIL.INTERVIEWER_CONTENT4;
	Executors.newSingleThreadExecutor().execute(new Runnable() {
	      public void run() {
	        try {
	          emailHelper.sendMeeting(Constants.APPLICATION_EMAIL, to, subject, text, start, end, location);
	        } catch (MessagingException e) {
	          e.printStackTrace();
	        }
	      }
	    });
	return Constants.User.VALID;
}

}
