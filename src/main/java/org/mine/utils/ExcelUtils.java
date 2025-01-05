package org.mine.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtils {
    public static final Logger logger= LogManager.getLogger("executionLogger");
    // Method to read Excel file and return data as a 2D Object array
    public static Object[][] readExcelData(String filePath, String sheetName) {
        List<Object[]> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

            for (int i = 0; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Object[] rowData = new Object[columnCount];// creates an array to store the cell values for each row
                    for (int j = 0; j < columnCount; j++) {
                        Cell cell = row.getCell(j);
                        rowData[j] = getCellValue(cell);
                    }
                    data.add(rowData);// Why Not Just data.add(getCellValue(cell))?: Adding each cell value directly to data wouldn’t distinguish between rows, resulting in a single list of cell values without row structure. Instead, the Object[] rowData array represents each row and is added to data as a single element, maintaining the 2D structure.
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException occured while reading data from excel",e);
            throw new RuntimeException();
        }

        // Convert List to 2D array
        Object[][] arrayData = new Object[data.size()][];
        return data.toArray(arrayData);
    }

    // Method to get cell value as Object
    private static Object getCellValue(Cell cell) {
        if (cell == null)  return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
            default:
                return "";
        }
    }


}




//
//public static Object[][] readExcelData(String filePath, String sheetName) {
//    List<List<Object>> data = new ArrayList<>();
//    try (FileInputStream fis = new FileInputStream(filePath);
//         Workbook workbook = new XSSFWorkbook(fis)) {
//        Sheet sheet = workbook.getSheet(sheetName);
//        int rowCount = sheet.getPhysicalNumberOfRows();
//        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
//
//        for (int i = 0; i < rowCount; i++) {
//            Row row = sheet.getRow(i);
//            if (row != null) {
//                List<Object> rowData = new ArrayList<>();
//                for (int j = 0; j < columnCount; j++) {
//                    Cell cell = row.getCell(j);
//                    rowData.add(getCellValue(cell));
//                }
//                data.add(rowData);
//            }
//        }
//
//        // Convert List<List<Object>> to Object[][]
//        Object[][] result = new Object[data.size()][];
//        for (int i = 0; i < data.size(); i++) {
//            result[i] = data.get(i).toArray(new Object[0]);
//        }
//        return result;
//
//    } catch (IOException e) {
//        logger.error("IOException occurred while reading data from excel", e);
//        throw new RuntimeException(e);
//    }
//}