package com.patti.dtos;

import com.patti.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GenericProductDto implements Serializable {
    private Long id;
    private String title;
    private int price;
    private String category;
    private String description;
    private String image;

    public static GenericProductDto from(Product product) {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setTitle(product.getTitle());
        genericProductDto.setDescription(product.getDescription());
        //genericProductDto.setPrice(product.getPrice());
        genericProductDto.setImage(product.getImage());
        //genericProductDto.setId(product.getId());
        //genericProductDto.setInventoryCount(product.getInventoryCount());
        return genericProductDto;
    }
}