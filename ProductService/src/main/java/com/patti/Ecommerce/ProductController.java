package com.patti.Ecommerce;

import com.patti.dtos.ExceptionDto;
import com.patti.dtos.FakeStoreProductDto;
import com.patti.dtos.GenericProductDto;
import com.patti.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@RequestHeader("Authorization") String authToken,@PathVariable("id") Long id){
        return productService.getProductById(authToken,id);
    }

    @GetMapping
    public List<GenericProductDto> getAllProduct(){
        return productService.getAllProducts();
    }

    @DeleteMapping("/{id}")
    public GenericProductDto deleteProductById(@PathVariable("id") Long id){
        return productService.deleteProductById(id);
    }

    @PostMapping
    public void createProduct(){

    }

}
