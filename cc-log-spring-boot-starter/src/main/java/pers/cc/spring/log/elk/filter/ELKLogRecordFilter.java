package pers.cc.spring.log.elk.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.log.elk.model.Log;

/**
 * @author chengce
 * @version 2018-06-23 16:34
 */
public class ELKLogRecordFilter extends Filter<ILoggingEvent> {

  @Override
  public FilterReply decide(ILoggingEvent iLoggingEvent) {
    Log log = CommonUtils.jsonToObject(iLoggingEvent.getMessage(), Log.class);
    if (CommonUtils.isNotEmpty(log)) {
      return FilterReply.ACCEPT;
    }
    return FilterReply.DENY;
  }
}
