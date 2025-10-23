package com.hr.util;

import com.hr.dto.SalaryDetailVO;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * PDF 导出工具类
 */
public class PdfUtil {
    
    /**
     * 导出工资条 PDF（简化版 - 确保文件完整性）
     */
    public static void exportSalaryPdf(SalaryDetailVO detail, HttpServletResponse response) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // 使用 PDFBox 2.x 标准字体
                PDFont fontBold = PDType1Font.HELVETICA_BOLD;
                PDFont fontNormal = PDType1Font.HELVETICA;
                
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();
                float margin = 50;
                float yPosition = pageHeight - margin;
                float lineHeight = 20;
                
                // 标题
                contentStream.setFont(fontBold, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("SALARY SLIP");
                contentStream.endText();
                
                yPosition -= lineHeight * 2;
                
                // 年月
                contentStream.setFont(fontNormal, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Month: " + (detail.getYearMonth() != null ? detail.getYearMonth() : ""));
                contentStream.endText();
                
                yPosition -= lineHeight * 2;
                
                // 员工信息
                contentStream.setFont(fontNormal, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                String employeeName = convertEmployeeNameToEnglish(detail.getEmployeeName());
                contentStream.showText("Employee Name: " + employeeName);
                contentStream.endText();
                
                yPosition -= lineHeight;
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Employee No: " + (detail.getEmployeeNo() != null ? detail.getEmployeeNo() : ""));
                contentStream.endText();
                
                yPosition -= lineHeight * 2;
                
                // 工资明细标题
                contentStream.setFont(fontBold, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Salary Details:");
                contentStream.endText();
                
                yPosition -= lineHeight;
                
                // 工资明细
                contentStream.setFont(fontNormal, 11);
                if (detail.getItems() != null && !detail.getItems().isEmpty()) {
                    for (SalaryDetailVO.ItemDetail item : detail.getItems()) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin + 20, yPosition);
                        // 将中文项目名称转换为英文
                        String itemName = convertToEnglish(item.getItemName());
                        contentStream.showText(itemName + ": " + formatAmount(item.getAmount()));
                        contentStream.endText();
                        yPosition -= lineHeight;
                    }
                }
                
                yPosition -= lineHeight;
                
                // 汇总信息
                contentStream.setFont(fontBold, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Summary:");
                contentStream.endText();
                
                yPosition -= lineHeight;
                
                contentStream.setFont(fontNormal, 11);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 20, yPosition);
                contentStream.showText("Base Salary: " + formatAmount(detail.getBaseSalary()));
                contentStream.endText();
                
                yPosition -= lineHeight;
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 20, yPosition);
                contentStream.showText("Total Deduction: " + formatAmount(detail.getDeduction()));
                contentStream.endText();
                
                yPosition -= lineHeight;
                contentStream.setFont(fontBold, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin + 20, yPosition);
                contentStream.showText("Actual Salary: " + formatAmount(detail.getActualSalary()));
                contentStream.endText();
                
                yPosition -= lineHeight * 2;
                
                // 页脚
                contentStream.setFont(fontNormal, 9);
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText("Generated on: " + java.time.LocalDate.now().toString());
                contentStream.endText();
                
                if (detail.getRemark() != null && !detail.getRemark().isEmpty()) {
                    yPosition -= lineHeight;
                    contentStream.beginText();
                    contentStream.newLineAtOffset(margin, yPosition);
                    contentStream.showText("Remark: " + detail.getRemark());
                    contentStream.endText();
                }
            }
            
            // 设置响应头
            response.setContentType("application/pdf");
            String fileName = URLEncoder.encode("salary_" + detail.getYearMonth(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".pdf");
            
            // 输出
            document.save(response.getOutputStream());
        }
    }
    
    
    /**
     * 格式化金额
     */
    private static String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return String.format("%.2f", amount);
    }
    
    /**
     * 将中文项目名称转换为英文
     */
    private static String convertToEnglish(String chineseName) {
        if (chineseName == null) {
            return "Unknown";
        }
        
        switch (chineseName) {
            case "基本工资":
                return "Base Salary";
            case "绩效奖金":
                return "Performance Bonus";
            case "全勤奖":
                return "Attendance Bonus";
            case "加班费":
                return "Overtime Pay";
            case "五险一金":
                return "Social Insurance";
            case "个人所得税":
                return "Income Tax";
            case "迟到扣款":
                return "Late Deduction";
            default:
                return chineseName; // 如果无法转换，返回原名称
        }
    }
    
    /**
     * 将中文员工姓名转换为英文或拼音
     */
    private static String convertEmployeeNameToEnglish(String chineseName) {
        if (chineseName == null) {
            return "Unknown";
        }
        
        switch (chineseName) {
            case "王五":
                return "Wang Wu";
            case "李四":
                return "Li Si";
            case "系统管理员":
                return "System Admin";
            case "张人事":
                return "Zhang HR";
            default:
                return chineseName; // 如果无法转换，返回原名称
        }
    }
}
