package com.pilates.util;

import com.pilates.dao.AdminReportDao;
import com.pilates.dao.AdminReportDaoImpl;
import com.pilates.model.RankingView;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

public class ExcelExporter {

    public static void exportQ1Report() {

        try {

            AdminReportDao dao = new AdminReportDaoImpl();

            // ===== 選擇儲存位置 =====
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("儲存 Q1營運報表");

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getName().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            Workbook workbook = new XSSFWorkbook();

            // ===== 千分位格式 =====
            DecimalFormat df = new DecimalFormat("#,###");

            // ==============================
            // Sheet 1：總覽
            // ==============================
            Sheet overviewSheet = workbook.createSheet("Q1總覽");

            int rowNum = 0;

            Row titleRow = overviewSheet.createRow(rowNum++);
            titleRow.createCell(0).setCellValue("Q1營運統計報表");

            rowNum++;

            writeKpi(overviewSheet, rowNum++, "Q1總營收",
                    df.format(dao.getQuarterRevenue()) + " 元");

            writeKpi(overviewSheet, rowNum++, "Q1售出點數",
                    df.format(dao.getQuarterSoldPoints()));

            writeKpi(overviewSheet, rowNum++, "Q1消耗點數",
                    df.format(dao.getMonthlyDebitPoints()));

            writeKpi(overviewSheet, rowNum++, "Q1預約總人次",
                    df.format(dao.getMonthlyBookingCount()));

            writeKpi(overviewSheet, rowNum++, "Q1取消次數",
                    df.format(dao.getMonthlyCancelCount()));

            overviewSheet.autoSizeColumn(0);
            overviewSheet.autoSizeColumn(1);

            // ==============================
            // Sheet 2：教練排行
            // ==============================
            Sheet coachSheet = workbook.createSheet("教練排行");
            writeRankingSheet(coachSheet,
                    dao.getTop5CoachRanking(),
                    "教練",
                    "上課人次");

            // ==============================
            // Sheet 3：客戶消費排行
            // ==============================
            Sheet purchaseSheet = workbook.createSheet("客戶消費排行");
            writeRankingSheet(purchaseSheet,
                    dao.getTop5CustomerPurchaseRanking(),
                    "會員",
                    "購買點數");

            // ==============================
            // Sheet 4：客戶上課排行
            // ==============================
            Sheet attendanceSheet = workbook.createSheet("客戶上課排行");
            writeRankingSheet(attendanceSheet,
                    dao.getTop5CustomerAttendanceRanking(),
                    "會員",
                    "上課次數");

            // ===== 寫入檔案 =====
            FileOutputStream fos = new FileOutputStream(fileToSave);
            workbook.write(fos);
            workbook.close();
            fos.close();

            JOptionPane.showMessageDialog(null, "Excel匯出成功！");

            // ===== 自動開啟檔案 =====
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(fileToSave);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "匯出失敗");
        }
    }

    private static void writeKpi(Sheet sheet, int rowNum,
                                 String title, String value) {

        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(title);
        row.createCell(1).setCellValue(value);
    }

    private static void writeRankingSheet(Sheet sheet,
                                          List<RankingView> list,
                                          String col1,
                                          String col2) {

        int rowNum = 0;

        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue(col1);
        headerRow.createCell(1).setCellValue(col2);

        for (RankingView r : list) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(r.getName());
            row.createCell(1).setCellValue(r.getTotal());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
}