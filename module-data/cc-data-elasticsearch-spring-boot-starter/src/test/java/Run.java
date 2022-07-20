import pers.cc.elasticsearch.annotation.DateFormat;
import pers.cc.elasticsearch.annotation.Document;
import pers.cc.elasticsearch.filed.MappingParameter;
import pers.cc.elasticsearch.util.ElasticsearchUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author chengce
 * @version 2018-07-07 17:42
 */
public class Run {
    public static void main(String[] ss) {
//        getValue();
        System.out.println(ElasticsearchUtils.getFormats(
                new DateFormat[]{DateFormat.custom_date, DateFormat.custom_date_time, DateFormat.date, DateFormat.year_month_day}));
        Map s = new HashMap();
//        s.put(MappingParameter.index, "s");
        System.out.println(s.toString());

    }


    public static int getValue() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            try {
                System.out.println("请输入");
                return scanner.nextInt();
            } catch (Exception e) {

            }
        }
    }
}
