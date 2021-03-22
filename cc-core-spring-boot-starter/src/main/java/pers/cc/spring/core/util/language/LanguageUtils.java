package pers.cc.spring.core.util.language;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import pers.cc.spring.core.util.other.RegExpUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;

/**
 * 语言工具类
 *
 * @author chengce
 * @version 2017-11-20 21:15
 */
public class LanguageUtils {

    @Deprecated
    public static enum ConvertType {
        UPPERCASE,
        LOWERCASE
    }

    /**
     * 汉字转全拼
     *
     * @param chineseLanguage 中文
     * @return 全拼
     * @throws BadHanyuPinyinOutputFormatCombination 转换异常
     *                                               未来版本会删除此方法
     * @see com.github.stuxuhai.jpinyin.PinyinHelper
     */
    @Deprecated
    public static String toHanyuPinyin(String chineseLanguage) throws BadHanyuPinyinOutputFormatCombination {
        char[] chars = chineseLanguage.trim().toCharArray();
        StringBuilder hanyupinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部小写
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        try {
            for (char lChar : chars) {
                // 如果字符是中文,则将中文转为汉语拼音
                if (String.valueOf(lChar).matches("[\u4e00-\u9fa5]+")) {
                    hanyupinyin.append(PinyinHelper.toHanyuPinyinStringArray(lChar, defaultFormat)[0]);
                } else {// 如果字符不是中文,则不转换
                    hanyupinyin.append(lChar);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new BadHanyuPinyinOutputFormatCombination(chineseLanguage + "转拼音失败");
        }
        return hanyupinyin.toString().trim();
    }

    /**
     * 汉字转全拼
     *
     * @param chineseLanguage 中文
     * @param translateType   转换类型，大小写
     * @return 全拼
     * @throws BadHanyuPinyinOutputFormatCombination 转换异常
     * @see com.github.stuxuhai.jpinyin.PinyinHelper
     */
    @Deprecated
    public static String toHanyuPinyin(String chineseLanguage,
                                       ConvertType translateType) throws BadHanyuPinyinOutputFormatCombination {
        switch (translateType) {
            case UPPERCASE:
                return toHanyuPinyin(chineseLanguage).toUpperCase();
            default:
                return toHanyuPinyin(chineseLanguage).toLowerCase();
        }
    }

    /**
     * 获取字符串的unicode编码
     * 汉字“木”的Unicode 码点为Ox6728
     *
     * @param s 木
     * @return \ufeff\u6728  \ufeff控制字符 用来表示「字节次序标记（Byte Order Mark）」不占用宽度
     * 在java中一个char是采用unicode存储的 占用2个字节 比如 汉字木 就是 Ox6728 4bit+4bit+4bit+4bit=2字节
     */
    public static String stringToUnicode(String s) throws UnsupportedEncodingException {
        StringBuilder out = new StringBuilder();
        //直接获取字符串的unicode二进制
        byte[] bytes = s.getBytes("unicode");
        //然后将其byte转换成对应的16进制表示即可
        for (int i = 0; i < bytes.length - 1; i += 2) {
            out.append("\\u");
            String str = Integer.toHexString(bytes[i + 1] & 0xff);
            for (int j = str.length(); j < 2; j++) {
                out.append("0");
            }
            String str1 = Integer.toHexString(bytes[i] & 0xff);
            out.append(str1);
            out.append(str);
        }
        return out.toString();
    }

    public static String unicodeToString(String str) {
        Matcher matcher = RegExpUtils.builder("(\\\\u(\\p{XDigit}{4}))").find(str);
        char ch;
        while (matcher.find()) {
            //roleGroup 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }
}
