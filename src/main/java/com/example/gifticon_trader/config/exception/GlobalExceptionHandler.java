package com.example.gifticon_trader.config.exception;

import com.example.gifticon_trader.config.i18n.I18nService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
  private final I18nService i18nService;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    BindingResult bindingResult = ex.getBindingResult();
    Map<String, Object> response = new HashMap<>();
    List<Map<String, Object>> errorList = List.of();
    if (bindingResult != null) {
      errorList = bindingResult.getFieldErrors().stream().map(error -> {
        Map<String, Object> errorMap = new HashMap<>();
        String[] codes = error.getCodes();
        errorMap.put("objectName", error.getObjectName());
        errorMap.put("field", error.getField());
        errorMap.put("rejectedValue", error.getRejectedValue());
        errorMap.put("code", error.getCode());
        errorMap.put("codes", codes);
        errorMap.put("message", error.getDefaultMessage());
        if (codes != null && codes.length > 0) {
          try {
            String message = this.i18nService.getMessage(codes[0], error.getArguments());
            errorMap.put("message", message);
          } catch (NoSuchMessageException ignore) {
          }
        }

        return errorMap;
      }).toList();
    }

    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("errors", errorList);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BizException.class)
  public ResponseEntity<Map<String, Object>> handleBizExceptions(BizException ex) {
    Map<String, String> errorMap = new HashMap<>();
    String code = ex.getCode();
    String message;
    try {
      message = this.i18nService.getMessage(code, ex.getArgs());
    } catch (NoSuchMessageException e) {
      message = "message not found";
    }

    errorMap.put("code", code);
    errorMap.put("message", message);
    List<Map<String, String>> errorList = List.of(errorMap);

    Map<String, Object> response = new HashMap<>();
    response.put("status", ex.getStatus().value());
    response.put("errors", errorList);

    return new ResponseEntity<>(response, ex.getStatus());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchExceptions(
      MethodArgumentTypeMismatchException ex) {
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("code", "InvalidArgument");
    errorMap.put("field", ex.getName());
    errorMap.put("rejectedValue", ex.getValue());
    errorMap.put("codes", List.of("InvalidArgument." + ex.getName(), "InvalidArgument"));
    errorMap.put("message", ex.getMessage());
    List<Map<String, Object>> errorList = List.of(errorMap);

    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.BAD_REQUEST.value());
    response.put("errors", errorList);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
    log.debug(ex.getMessage());
    Map<String, Object> response = new HashMap<>();
    response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.put("errors", Collections.emptyList());

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
