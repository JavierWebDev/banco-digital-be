package org.example.bancodigitalbe.adapters.in.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
        String code,
        String message,
        Map<String, String> fieldErrors
) {
    public static ApiErrorResponse of(String code, String message) {
        return new ApiErrorResponse(code, message, null);
    }
}
