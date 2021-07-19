package pers.cc.spring.data.jpa.util;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.database.DatabaseUtils;
import pers.cc.spring.core.util.date.DateUtils;
import pers.cc.spring.data.jpa.page.PageRequest;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chengce
 * @version 2018-06-02 03:00
 */
public class JpaQueryDslUtils {

  public static BooleanExpression getEnumExpression(EnumPath enumPath, Object value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return enumPath.eq(value);
  }

  public static BooleanExpression getEnumInExpression(EnumPath enumPath, Object[] value) {
    if (CommonUtils.isEmpty(value) || value.length == 0) {
      return null;
    }
    return enumPath.in(value);
  }

  public static BooleanExpression getStringEqualExpression(StringPath stringPath, String value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return stringPath.eq(value);
  }

  public static BooleanExpression getNumberEqualExpression(NumberPath numberPath, Object value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return numberPath.eq(value);
  }

  public static BooleanExpression getStringEqualOrExpression(StringPath stringPath1, StringPath stringPath2, String value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return stringPath1.eq(value).or(stringPath2.eq(value));
  }

  public static BooleanExpression getDateTimeEqualExpression(DateTimePath dateTimePath, Date value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return dateTimePath.eq(value);
  }

  public static BooleanExpression getDateTimeBetweenExpression(DateTimePath dateTimePath, String dateTime) {
    if (CommonUtils.isEmpty(dateTime)) {
      return null;
    }
    Long[] split = Arrays.stream(dateTime.split(",")).map(Long::valueOf).toArray(Long[]::new);
    return dateTimePath.between(new Date(split[0]), new Date(split[1]));
  }

  public static BooleanExpression getNumberRangeExpression(NumberPath numberPath, String text) {
    if (CommonUtils.isEmpty(text) || !text.contains("-")) {
      return null;
    }
    Long[] split = Arrays.stream(text.split("-")).map(Long::valueOf).toArray(Long[]::new);
    return numberPath.between(split[0], split[1]);
  }

  public static BooleanExpression getDateTimeBetweenExpression(Date date, DateTimePath fromDateTimePath, DateTimePath toDateTimePath) {
    if (CommonUtils.isEmpty(date, fromDateTimePath, toDateTimePath)) {
      return null;
    }
    return fromDateTimePath.goe(date).and(toDateTimePath.loe(date));
  }

  public static BooleanExpression getDateTimeBetweenExpression(String date, DateTimePath fromDateTimePath, DateTimePath toDateTimePath) {
    if (CommonUtils.isEmpty(date, fromDateTimePath, toDateTimePath)) {
      return null;
    }
    Date targetDate = new Date(Long.valueOf(date));
    return fromDateTimePath.loe(targetDate).and(toDateTimePath.goe(targetDate));
  }

  public static BooleanExpression getDateTimeBetweenExpression(DateTimePath dateTimePath, Date from, Date to) {
    if (CommonUtils.isEmpty(from, to)) {
      return null;
    }
    return dateTimePath.between(from, to);
  }

  public static BooleanExpression getBooleanEqualExpression(BooleanPath booleanPath, Boolean value) {
    if (CommonUtils.isEmpty(value) || Boolean.FALSE.equals(value)) {
      return null;
    }
    return booleanPath.eq(value);
  }

  public static BooleanExpression getBooleanEqualExactExpression(BooleanPath booleanPath, Boolean value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return booleanPath.eq(value);
  }

  public static BooleanExpression getExistExpression(BooleanExpression booleanExpression, Boolean value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return booleanExpression;
  }

  public static BooleanExpression getStringLikeExpression(StringPath stringPath, String value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return stringPath.like(DatabaseUtils.likeAll(value));
  }

  public static BooleanExpression getStringInExpression(StringPath stringPath, String[] value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return stringPath.in(Arrays.stream(value).collect(Collectors.toList()));
  }

  public static OrderSpecifier getOrderSpecifier(String value, Expression expression) {
    if (CommonUtils.isNotEmpty(value)) {
      if (value.equals("asc")) {
        return new OrderSpecifier<>(Order.ASC, expression).nullsLast();
      } else {
        return new OrderSpecifier<>(Order.DESC, expression);
      }
    }
    return null;
  }

  public static OrderSpecifier getOrderSpecifier(String value, OrderSpecifier orderSpecifier) {
    if (CommonUtils.isNotEmpty(value)) {
      return orderSpecifier;
    }
    return null;
  }

