package pers.cc.spring.data.jpa.util;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.database.DatabaseUtils;

import java.util.Arrays;
import java.util.Date;

/**
 * @author chengce
 * @version 2018-06-02 03:00
 */
public class JpaQueryDslUtils {

  public static Predicate getEnumExpression(EnumPath enumPath, Object value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return enumPath.eq(value);
  }

  public static Predicate getStringEqualExpression(StringPath stringPath, String value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return stringPath.eq(value);
  }

  public static Predicate getDateTimeEqualExpression(DateTimePath dateTimePath, Date value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return dateTimePath.eq(value);
  }

  public static Predicate getDateTimeBetweenExpression(DateTimePath dateTimePath, String dateTime) {
    if (CommonUtils.isEmpty(dateTime)) {
      return null;
    }
    Long[] split = Arrays.stream(dateTime.split(",")).map(Long::valueOf).toArray(Long[]::new);
    return dateTimePath.between(new Date(split[0]), new Date(split[1]));
  }

  public static Predicate getBooleanEqualExpression(BooleanPath booleanPath, Boolean value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return booleanPath.eq(value);
  }

  public static Predicate getStringLikeExpression(StringPath stringPath, String value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return stringPath.like(DatabaseUtils.likeAll(value));
  }

  public static OrderSpecifier getOrderSpecifier(String value, Expression expression) {
    if (CommonUtils.isNotEmpty(value)) {
      if (value.equals("asc")) {
        return new OrderSpecifier<>(Order.ASC, expression);
      } else {
        return new OrderSpecifier<>(Order.DESC, expression);
      }
    }
    return null;
  }

}
