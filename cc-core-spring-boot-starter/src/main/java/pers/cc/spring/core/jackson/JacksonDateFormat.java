package pers.cc.spring.core.jackson;

import java.text.SimpleDateFormat;

/**
 * @author chengce
 * @version 2018-09-30 01:58
 */
public class JacksonDateFormat extends SimpleDateFormat {

    public JacksonDateFormat() {
        super("yyyy");
    }
}
