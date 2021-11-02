package com.kevinproject.backtienda.exceptions;


import com.kevinproject.backtienda.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.ValidationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler {

     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler({
             HttpClientErrorException.BadRequest.class,
             ValidationException.class,
             RuntimeException.class
     })
     @ResponseBody
     public ResponseEntity<ErrorDto> BadRequest(Exception exception){
          return ResponseEntity.badRequest().body(ErrorDto.builder()
                                                  .error(exception.getMessage())
                                                  .code(HttpStatus.BAD_REQUEST.value())
                                                  .build());
     }

     @ResponseStatus(HttpStatus.BAD_REQUEST)
     @ExceptionHandler({
             MissingRequestHeaderException.class
     })
     @ResponseBody
     public ResponseEntity<ErrorDto> missingInfo(Exception exception){
          return ResponseEntity.badRequest().body(ErrorDto.builder()
                  .error("Revisa los campos")
                  .code(999)
                  .build());
     }

     @ResponseStatus(HttpStatus.UNAUTHORIZED)
     @ExceptionHandler({
             HttpClientErrorException.Unauthorized.class,
             NoSuchElementException.class
     })
     @ResponseBody
     public ResponseEntity<ErrorDto> UNAUTHORIZED(Exception exception){
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                  .body(ErrorDto.builder()
                                                  .error(exception.getMessage())
                                                  .code(HttpStatus.UNAUTHORIZED.value())
                                                  .build());

     }

     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     @ExceptionHandler({
             HttpServerErrorException.InternalServerError.class
     })
     @ResponseBody
     public ResponseEntity<ErrorDto> UnExpectedException(Exception exception){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                  .body(ErrorDto.builder()
                                                  .error(exception.getMessage())
                                                  .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                  .build());
     }
}
