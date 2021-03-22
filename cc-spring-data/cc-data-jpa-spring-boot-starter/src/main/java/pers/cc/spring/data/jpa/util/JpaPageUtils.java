package pers.cc.spring.data.jpa.util;

import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import pers.cc.spring.core.page.PageResults;

import java.util.List;

/**
 * jpa分页工具类
 * 因为querydsl提供的queryResult分页类存在BUG，并且没有和Page分页统一
 *
 * @author chengce
 * @version 2018-05-18 12:35
 */
public class JpaPageUtils {


  public static <T> PageResults<T> convertToPageData(QueryResults<T> queryResults) {
    return PageResults.of(queryResults.getOffset(), queryResults.getLimit(), queryResults.getTotal(),
        queryResults.getResults());
  }


  public static <T> PageResults<T> convertToPageData(QueryResults<T> queryResults, List<T> otherList) {
    return PageResults.of(queryResults.getOffset(), queryResults.getLimit(), queryResults.getTotal(), otherList);
  }

  public static <T> PageResults<T> convertToPageData(Page<T> page) {
    return PageResults.of(page.getPageable().getPageNumber(), page.getPageable().getPageSize(), page.getTotalElements(),
        page.getContent());
  }


}
