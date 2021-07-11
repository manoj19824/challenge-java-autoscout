package com.autoscout.challenge.util;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private final Logger logger = LoggerFactory.getLogger(ExcelHelper.class);

    public boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
      return true;
    }

    public Map<Integer,Map<String,String>> readAndFetchDataFromExcel(InputStream is,String fileName) throws IOException {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();
            Map<Integer,Map<String,String>> data = new HashMap<>();
            List<String> linkedList = new LinkedList<>();
            sheet.forEach(row -> {
                //This is reserved for the column headers
                if(row.getRowNum() == 0){
                    row.forEach(cell -> {
                        linkedList.add(dataFormatter.formatCellValue(cell));
                    });
                }else {
                    data.put(row.getRowNum(), new HashMap<>());
                    row.forEach(cell -> {
                        String cellValue = dataFormatter.formatCellValue(cell);
                        data.get(row.getRowNum()).put(fileName + ":" +linkedList.get(cell.getColumnIndex()),cellValue);
                    });
                }
            });
            return data;
        }catch (Exception ex){
            logger.error("File processing failed {}",ex);
            throw ex;
        }
        finally {
            if(workbook != null){
                workbook.close();
            }
        }
    }
}
