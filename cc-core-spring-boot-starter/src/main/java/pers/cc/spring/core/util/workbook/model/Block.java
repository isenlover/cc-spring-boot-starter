package pers.cc.spring.core.util.workbook.model;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;
import java.util.Map;

/**
 * excel块
 * 某一个sheet
 *
 * @author chengce
 * @version 2018-01-01 22:23
 */
@Data
public class Block {
    private String name;

    private Sheet sheet;

    private List<String> titles;

    private Map<Integer, Columns<Column>> rows;
}
