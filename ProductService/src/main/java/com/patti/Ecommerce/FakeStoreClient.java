package com.patti.Ecommerce;

import com.patti.dtos.FakeStoreProductDto;
import com.patti.dtos.GenericProductDto;
import com.patti.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class FakeStoreClient {

    private final RestTemplateBuilder restTemplateBuilder;
    private final String specificProductUrl;
    private final String genericProductUrl;
    private String fakeStoreUrl;
    private String pathForProducts;

    FakeStoreClient(RestTemplateBuilder restTemplateBuilder,
                    @Value("${fakestore.api.url}") String fakeStoreUrl,
                    @Value("${fakestore.api.paths.products}") String pathForProducts) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.genericProductUrl = fakeStoreUrl + pathForProducts;
        this.specificProductUrl = fakeStoreUrl + pathForProducts + "/{id}";
    }


    public FakeStoreProductDto getProductById(Long id){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.
                getForEntity(specificProductUrl, FakeStoreProductDto.class, id);
        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
        if(fakeStoreProductDto == null){
            throw new ProductNotFoundException("Product not found for id"+id);
        }
        return fakeStoreProductDto;
    }

    public List<FakeStoreProductDto> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.
                getForEntity(genericProductUrl, FakeStoreProductDto[].class);
        return List.of(Objects.requireNonNull(responseEntity.getBody()));
    }

    public FakeStoreProductDto deleteProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.
                responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = restTemplate.execute(
                specificProductUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id).getBody();
        if(fakeStoreProductDto == null){
            throw new ProductNotFoundException("Product not found for id"+id);
        }
        return fakeStoreProductDto;
    }
}
