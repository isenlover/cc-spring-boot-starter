package pers.cc.spring.core.util.workbook.model;


import pers.cc.spring.core.exception.ExcelRuntimeException;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.core.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * excel 列数组
 *
 * @author chengce
 * @version 2018-01-01 23:13
 */
public class Columns<T> extends ArrayList<Column> implements List<Column> {

    public Column get(String title) throws ExcelRuntimeException {
        Optional<Column> column = this.stream().filter(obj -> obj.getTitle().equals(title)).findFirst();
        if (column.isPresent()) {
            return column.get();
        } else {
            throw new ExcelRuntimeException(title + "字段缺失", MessageCode.SERVER_ERROR_EXCEL);
        }
    }

    public String getValue(String title) throws ExcelRuntimeException {
        return get(title) == null ? "" : get(title).getValue();
    }

    public String getValueTrim(String title) throws ExcelRuntimeException {
        return getValue(title).trim();
    }

    public boolean isEmptyValue(String title) throws ExcelRuntimeException {
        return CommonUtils.isEmpty(getValueTrim(title));
    }
}
