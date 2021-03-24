package pers.cc.spring.core.util.workbook;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import pers.cc.spring.core.exception.ExcelRuntimeException;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.core.util.file.FileUtils;
import pers.cc.spring.core.util.workbook.model.Block;
import pers.cc.spring.core.util.workbook.model.Column;
import pers.cc.spring.core.util.workbook.model.Columns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 操作Excel文档的接口
 *
 * @author chengce
 * @version 2016-08-02 11:06
 */
public class WorkBook<T> {
  private List<Block> blocks;

  private File file;

  //创建线程池
  private final ExecutorService executorService = Executors.newCachedThreadPool();

  private WorkBook() {
  }

  /**
   * 只读第一个sheet
   *
   * @param file 文件
   * @return 解析结果
   * @throws ExcelRuntimeException 异常
   */
  public static <T> WorkBook<T> builder(File file) throws ExcelRuntimeException {
    WorkBook<T> ccWorkBook = new WorkBook<>();
    ccWorkBook.file = file;
    return ccWorkBook;
  }

  private Block getBlock(Workbook workbook,
                         int sheetIndex) {
    Block block = new Block();
    block.setSheet(workbook.getSheetAt(sheetIndex));
    block.setName(block.getSheet().getSheetName());
    Row titleRow = block.getSheet().getRow(0);
    List<String> titles = new ArrayList<>();
    for (int i = 0; i < titleRow.getLastCellNum(); i++) {
      titles.add(getCellValue(titleRow.getCell(i)));
    }
    block.setTitles(titles);
    return block;
  }

