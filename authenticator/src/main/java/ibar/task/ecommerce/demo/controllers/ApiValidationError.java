package ibar.task.ecommerce.demo.controllers;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public
class ApiValidationError extends ApiSubError {
    @JsonProperty("object")
    private String object;

    @JsonProperty("field")
    private String field;

    @JsonProperty("rejectedValue")
    private Object rejectedValue;

    @JsonProperty("message")
    private String message;

    public ApiValidationError(String object, String field, String rejectedValue, String message) {
        this.object = object;
        this.message = message;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }
}
