package com.cmc.recruitment.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public class ExcelViewDuReport extends AbstractXlsxView {

  private Long year;
  
  public Long getYear() {
    return year;
  }

  public void setYear(Long year) {
    this.year = year;
  }

  public ExcelViewDuReport(Long year) {
    this.year = year;
  }

  public ExcelViewDuReport() {
  }

  @Override
  protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    // change the file name
    response.setHeader("Content-Disposition", "attachment; filename=\"Du Report.xls\"");

    @SuppressWarnings("unchecked")
    List<ReportByGroup> reportList = (List<ReportByGroup>) model.get("reportList");

    String[] month = new String[] { "April", "May", "June", "July", "August", "September", "October", "November", "December", "January",
        "February", "March" };

    // create excel xls sheet
    Sheet sheet = workbook.createSheet("Report Du Detail");
    // sheet.setDefaultColumnWidth(12);

    // create style for header cells
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setFontName("Arial");
    style.setFillForegroundColor(HSSFColor.BLUE.index);
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setWrapText(true);
    style.setBorderRight(BorderStyle.THIN);
    style.setRightBorderColor(IndexedColors.BLACK.getIndex());
    style.setBorderLeft(BorderStyle.THIN);
    style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    style.setBorderTop(BorderStyle.THIN);
    style.setTopBorderColor(IndexedColors.BLACK.getIndex());
    style.setBorderBottom(BorderStyle.THIN);
    style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    font.setBold(true);
    font.setColor(HSSFColor.WHITE.index);
    style.setFont(font);

    CellStyle style1 = workbook.createCellStyle();
    // style1.setAlignment(HorizontalAlignment.CENTER);
    // style1.setWrapText(true);
    style1.setBorderRight(BorderStyle.THIN);
    style1.setRightBorderColor(IndexedColors.BLACK.getIndex());
    style1.setBorderLeft(BorderStyle.THIN);
    style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
    style1.setBorderTop(BorderStyle.THIN);
    style1.setTopBorderColor(IndexedColors.BLACK.getIndex());
    style1.setBorderBottom(BorderStyle.THIN);
    style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    // style1.setFont(font);

    // create header row
    Row header = sheet.createRow(0);
    Row header1 = sheet.createRow(1);
    Row header2 = sheet.createRow(2);

    header.createCell(0).setCellValue("STT");
    header.getCell(0).setCellStyle(style);
    sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
        2, // last row (0-based)
        0, // first column (0-based)
        0 // last column (0-based)
    ));
    header.createCell(1).setCellValue("Vị trí");
    header.getCell(1).setCellStyle(style);
    sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
        2, // last row (0-based)
        1, // first column (0-based)
        1 // last column (0-based)
    ));

    header.createCell(2).setCellValue("");
    header.getCell(2).setCellStyle(style);
    sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
        2, // last row (0-based)
        2, // first column (0-based)
        2 // last column (0-based)
    ));

    header.createCell(3).setCellValue("Số lượng đã tuyển trong tháng");
    header.getCell(3).setCellStyle(style);
    sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
        0, // last row (0-based)
        3, // first column (0-based)
        26 // last column (0-based)
    ));
    header.createCell(27).setCellValue("SL tuyển dụng " + this.year);
    header.getCell(27).setCellStyle(style);
    sheet.addMergedRegion(new CellRangeAddress(0, // first row (0-based)
        2, // last row (0-based)
        27, // first column (0-based)
        27 // last column (0-based)
    ));

    header1.createCell(0).setCellValue("");
    header1.getCell(0).setCellStyle(style);
    header1.createCell(1).setCellValue("");
    header1.getCell(1).setCellStyle(style);
    int k = 0;
    for (int i = 3; i < 27; i++) {
      if (i % 2 != 0) {
        header1.createCell(i).setCellValue(month[k++]);
        header1.getCell(i).setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(1, // first row (0-based)
            1, // last row (0-based)
            i, // first column (0-based)
            i + 1 // last column (0-based)
        ));
      } else {
        header1.createCell(i).setCellValue("");
        header1.getCell(i).setCellStyle(style);
      }
    }

    header2.createCell(0).setCellValue("");
    header2.getCell(0).setCellStyle(style);
    header2.createCell(1).setCellValue("");
    header2.getCell(1).setCellStyle(style);
    for (int i = 3; i < 27; i++) {
      if (i % 2 != 0) {
        header2.createCell(i).setCellValue("NEW");
        header2.getCell(i).setCellStyle(style);
      } else {
        header2.createCell(i).setCellValue("TT");
        header2.getCell(i).setCellStyle(style);
      }
    }

    int rowCount = 3;

    for (ReportByGroup item : reportList) {
      Row row = sheet.createRow(rowCount++);
      row.createCell(0).setCellValue(rowCount - 3);
      if (item.getDepartment() == null) {
        row.createCell(1).setCellValue(item.getGroup());
      } else {
        row.createCell(1).setCellValue("");
      }
      row.createCell(2).setCellValue(item.getDepartment());
      int a = 3;
      for (StatisticMonth number : item.getStatisticMonth()) {
        if (number.getNumberOfNew() == 0) {
          row.createCell(a++).setCellValue("");
        } else {
          row.createCell(a++).setCellValue(number.getNumberOfNew());
        }
        if (number.getNumberOfTT() == 0) {
          row.createCell(a++).setCellValue("");
        } else {
          row.createCell(a++).setCellValue(number.getNumberOfTT());
        }
      }
      if (item.getTotal() == 0) {
        row.createCell(a).setCellValue("");
      } else {
        row.createCell(a).setCellValue(item.getTotal());
      }

      for (int i = 0; i < 28; i++) {
        row.getCell(i).setCellStyle(style1);
      }
    }
    sheet.autoSizeColumn(0);
    sheet.autoSizeColumn(1);
    sheet.autoSizeColumn(2);
    sheet.autoSizeColumn(3);
    sheet.autoSizeColumn(4);
    sheet.autoSizeColumn(5);
    sheet.autoSizeColumn(6);
    sheet.autoSizeColumn(7);
    sheet.autoSizeColumn(8);
    sheet.autoSizeColumn(9);
    sheet.autoSizeColumn(10);
    sheet.autoSizeColumn(11);
    sheet.autoSizeColumn(12);
    sheet.autoSizeColumn(13);
    sheet.autoSizeColumn(14);
    sheet.autoSizeColumn(15);
    sheet.autoSizeColumn(16);
    sheet.autoSizeColumn(17);
    sheet.autoSizeColumn(18);
    sheet.autoSizeColumn(19);
    sheet.autoSizeColumn(20);
    sheet.autoSizeColumn(21);
    sheet.autoSizeColumn(22);
    sheet.autoSizeColumn(23);
    sheet.autoSizeColumn(24);
    sheet.autoSizeColumn(25);
  }
}