  private void initWorkbook(boolean singleSheet) throws ExcelRuntimeException {
    if (file != null && file.exists()) {
      try {
        // 获取文件名
        String fileName = file.getName();
        // 获取Excel扩展名
        String fileExt = FileUtils.getFileExt(fileName);
        // 判断Excel版本
        Workbook workbook;
        if (fileExt.equals(".xls")) {
          // 针对 2003 Excel 文件
          workbook = CCWorkbookVersion.Excel2003.getWorkBook(file);
        } else {
          // 针对2007 Excel 文件
          workbook = CCWorkbookVersion.Excel2007.getWorkBook(file);
        }

        blocks = new ArrayList<>();
        if (singleSheet) {
          blocks.add(getBlock(workbook, 0));
        } else {
          Block block;
          for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            block = getBlock(workbook, i);
            blocks.add(block);
          }
        }
      } catch (IOException e) {
        throw new ExcelRuntimeException(MessageCode.SERVER_ERROR_EXCEL);
      }
    }
  }

  /**
   * 获取某个单元格的值
   *
   * @param cell 单元格
   * @return 值（字符串）
   */
  private String getCellValue(Cell cell) {
    if (cell == null) {
      return "";
    }
    String value;
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_BLANK:
        value = "";
        break;
      case Cell.CELL_TYPE_BOOLEAN:
        value = Boolean.toString(cell.getBooleanCellValue());
        break;
      // 数值
      case Cell.CELL_TYPE_NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          value = simpleDateFormat.format(cell.getDateCellValue());
//                    value = String.valueOf(cell.getDateCellValue());
        } else {
          cell.setCellType(Cell.CELL_TYPE_STRING);
          String temp = cell.getStringCellValue();
          // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
          if (temp.contains(".")) {
            value = String.valueOf(new Double(temp)).trim();
          } else {
            value = temp.trim();
          }
        }
        break;
      case Cell.CELL_TYPE_STRING:
        value = cell.getStringCellValue().trim();
        break;
      case Cell.CELL_TYPE_ERROR:
        value = "";
        break;
      case Cell.CELL_TYPE_FORMULA:
        cell.setCellType(Cell.CELL_TYPE_STRING);
        value = cell.getStringCellValue();
        if (value != null) {
          value = value.replaceAll("#N/A", "").trim();
        }
        break;
      default:
        value = "";
        break;
    }
    return value;
  }

  /**
   * 读取excel
   */
  private void readExcelListByRow() throws ExcelRuntimeException {
    if (blocks != null) {
      Row row;
      Map<Integer, Columns<Column>> rowMap;
      for (Block block : blocks) {
        Sheet sheet = block.getSheet();
        rowMap = new HashMap<>();
        Columns<Column> columns;
        // 从第一行开始读取，getPhysicalNumberOfRows为此行的列数
        // 跳过第一行
        Column column;
        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
          row = sheet.getRow(i);
          // 如果此行为空则跳过
          if (row == null) {
            continue;
          }
          columns = new Columns<>();
          for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
            column = new Column();
            column.setIndex(j + 1);
            column.setRowIndex(i + 1);
            column.setTitle(block.getTitles().get(j));
            column.setValue(getCellValue(row.getCell(j)));
            columns.add(column);
          }
          rowMap.put(i - 1, columns);
        }
        block.setRows(rowMap);
      }
    } else {
      throw new ExcelRuntimeException(MessageCode.SERVER_ERROR_EXCEL);
    }

  }

  /**
   * 写入文件
   *
   * @param data     数据源
   * @param workbook Workbook 实例
   * @param file     文件路径
   * @return 是否写入成功
   */
  private static boolean writeToFile(List<List<Map<Integer, String>>> data,
                                     Workbook workbook,
                                     File file,
                                     CellStyle rowStyle,
                                     CellStyle cellStyle) throws ExcelRuntimeException {
    Sheet sheet;
    Row row;
    Cell cell;
    Map<Integer, String> cellValues;
    // sheet
    for (List<Map<Integer, String>> rowList : data) {
      sheet = workbook.createSheet();
      // row
      for (int j = 0; j < rowList.size(); j++) {
        row = sheet.createRow(j);
        if (rowStyle != null) {
          row.setRowStyle(rowStyle);
        }
        cellValues = rowList.get(j);
        // column
        for (int l = 0; l < cellValues.size(); l++) {
          cell = row.createCell(l);
          cell.setCellValue(cellValues.get(l));
          if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
          }
        }
      }
    }

    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      workbook.write(fileOutputStream);
      fileOutputStream.close();
    } catch (IOException e) {
      throw new ExcelRuntimeException(MessageCode.SERVER_ERROR_EXCEL);
    }
    return true;
  }

  public Message<Block> readSheet() throws ExcelRuntimeException {
    initWorkbook(true);
    readExcelListByRow();
    return Message.ok(blocks.get(0));
  }

  public Message<List<Block>> readSheets() throws ExcelRuntimeException {
    initWorkbook(false);
    readExcelListByRow();
    return Message.ok(blocks);
  }

  /**
   * 单线程写入Excel
   *
   * @param data              数据源
   * @param CCWorkbookVersion 需要写入的Excel版本
   * @param file              写入文件的路径
   * @return 写入结果
   */
  public boolean syncWrite(List<List<Map<Integer, String>>> data,
                           CCWorkbookVersion CCWorkbookVersion,
                           File file) throws ExcelRuntimeException {
    return syncWrite(data, CCWorkbookVersion, file, null, null);
  }

  /**
   * 单线程写入Excel
   *
   * @param data            数据源
   * @param workbookVersion 需要写入的Excel版本
   * @param file            写入文件的路径
   * @param rowStyle        行样式
   * @param cellStyle       单元格样式
   * @return 写入结果
   */
  public boolean syncWrite(List<List<Map<Integer, String>>> data,
                           CCWorkbookVersion workbookVersion,
                           File file,
                           CellStyle rowStyle,
                           CellStyle cellStyle) throws ExcelRuntimeException {
    if (!FileUtils.createOrUpdateFile(file)) {
      return false;
    }
    try {
      Workbook workbook = workbookVersion.getNewWorkBook(file);
      writeToFile(data, workbook, file, rowStyle, cellStyle);
    } catch (IOException | InvalidFormatException e) {
      throw new ExcelRuntimeException(MessageCode.SERVER_ERROR_EXCEL);
    }
    return true;
  }

  public void multiWriteExcel() {

  }
}
