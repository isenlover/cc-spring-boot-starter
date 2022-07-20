package pers.cc.spring.core.page;

import lombok.Data;
import lombok.NoArgsConstructor;
import pers.cc.spring.core.annotation.typescript.Typescript;
import pers.cc.spring.core.annotation.typescript.TypescriptGenerics;
import pers.cc.spring.core.util.CommonUtils;

import java.util.List;

/**
 * 分页类
 *
 * @author chengce
 * @version 2018-05-18 12:36
 */
@Data
@Typescript
@TypescriptGenerics
@NoArgsConstructor
public class PageResults<T> {
  private long page;

  private long size;

  private long total;

  private boolean finish;

  private boolean empty;

  private List<T> results;

  private PageResults(long page, long size, long total, List<T> results) {
    this.page = page;
    this.size = size;
    this.total = total;
    this.results = results;
    this.empty = this.results == null || this.results.isEmpty();
    this.finish = CommonUtils.isEmpty(this.results) || this.results.size() < this.size;
  }

  public static <T> PageResults<T> of(Long page, Long size, Long total, List<T> results) {
    page = page == null ? 0 : page + 1;
    size = size == null ? 0 : size;
    total = total == null ? 0 : total;
    return new PageResults<>(page, size, total, results);
  }

  public static <T> PageResults<T> finish() {
    return new PageResults<>(0, 0, 0, null);
  }

  public static PageResults of(PageResults pageResults) {
    return new PageResults<>(pageResults.getPage(), pageResults.getSize(), pageResults.getTotal(), null);
  }

  public static <T> PageResults<T> of(int page, int size, Long total, List<T> results) {
    total = total == null ? 0 : total;
    return new PageResults<>(page, size, total, results);
  }

  public static <T> PageResults<T> of(int page, int size, int total, List<T> results) {
    return new PageResults<>(page, size, total, results);
  }

  public static <T> PageResults<T> of(Long page, Long size, Long total) {
    return new PageResults<>(page, size, total, null);
  }

  public static <T> PageResults<T> empty() {
    return new PageResults<>(0, 0, 0, null);
  }
}
