package pers.cc.spring.api.core.model;

import lombok.Data;

/**
 * 注解@valid 抛出异常后返回前端的bean
 *
 * @author chengce
 * @version 2018-05-09 22:50
 */
@Data
public class ParamNotValidDTO {
    private String field;

    private String message;

    private ParamNotValidDTO(Builder builder) {
        setField(builder.field);
        setMessage(builder.message);
    }

    public static final class Builder {
        private String field;
        private String message;

        public Builder() {
        }

        public Builder field(String val) {
            field = val;
            return this;
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public ParamNotValidDTO build() {
            return new ParamNotValidDTO(this);
        }
    }
}
