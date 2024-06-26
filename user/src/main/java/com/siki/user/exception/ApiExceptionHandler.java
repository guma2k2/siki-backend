package com.siki.user.exception;

import com.siki.user.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    private static final String ERROR_LOG_FORMAT = "Error: URI: {}, ErrorCode: {}, Message: {}";
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorVm = new ErrorDto(HttpStatus.NOT_FOUND.toString(), "NotFound", message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 404, message);
        log.debug(ex.toString());
        return new ResponseEntity<>(errorVm, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorVm = new ErrorDto(HttpStatus.FORBIDDEN.toString(), "Access Denied", message);
        log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 403, message);
        log.debug(ex.toString());
        return new ResponseEntity<>(errorVm, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBadRequest(BadRequestException ex, WebRequest request) {
        String message = ex.getMessage();
        ErrorDto errorVm = new ErrorDto("400", "Bad request", message);
       /* log.warn(ERROR_LOG_FORMAT, this.getServletPath(request), 400, message);
        log.debug(ex.toString());*/
        return new ResponseEntity<>(errorVm, HttpStatus.BAD_REQUEST);
    }

    private String getServletPath(WebRequest webRequest) {
        ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
        return servletRequest.getRequest().getServletPath();
    }

}
