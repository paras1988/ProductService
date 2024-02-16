package com.patti.security;

import com.patti.dtos.FakeStoreProductDto;
import com.patti.exception.ProductNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

@Component
public class TokenValidator {
    private RestTemplateBuilder restTemplateBuilder;

    TokenValidator(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }


    /**
     * This method should call the UserService to validate the token.
     * If the token is valid then return the corresponding object else
     * return empty.
     * @param token
     * @return
     */
    public JWTObject validateToken(String token) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //Make an HTTP call to UserService to call the validateToken method.
        String userServiceValidateURL = "http://localhost:8080/auth/validate";
        ResponseEntity<JWTObject> responseEntity = restTemplate.
                    postForEntity(userServiceValidateURL, new ValidateTokenRequestDto(token, 1L),JWTObject.class, new HashMap<>());

        JWTObject jwtObject = responseEntity.getBody();
        if(jwtObject == null){
            throw new ProductNotFoundException("jwtObject is null");
        }


        return jwtObject;
    }

    @Getter
    @Setter
    public class ValidateTokenRequestDto {
        private String token;
        private Long userId;

        public ValidateTokenRequestDto(String token, Long userId) {
            this.token = token;
            this.userId = userId;
        }
    }

}
