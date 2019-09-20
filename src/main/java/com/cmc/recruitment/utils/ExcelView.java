//package com.cmc.recruitment.utils;

package com.cmc.recruitment.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.cmc.recruitment.entity.Request;
import com.cmc.recruitment.entity.RequestAssignee;

public class ExcelView extends AbstractXlsxView {

  @Override
  protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

    // change the file name
    response.setHeader("Content-Disposition", "attachment; filename=\"Recruitment Pineline.xls\"");

    @SuppressWarnings("unchecked")
    List<Request> requests = (List<Request>) model.get("requests");

    // create excel xls sheet
    Sheet sheet = workbook.createSheet("Request Detail");
    // sheet.setDefaultColumnWidth(12);

    // create style for header cells
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setFontName("Arial");
    style.setFillForegroundColor(HSSFColor.BLUE.index);
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    font.setBold(true);
    font.setColor(HSSFColor.WHITE.index);
    style.setFont(font);

    // create header row
    Row header = sheet.createRow(0);

    header.createCell(0).setCellValue("STT");
    header.getCell(0).setCellStyle(style);
    header.createCell(1).setCellValue("Mã tuyển dụng");
    header.getCell(1).setCellStyle(style);
    header.createCell(2).setCellValue("Dự án");
    header.getCell(2).setCellStyle(style);
    header.createCell(3).setCellValue("Vị trí");
    header.getCell(3).setCellStyle(style);
    header.createCell(4).setCellValue("Loại");
    header.getCell(4).setCellStyle(style);
    header.createCell(5).setCellValue("Ngày nhận order");
    header.getCell(5).setCellStyle(style);
    header.createCell(6).setCellValue("Deadline");
    header.getCell(6).setCellStyle(style);
    header.createCell(7).setCellValue("số nhận order");
    header.getCell(7).setCellStyle(style);
    header.createCell(8).setCellValue("Phụ trách");
    header.getCell(8).setCellStyle(style);
    header.createCell(9).setCellValue("Số lượng tuyển");
    header.getCell(9).setCellStyle(style);
    header.createCell(10).setCellValue("Applied");
    header.getCell(10).setCellStyle(style);
    header.createCell(11).setCellValue("Contacting");
    header.getCell(11).setCellStyle(style);
    header.createCell(12).setCellValue("Interview");
    header.getCell(12).setCellStyle(style);
    header.createCell(13).setCellValue("Offer");
    header.getCell(13).setCellStyle(style);
    header.createCell(14).setCellValue("Onboard");
    header.getCell(14).setCellStyle(style);
    header.createCell(15).setCellValue("Missing");
    header.getCell(15).setCellStyle(style);
    header.createCell(16).setCellValue("Status");
    header.getCell(16).setCellStyle(style);

    int rowCount = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int numberOfRequest = 0;
    for (Request request1 : requests) {
      numberOfRequest++;
      if (!request1.getRequestAssignee().isEmpty()) {
        int soAssignee = 0;
        for (RequestAssignee ra : request1.getRequestAssignee()) {
          soAssignee++;
          Row requestRow = sheet.createRow(rowCount++);
          if(soAssignee == 1) {
          requestRow.createCell(0).setCellValue(numberOfRequest);
          requestRow.createCell(1).setCellValue(request1.getRequestCode());
          requestRow.createCell(2).setCellValue(request1.getProjectId().getTitle());
          requestRow.createCell(3).setCellValue(request1.getPositionId().getTitle());
          requestRow.createCell(4).setCellValue(request1.getRecruitmentTypeId().getTitle());
          requestRow.createCell(5).setCellValue(request1.getApprovedDate() == null? "":sdf.format(request1.getApprovedDate()));
          requestRow.createCell(6).setCellValue(request1.getDeadline() == null? "":sdf.format(request1.getDeadline()));
          requestRow.createCell(7).setCellValue(request1.getNumber());
          }else {
            requestRow.createCell(0).setCellValue("");
            requestRow.createCell(1).setCellValue("");
            requestRow.createCell(2).setCellValue("");
            requestRow.createCell(3).setCellValue("");
            requestRow.createCell(4).setCellValue("");
            requestRow.createCell(5).setCellValue("");
            requestRow.createCell(6).setCellValue("");
            requestRow.createCell(7).setCellValue("");
          }
          requestRow.createCell(8).setCellValue(ra.getAssignee().getFullName());
          requestRow.createCell(9).setCellValue(ra.getNumberOfCandidate());
          requestRow.createCell(10).setCellValue(ra.getCountCandidateStatus().get("Apply") == null ? 0 : ra.getCountCandidateStatus().get("Apply"));
          requestRow.createCell(11).setCellValue(ra.getCountCandidateStatus().get("Contacting") == null ? 0 : ra.getCountCandidateStatus().get("Contacting"));
          requestRow.createCell(12).setCellValue(ra.getCountCandidateStatus().get("Interview") == null ? 0 : ra.getCountCandidateStatus().get("Interview"));
          requestRow.createCell(13).setCellValue(ra.getCountCandidateStatus().get("Offer") == null ? 0 : ra.getCountCandidateStatus().get("Offer"));
          requestRow.createCell(14).setCellValue(ra.getCountCandidateStatus().get("Onboard") == null ? 0 : ra.getCountCandidateStatus().get("Onboard"));
          requestRow.createCell(14).setCellValue(ra.getCountCandidateStatus().get("Onboard") == null ? 0 : ra.getCountCandidateStatus().get("Onboard"));
          requestRow.createCell(15).setCellValue(ra.getNumberOfCandidate() - (ra.getCountCandidateStatus().get("Onboard") == null ? 0 : ra.getCountCandidateStatus().get("Onboard")));
         // if(request1.getRequestStatusId().getTitle().equals("Closed")) {
            requestRow.createCell(16).setCellValue(request1.getRequestStatusId().getTitle());
        //  }
        }
      } else {
        Row requestRow = sheet.createRow(rowCount++);
        requestRow.createCell(0).setCellValue(numberOfRequest);
        requestRow.createCell(1).setCellValue(request1.getRequestCode());
        requestRow.createCell(2).setCellValue(request1.getProjectId().getTitle());
        requestRow.createCell(3).setCellValue(request1.getPositionId().getTitle());
        requestRow.createCell(4).setCellValue(request1.getRecruitmentTypeId().getTitle());
        requestRow.createCell(5).setCellValue(request1.getApprovedDate() == null? "":sdf.format(request1.getApprovedDate()));
        requestRow.createCell(6).setCellValue(request1.getDeadline() == null? "":sdf.format(request1.getDeadline()));
        requestRow.createCell(7).setCellValue(request1.getNumber());
        requestRow.createCell(8).setCellValue("");
        requestRow.createCell(9).setCellValue("");
        requestRow.createCell(10).setCellValue("");
        requestRow.createCell(11).setCellValue("");
        requestRow.createCell(12).setCellValue("");
        requestRow.createCell(13).setCellValue("");
        requestRow.createCell(14).setCellValue("");
        requestRow.createCell(15).setCellValue("");
        requestRow.createCell(16).setCellValue(request1.getRequestStatusId().getTitle());
      }
    }
    for (int i = 0; i <= 16; i++) {
      sheet.autoSizeColumn(i);
    }
  }
}
