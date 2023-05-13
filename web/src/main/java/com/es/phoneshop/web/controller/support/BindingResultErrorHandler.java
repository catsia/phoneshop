package com.es.phoneshop.web.controller.support;

import org.springframework.context.support.DefaultMessageSourceResolvable;
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

    public Map<String, String> getErrorsForCart(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .filter(fieldError -> fieldError.getCode() != null)
                .collect(Collectors.toMap(
                        DefaultMessageSourceResolvable::getCode,
                        FieldError::getDefaultMessage
                ));
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getCode
                ));
    }
}
