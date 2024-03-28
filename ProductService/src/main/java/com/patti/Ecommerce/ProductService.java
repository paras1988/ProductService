package com.patti.Ecommerce;


import com.patti.dtos.FakeStoreProductDto;
import com.patti.dtos.GenericProductDto;
import com.patti.dtos.SearchRequestDTO;

import java.util.List;

public interface ProductService {
    GenericProductDto getProductById(String authToken,Long id);

    GenericProductDto getProductById(Long id);

    List<GenericProductDto> getAllProducts();

    GenericProductDto deleteProductById(Long id);
}
