package pers.cc.spring.core.util.language;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import pers.cc.spring.core.exception.PinyinRuntimeException;

/**
 * 对 {@link PinyinHelper}的再次封装
 *
 * @author chengce
 * @version 2018-06-03 16:57
 */
public class PinyinUtils {

    /**
     * 中文转拼音
     * 无分隔符和音调
     *
     * @param chinese 中文
     * @return 拼音
     */
    public static String toPinyin(String chinese) {
        return toPinyin(chinese, "");
    }

    public static String toPinyin(String chinese, String separator) {
        try {
            return PinyinHelper.convertToPinyinString(chinese, separator, PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            throw new PinyinRuntimeException(e.getMessage());
        }
    }

    public static String toShortPinyin(String chinese) {
        try {
            return PinyinHelper.getShortPinyin(chinese);
        } catch (PinyinException e) {
            throw new PinyinRuntimeException(e.getMessage());
        }
    }
}
