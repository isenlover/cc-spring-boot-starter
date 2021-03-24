package pers.cc.spring.core.util.workbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * Excel版本选择器
 *
 * @author chengce
 * @version 2016-09-22 15:42
 */
public enum CCWorkbookVersion {
  Excel2003("Excel2003"),
  Excel2007("Excel2007");

  private String workBookVersion;

  CCWorkbookVersion(String wbVersion) {
    this.workBookVersion = wbVersion;
  }

  private Workbook getWorkBook(InputStream inputStream) throws IOException {
    Workbook workbook = null;
    if (Excel2003.equals(this)) {
      workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
    } else if (Excel2007.equals(this)) {
      workbook = new XSSFWorkbook(inputStream);
    }
    return workbook;
  }

  private Workbook createWorkBook(File file) throws IOException, InvalidFormatException {
    Workbook workbook = null;
    if (Excel2003.equals(this)) {
      workbook = new HSSFWorkbook(new POIFSFileSystem());
    } else if (Excel2007.equals(this)) {
      workbook = new XSSFWorkbook();
    }
    if (workbook != null) {
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      workbook.write(fileOutputStream);
      fileOutputStream.close();
    }
    return workbook;
  }

  public Workbook getWorkBook(String file) throws IOException {
    return getWorkBook(new FileInputStream(file));
  }

  public Workbook getWorkBook(File file) throws IOException {
    return getWorkBook(new FileInputStream(file));
  }

  public Workbook getNewWorkBook(File file) throws IOException, InvalidFormatException {
    return createWorkBook(file);
  }

  public void setWorkBookVersion(String workBookVersion) {
    this.workBookVersion = workBookVersion;
  }
}
