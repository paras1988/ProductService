package com.patti.Ecommerce;

import com.patti.dtos.GenericProductDto;
import com.patti.dtos.SearchRequestDTO;
import com.patti.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    SearchService searchService;

    SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    @GetMapping
    public Page<GenericProductDto> searchAllProducts(@RequestBody SearchRequestDTO requestDTO){
        //this authToken is not needed in spring security
        return searchService.searchAllProducts(requestDTO);
    }
}
