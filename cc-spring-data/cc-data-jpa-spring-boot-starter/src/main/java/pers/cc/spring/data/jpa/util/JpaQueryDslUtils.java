package pers.cc.spring.data.jpa.util;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.database.DatabaseUtils;

import java.util.Arrays;
import java.util.Date;
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

  public static BooleanExpression getStringEqualExpression(StringPath stringPath, String value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return stringPath.eq(value);
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

  public static BooleanExpression getBooleanEqualExpression(BooleanPath booleanPath, Boolean value) {
    if (CommonUtils.isEmpty(value)) {
      return null;
    }
    return booleanPath.eq(value);
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
        return new OrderSpecifier<>(Order.ASC, expression);
      } else {
        return new OrderSpecifier<>(Order.DESC, expression);
      }
    }
    return null;
  }

}
