package ibar.task.ecommerce.demo.controllers;


import com.fasterxml.jackson.annotation.JsonProperty;

class ApiValidationError extends ApiSubError {
    @JsonProperty("object")
    private String object;

    @JsonProperty("field")
    private String field;

    @JsonProperty("rejectedValue")
    private Object rejectedValue;

    @JsonProperty("message")
    private String message;

    ApiValidationError(String object, String field, String rejectedValue,  String message) {
        this.object = object;
        this.message = message;
        this.field = field;
        this.rejectedValue = rejectedValue;
    }
}
