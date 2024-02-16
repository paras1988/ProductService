package com.patti.Ecommerce;

import com.patti.dtos.ExceptionDto;
import com.patti.dtos.GenericProductDto;
import com.patti.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    private ResponseEntity<ExceptionDto> handleProductNotFoundException(){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setHttpStatus(HttpStatus.NOT_FOUND);
        exceptionDto.setMessage("Got ProductNotFoundException");
        return new ResponseEntity<>(exceptionDto, HttpStatusCode.valueOf(404));
    }

}
