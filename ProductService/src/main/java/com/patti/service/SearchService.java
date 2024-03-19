package com.patti.service;

import com.patti.dtos.GenericProductDto;
import com.patti.dtos.SearchRequestDTO;
import com.patti.models.Product;
import com.patti.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    ProductRepository productRepository;

    SearchService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    public Page<GenericProductDto> searchAllProducts(SearchRequestDTO requestDTO) {
        PageRequest pageRequest = PageRequest.of(requestDTO.getPageNumber(),requestDTO.getItemsPerPage());
        List<Product> products = productRepository.findAllByTitleContainingIgnoreCase(requestDTO.getTitle(), pageRequest);
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for (Product product : products) {
            genericProductDtos.add(GenericProductDto.from(product));
        }
        Page<GenericProductDto> page = new PageImpl<>(genericProductDtos, pageRequest, genericProductDtos.size());
        return page;
    }
}
