package pers.cc.spring.data.jpa.page;

import org.springframework.data.domain.Sort;

/**
 * 前端一般传回的page都是1开头，但是数据库需要从0开始
 * 为了兼容所以有了此类
 *
 * @author chengce
 * @version 2018-01-10 11:57
 */
public class PageRequest extends org.springframework.data.domain.PageRequest {
//   此方法已被删除  2021.1.22
//    @Deprecated
//    public PageRequest(int page, int size) {
//        super(page - 1, size);
//    }

  @Deprecated
  public PageRequest(int page, int size, Sort sort) {
    super(page - 1, size, sort);
  }

  public static PageRequest of(int page, int size) {
    return of(page, size, Sort.unsorted());
  }

  public static PageRequest of(int page, int size, int maxPageSize) {
    return of(page, Math.min(maxPageSize, size), Sort.unsorted());
  }

  public static PageRequest of(int page, int size, int maxPageSize, int maxPage) {
    return of(Math.min(page, maxPage), Math.min(maxPageSize, size), Sort.unsorted());
  }

  public static PageRequest of(int page, int size, Sort sort) {
    return new PageRequest(page, size, sort);
  }

  public static PageRequest of(int page, int size, Sort.Direction direction, String... properties) {
    return of(page, size, Sort.by(direction, properties));
  }
}
