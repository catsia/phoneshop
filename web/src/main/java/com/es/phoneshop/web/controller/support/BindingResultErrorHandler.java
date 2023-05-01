package com.es.phoneshop.web.controller.support;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class BindingResultErrorHandler {
    public String getError(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .filter(object -> object instanceof FieldError)
                .map(object -> (FieldError) object)
                .map(FieldError::getCode)
                .collect(Collectors.joining("\n"));
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .filter(fieldError -> fieldError.getField().startsWith("cartItemReduced[") && fieldError.getCode() != null)
                .collect(Collectors.toMap(
                        fieldError -> {
                            int startIndex = fieldError.getField().indexOf("[") + 1;
                            int endIndex = fieldError.getField().indexOf("]");
                            System.out.println(fieldError.getField().substring(startIndex, endIndex));
                            return fieldError.getField().substring(startIndex, endIndex);
                        },
                        FieldError::getCode
                ));
    }
}
