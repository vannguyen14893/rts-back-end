package com.cmc.recruitment.utils;

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
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.cmc.recruitment.entity.Candidate;

public class ExcelViewAM extends AbstractXlsxView {

  @Override
  protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

    // change the file name
    response.setHeader("Content-Disposition", "attachment; filename=\"am_report.xls\"");

    @SuppressWarnings("unchecked")
    List<Candidate> candidates = (List<Candidate>) model.get("candidates");

    // create excel xls sheet
    Sheet sheet = workbook.createSheet("AM Report");
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
    header.createCell(1).setCellValue("Họ và tên ứng viên");
    header.getCell(1).setCellStyle(style);
    header.createCell(2).setCellValue("Dự án");
    header.getCell(2).setCellStyle(style);
    header.createCell(3).setCellValue("Vị trí");
    header.getCell(3).setCellStyle(style);
    header.createCell(4).setCellValue("Loại");
    header.getCell(4).setCellStyle(style);
    header.createCell(5).setCellValue("Ngày nhận order");
    header.getCell(5).setCellStyle(style);
    header.createCell(6).setCellValue("Ngày đi làm");
    header.getCell(6).setCellStyle(style);
    header.createCell(7).setCellValue("Người tuyển dụng");
    header.getCell(7).setCellStyle(style);

    int rowCount = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    for (Candidate candidate : candidates) {
      Row requestRow = sheet.createRow(rowCount++);
      requestRow.createCell(0).setCellValue(rowCount - 1);
      requestRow.createCell(1).setCellValue(candidate.getCvId().getFullName());
      requestRow.createCell(2).setCellValue(candidate.getRequestId().getProjectId().getTitle());
      requestRow.createCell(3).setCellValue(candidate.getRequestId().getPositionId().getTitle());
      requestRow.createCell(4)
          .setCellValue(candidate.getRequestId().getRecruitmentTypeId().getTitle());
      requestRow.createCell(5).setCellValue(candidate.getRequestId().getApprovedDate() == null? "":sdf.format(candidate.getRequestId().getApprovedDate()));
      requestRow.createCell(6).setCellValue(candidate.getOnboardDate() == null? "":sdf.format(candidate.getOnboardDate()));
      requestRow.createCell(7).setCellValue(candidate.getCreatedBy().getFullName());
    }
    sheet.autoSizeColumn(0);
    sheet.autoSizeColumn(1);
    sheet.autoSizeColumn(2);
    sheet.autoSizeColumn(3);
    sheet.autoSizeColumn(4);
    sheet.autoSizeColumn(5);
    sheet.autoSizeColumn(6);
    sheet.autoSizeColumn(7);
  }
}
