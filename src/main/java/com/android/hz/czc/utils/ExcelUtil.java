package com.android.hz.czc.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 导出Excel文档工具类
 * */
public class ExcelUtil {
	 /**
     * 创建excel文档，
     * list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
    public static Workbook createWorkBook(List<Map<String, Object>> list,String []keys,String columnNames[]) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet");
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (short i = 1; i <= list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i-1).get(keys[j]) == null?" ": list.get(i-1).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }
    /**
     * 读取excel
     * @param keys
     * @param file
     * @param startrow
     * @return List<Map<String,Object>>
     */
    public static List<Map<String,Object>> readExcel(String []keys, MultipartFile multipartFile, File file, int startrow){
    	jxl.Workbook readwb = null;  
    	try{
            InputStream instream =null;
            if(file!=null){
            	instream = new FileInputStream(file);
            }else {
            	instream = multipartFile.getInputStream();
            }
            readwb = jxl.Workbook.getWorkbook(instream) ;
            jxl.Sheet sheet1 = readwb.getSheet(0);
            int rows= sheet1.getRows();
            boolean flag = false;
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            for(int k=startrow;k<rows;k++){//
            	Map<String,Object> map = new TreeMap<String,Object>();
            	flag = true;
            	for(int i = 0;i<keys.length;i++){
            		if(!"".equals(sheet1.getCell(i,k).getContents().toString())){
            			flag = false;
            		}
            		map.put(keys[i],  sheet1.getCell(i,k).getContents());
            	}
            	if(flag){
            		return list;
            	}else{
            		list.add(map);
            	}
            }
            return list;
    	}catch (Exception e) {
			return new ArrayList<Map<String,Object>>();
		}finally{
			if(readwb != null){
        		readwb.close(); 
        	}
		}
    }




    public static List<Map<String,Object>> readExcelOneRow(String []keys, MultipartFile multipartFile, File file, int startrow){
        jxl.Workbook readwb = null;
        try{
            InputStream instream =null;
            if(file!=null){
                instream = new FileInputStream(file);
            }else {
                instream = multipartFile.getInputStream();
            }
            readwb = jxl.Workbook.getWorkbook(instream) ;
            jxl.Sheet sheet1 = readwb.getSheet(0);
            int rows= sheet1.getRows();
            boolean flag = false;
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                Map<String,Object> map = new TreeMap<String,Object>();
                flag = true;
                for(int i = 0;i<keys.length;i++){
                    if(!"".equals(sheet1.getCell(i,startrow).getContents().toString())){
                        flag = false;
                    }
                    map.put(keys[i],  sheet1.getCell(i,startrow).getContents());
                }
                if(flag){
                    return list;
                }else{
                    list.add(map);
                }

            return list;
        }catch (Exception e) {
            return new ArrayList<Map<String,Object>>();
        }finally{
            if(readwb != null){
                readwb.close();
            }
        }
    }

    /**
     * 创建excel文档，
     * list 数据
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     *@paream  rownum 从第几列开始写入
     * */
    public static Workbook createWorkBookByRowNum(List<Map<String, Object>> list,String []keys,String columnNames[],Integer rownum) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet("sheet");
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for(int i=0;i<keys.length;i++){
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        // 创建第rownum行
        Row row = sheet.createRow( rownum);

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

//        Font f3=wb.createFont();
//        f3.setFontHeightInPoints((short) 10);
//        f3.setColor(IndexedColors.RED.getIndex());

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        // 设置第二种单元格的样式（用于值）
        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);
        //设置列名
        for(int i=0;i<columnNames.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        //设置每行每列的值
        for (int i = rownum+1; i <= list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow((short) i);
            // 在row行上创建一个方格
            for(short j=0;j<keys.length;j++){
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i-1-rownum).get(keys[j]) == null?" ": list.get(i-1-rownum).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }
}
