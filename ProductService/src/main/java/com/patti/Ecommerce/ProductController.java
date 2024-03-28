package com.patti.Ecommerce;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.patti.dtos.ExceptionDto;
import com.patti.dtos.FakeStoreProductDto;
import com.patti.dtos.GenericProductDto;
import com.patti.exception.ProductNotFoundException;
import com.patti.hazelcast.HzConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;
    HazelcastInstance hazelcastInstance;

    ProductController(ProductService productService,HazelcastInstance hazelcastInstance){
        this.productService = productService;
        this.hazelcastInstance = hazelcastInstance;
    }

  /*  @GetMapping("/{id}")
    public GenericProductDto getProductById(@RequestHeader("Authorization") String authToken,@PathVariable("id") Long id){
        //this authToken is not needed in spring security
        return productService.getProductById(authToken,id);
    }*/

    @GetMapping("/{id}")
    public GenericProductDto getProductById(@PathVariable("id") Long id){
        IMap<Long,GenericProductDto> iMap = hazelcastInstance.getMap(HzConfig.PRODUCTS);
        if(iMap.containsKey(id)){
            return iMap.get(id);
        }
        GenericProductDto genericProductDto =  productService.getProductById(id);
        iMap.putIfAbsent(id,genericProductDto);
        return genericProductDto;
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
