package com.patti.Ecommerce;

import com.patti.dtos.FakeStoreProductDto;
import com.patti.dtos.GenericProductDto;
import com.patti.dtos.SearchRequestDTO;
import com.patti.security.JWTObject;
import com.patti.security.TokenValidator;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FakeStoreProductService implements ProductService{

    private final FakeStoreClient fakeStoreClient;
    private final TokenValidator tokenValidator;


    FakeStoreProductService(FakeStoreClient fakeStoreClient, TokenValidator tokenValidator){
        this.fakeStoreClient = fakeStoreClient;
        this.tokenValidator = tokenValidator;
    }
    @Override
    public GenericProductDto getProductById(String authToken,Long id) {
        System.out.println(authToken);

        JWTObject jwtObject = tokenValidator.validateToken(authToken);
        Long userId = jwtObject.getUserId();
       /* if(specialIds.present(id)){
            i dont want to allow this request
        }
*/
        return convertToGenericProductDto(fakeStoreClient.getProductById(id));
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<GenericProductDto> genericProductDtos= new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto:fakeStoreClient.getAllProducts()){
            if(fakeStoreProductDto!=null)
            genericProductDtos.add(convertToGenericProductDto(fakeStoreProductDto));
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto deleteProductById(Long id) {
        return convertToGenericProductDto(fakeStoreClient.deleteProductById(id));
    }

    private static GenericProductDto convertToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setId(fakeStoreProductDto.getId());
        genericProductDto.setImage(fakeStoreProductDto.getImage());
        genericProductDto.setCategory(fakeStoreProductDto.getCategory());
        genericProductDto.setDescription(fakeStoreProductDto.getDescription());
        genericProductDto.setTitle(fakeStoreProductDto.getTitle());
        genericProductDto.setPrice(fakeStoreProductDto.getPrice());

        return genericProductDto;
    }

}
