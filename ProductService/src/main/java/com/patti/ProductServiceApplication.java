package com.patti;

import com.patti.models.Category;
import com.patti.models.Order;
import com.patti.models.Price;
import com.patti.models.Product;
import com.patti.repository.CategoryRepository;
import com.patti.repository.OrderRepository;
import com.patti.repository.PriceRepository;
import com.patti.repository.ProductRepository;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ProductServiceApplication implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final PriceRepository priceRepository;

	public ProductServiceApplication(CategoryRepository categoryRepository,
									 ProductRepository productRepository,
									 OrderRepository orderRepository,
									 PriceRepository priceRepository) throws Exception {
        this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		this.priceRepository = priceRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	public void run(String... args) throws Exception {
		/*Category category = new Category();
		category.setName("Samsung");

		categoryRepository.save(category);


		Product product = new Product();
        product.setTitle("iPhone 15 pro");
        product.setDescription("Best iPhone ever");
        product.setCategory(category);


        Product savedProduct = productRepository.save(product);

		Order order = new Order();
		order.setProducts(Collections.singletonList(savedProduct));


		Price price = new Price();
		price.setCurrency("INR");
		price.setValue(22);
*/


	}

}
