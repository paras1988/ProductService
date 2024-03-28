package com.patti.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(as = Product.class)
//@Document(indexName = "products")
public class Product extends BaseModel{
    private String title;
    private String description;
    private String image;
    private String url;

    @ManyToOne
    private Category category;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private Price price;

}
