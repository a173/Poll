package ru.poll.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {
    private Integer errorCode;
    private String message;

    public static ResponseMessageBuilder builder() {
        return new ResponseMessageBuilder();
    }

    @NoArgsConstructor
    public static class ResponseMessageBuilder {
        private Integer errorCode;
        private String message;

        public ResponseMessageBuilder errorCode(Integer errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public ResponseMessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseMessageBuilder message(List<FieldError> fieldErrors) {
            StringBuilder builder = new StringBuilder("{");
            fieldErrors.forEach(error -> builder
                    .append(error.getField())
                    .append(": '")
                    .append(error.getDefaultMessage())
                    .append("', "));

            this.message = builder.substring(0, builder.length() - 2) + "}";
            return this;
        }

        public ResponseMessageBuilder message(String key, String value) {
            this.message = "{" + key + ": '" + value + "'}";
            return this;
        }

        public ResponseMessage build() {
            return new ResponseMessage(errorCode, message);
        }
    }
}