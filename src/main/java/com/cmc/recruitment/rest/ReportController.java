package com.cmc.recruitment.rest;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cmc.recruitment.entity.Candidate;
import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.service.CandidateService;
import com.cmc.recruitment.service.CandidateStatusService;
import com.cmc.recruitment.service.RequestService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;
//import com.cmc.recruitment.utils.ReportXlsxStreaming;
import com.cmc.recruitment.utils.ExcelView;
import com.cmc.recruitment.utils.ExcelViewAM;
import com.cmc.recruitment.utils.ExcelViewDuReport;
import com.cmc.recruitment.utils.ReportByGroup;

@Controller
public class ReportController {

  @Autowired
  RequestService requestService;

  @Autowired
  CandidateService candidateService;

  @Autowired
  private CandidateStatusService candidateStatusService;

  @PreAuthorize("hasAnyRole('ROLE_GROUP_LEAD','ROLE_HR_MANAGER','ROLE_HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.DOWNLOAD)
  public ModelAndView download(HttpServletRequest req, HttpServletResponse res,
      @RequestParam(required = false, value = "toDate") String toDate,
      @RequestParam(required = false, value = "fromDate") String fromDate,
      Pageable pageable) {
    List<Request> requests = requestService.getReportRP(fromDate, toDate, pageable).getContent();
    return new ModelAndView(new ExcelView(), "requests", requests);
  }

  @PreAuthorize("hasAnyRole('ROLE_GROUP_LEAD','ROLE_HR_MANAGER','ROLE_HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.DOWNLOAD_AM)
  public ModelAndView downloadAmReport(HttpServletRequest req, HttpServletResponse res,
      @RequestParam(required = false, value = "hrmemberId") Long hrmemberId,
      @RequestParam(required = false, value = "fromDate") String fromDate,
      @RequestParam(required = false, value = "toDate") String toDate,
      Pageable pageable) {
    List<Candidate> candidates;
    candidates = candidateService.getReportAM(hrmemberId, fromDate, toDate,
        candidateStatusService.findByTitle(Constants.ONBOARD).getId(), pageable).getContent();
    return new ModelAndView(new ExcelViewAM(), "candidates", candidates);
  }
  
  @PreAuthorize("hasAnyRole('ROLE_GROUP_LEAD','ROLE_HR_MANAGER','ROLE_HR_MEMBER')")
  @GetMapping(ConstantsUrl.Request.DOWNLOAD_DU)
  public ModelAndView downloadDuReport(HttpServletRequest req, HttpServletResponse res, @RequestParam(value = Constants.PARAM.YEAR, required = false) Long year) {
    if(year==null) {
      int currentYear = Calendar.getInstance().get(Calendar.YEAR);
      year = Long.parseLong(currentYear+"");
    }
    List<ReportByGroup> reportList = candidateService.statisticCandidateByGroup(year);
    return new ModelAndView(new ExcelViewDuReport(year), "reportList", reportList);
//    return new ModelAndView(new ExcelViewDuReport());
  }
}