  public static <T> JPAQuery<T> getPageResult(JPAQuery<T> jpaQuery, PageRequest pageRequest) {
    return jpaQuery.offset(pageRequest.getOffset()).limit(pageRequest.getPageSize());
  }

  public static <T> JPAQuery<T> getPageResult(JPAQuery<T> jpaQuery, PageRequest pageRequest, OrderSpecifier... orderSpecifier) {
    return jpaQuery.offset(pageRequest.getOffset()).limit(pageRequest.getPageSize()).orderBy(orderSpecifier);
  }

  public static OrderSpecifier[] getOrderSpecifierList(List<OrderSpecifier> orderSpecifierList) {
    return orderSpecifierList.stream().filter(Objects::nonNull).collect(Collectors.toList()).toArray(new OrderSpecifier[]{});
  }

  public static OrderSpecifier[] getOrderSpecifierList(OrderSpecifier... orderSpecifierList) {
    if (orderSpecifierList != null && orderSpecifierList.length > 0) {
      return Arrays.stream(orderSpecifierList).filter(Objects::nonNull).collect(Collectors.toList()).toArray(new OrderSpecifier[]{});
    }
    return new OrderSpecifier[]{};
  }

  public static OrderSpecifier[] getDefaultOrderSpecifierList(OrderSpecifier defaultOrderSpecifier, OrderSpecifier... orderSpecifierList) {
    if (orderSpecifierList != null && orderSpecifierList.length > 0) {
      List<OrderSpecifier> list = Arrays.stream(orderSpecifierList).filter(Objects::nonNull).collect(Collectors.toList());
      if (CommonUtils.isEmpty(list)) {
        return new OrderSpecifier[]{defaultOrderSpecifier};
      }
      return list.toArray(new OrderSpecifier[]{});
    }
    return new OrderSpecifier[]{defaultOrderSpecifier};
  }

  /**
   * "DATE_FORMAT({0},'%Y-%m-%d')"
   * %Y：年，4 位
   * %y：年，2 位
   * %M：月名
   * %m：月，数值（00-12）
   * %D：带有英文前缀的月中的天
   * %d：月的天，数值（00-31）
   * %e：月的天，数值（0-31）
   * <p>
   * %H：小时（00-23）
   * %h：小时（01-12）
   * %I：小时（01-12）
   * %i：分钟，数值(00-59)
   * %S：秒(00-59)
   * %s：秒(00-59)
   * <p>
   * %W：星期名
   * %a：缩写星期名
   * %b：缩写月名
   * %c：月，数值
   * %f：微秒
   * %j：年的天 (001-366)
   * %k：小时 (0-23)
   * %l：小时 (1-12)
   * <p>
   * %p：AM 或 PM
   * %r：时间，12-小时（hh:mm:ss AM 或 PM）
   * %T：时间, 24-小时 (hh:mm:ss)
   * <p>
   * %U：周 (00-53) 星期日是一周的第一天
   * %u：周 (00-53) 星期一是一周的第一天
   * %V：周 (01-53) 星期日是一周的第一天，与 %X 使用
   * %v：周 (01-53) 星期一是一周的第一天，与 %x 使用
   * %w：周的天 （0=星期日, 6=星期六）
   * <p>
   * %X：年，其中的星期日是周的第一天，4 位，与 %V 使用
   * %x：年，其中的星期一是周的第一天，4 位，与 %v 使用
   *
   * @param datePath
   * @param format
   * @param targetDate
   * @return
   */
  public static BooleanExpression getFormatDateEqualExpression(DateTimePath datePath, String format, String targetDate) {
    if (CommonUtils.isEmpty(targetDate)) {
      return null;
    }
    return Expressions.stringTemplate("DATE_FORMAT({0},'" + format + "')", datePath).eq(targetDate);
  }

  public static BooleanExpression getFormatDateEqualExpression(DateTimePath datePath, String targetDate) {
    if (CommonUtils.isEmpty(targetDate)) {
      return null;
    }
    return Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", datePath).eq(targetDate);
  }

  public static BooleanExpression getFormatDateEqualExpression(DateTimePath datePath, Date targetDate) {
    if (CommonUtils.isEmpty(targetDate)) {
      return null;
    }
    return Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", datePath).eq(DateUtils.getString(targetDate));
  }

  public static BooleanExpression getFormatDateEqualTodayExpression(DateTimePath datePath) {
    return Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", datePath).eq(DateUtils.getCurrentDayString());
  }

}
