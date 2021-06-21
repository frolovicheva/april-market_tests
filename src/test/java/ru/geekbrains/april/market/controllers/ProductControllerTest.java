package ru.geekbrains.april.market.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.april.market.dtos.ProductDto;
import ru.geekbrains.april.market.services.ProductService;
import java.math.BigDecimal;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductService productService;

    @Test
    void getAllProducts() {

    }

    @Test
    void getOneProductById() {
        ProductDto expectedDto = new ProductDto (3L, "Cheese", BigDecimal.valueOf(325.15), "Food");
        ProductDto productDtoForTest = restTemplate.getForObject ("/api/v1/products/{id}",ProductDto.class, 3L);
        Assertions.assertNotNull (productDtoForTest);
        Assertions.assertEquals (expectedDto, productDtoForTest);
    }

    @Test
    void createNewProduct() {
        ProductDto someDto = new ProductDto (15L,"Cherry", BigDecimal.valueOf(25.15), "Food");
        ProductDto productDtoCreated = restTemplate.postForObject ("/api/v1/products", someDto, ProductDto.class);
        Assertions.assertNotNull (productDtoCreated);
        Assertions.assertEquals (someDto,productDtoCreated);
        Assertions.assertEquals (someDto.getTitle (),productDtoCreated.getTitle ());
    }

    @Test
    void updateProduct() {
        ProductDto someDto = new ProductDto (3L,"Banana", BigDecimal.valueOf(15.15), "Food");
        ResponseEntity<ProductDto> productDtoUpdated = restTemplate.exchange ("/api/v1/products", HttpMethod.PUT,new HttpEntity<> (someDto),ProductDto.class, someDto);
        Assertions.assertNotNull (productDtoUpdated);
        Assertions.assertEquals (someDto,productDtoUpdated.getBody ());
        Assertions.assertEquals (someDto.getTitle (),productDtoUpdated.getBody ().getTitle ());
    }

    @Test
    void deleteById() {
        restTemplate.delete ("/api/v1/products/{id}",3L);
        Assertions.assertEquals (Optional.empty(), productService.findById (3L));
    }
}