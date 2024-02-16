package com.patti.repository;

import com.patti.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByTitle(String title);

    List<Product> findAllByTitleAndDescription(String title,String description);

    //use jpa doc for getting queries;

    List<Product> findAllByPrice_ValueLessThan(Integer x);

    //@Query(value = "select * from product where id = 1", nativeQuery = true)
    List<Product> findAllByPrice_ValueBetween(Integer x, Integer y);
}
