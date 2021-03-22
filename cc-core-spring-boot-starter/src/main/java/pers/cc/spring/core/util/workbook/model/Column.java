package pers.cc.spring.core.util.workbook.model;

import lombok.Data;

/**
 * excel 列
 *
 * @author chengce
 * @version 2018-01-01 22:25
 */
@Data
public class Column {
    private Integer index;

    private Integer rowIndex;

    private String title;

    private String value;
}
