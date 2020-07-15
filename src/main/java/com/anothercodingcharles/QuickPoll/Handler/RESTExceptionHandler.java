package com.anothercodingcharles.QuickPoll.Handler;


import com.anothercodingcharles.QuickPoll.DTO.ErrorDetail;
import com.anothercodingcharles.QuickPoll.DTO.ValidationError;
import com.anothercodingcharles.QuickPoll.Exception.ResourceNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RESTExceptionHandler  extends  ResponseEntityExceptionHandler{

    private MessageSource messageSource;

    public RESTExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request){

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setTitle("Resource Not Found");

        return new ResponseEntity<>(errorDetail,null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setDetail("Input validation failed");
        errorDetail.setDeveloperMessage(manve.getClass().getName());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setTitle("Input Validation");

        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors = manve.getBindingResult().getFieldErrors();

        for(FieldError fe : fieldErrors){

            List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
            if(validationErrorList==null){
                validationErrorList = new ArrayList<>();
                errorDetail.getErrors().put(fe.getField(), validationErrorList);
            }

            ValidationError validationError = new ValidationError();
            validationError.setCode(fe.getCode());
            validationError.setMessage(messageSource.getMessage(fe,null));
            validationErrorList.add(validationError);
            errorDetail.getErrors().put(fe.getField(),validationErrorList);
        }
        return handleExceptionInternal(manve, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle("Message Not Readable");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

}
