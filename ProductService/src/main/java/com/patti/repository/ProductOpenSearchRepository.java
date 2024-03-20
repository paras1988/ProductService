package com.patti.repository;

import com.patti.models.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOpenSearchRepository extends ElasticsearchRepository<Product, Long> {

}
