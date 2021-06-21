package ru.geekbrains.april.market.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.april.market.dtos.ProductDto;
import ru.geekbrains.april.market.error_handling.ResourceNotFoundException;
import ru.geekbrains.april.market.models.Category;
import ru.geekbrains.april.market.models.Product;
import ru.geekbrains.april.market.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private ProductRepository productRepository;


    @Test
    public void findByIdTest() {
        Product testProduct = new Product ();
        testProduct.setId (1L);
        testProduct.setTitle ("apple");
        testProduct.setPrice (BigDecimal.valueOf (25.50));

        Mockito.doReturn (Optional.of(testProduct)).when(productRepository).findById (1L);
        Product apple = productService.findById (1L).get ();
        Assertions.assertNotNull (apple);
        Assertions.assertEquals("apple", apple.getTitle ());
        Assertions.assertEquals(BigDecimal.valueOf (25.50),apple.getPrice ());
        Mockito.verify(productRepository, Mockito.times(1)).findById (ArgumentMatchers.eq(1L));

    }

    @Test
    public void createNewProductTest() {
        ProductDto someProductDto = new ProductDto (1L,"apple", BigDecimal.valueOf (25.50), "Food");
        Mockito.doReturn (null).when (productRepository).save (new Product ());
        ProductDto createdProductDTO = productService.createNewProduct (someProductDto);
        Assertions.assertNotNull (createdProductDTO);
        Assertions.assertEquals("apple",createdProductDTO.getTitle());
        Assertions.assertEquals(BigDecimal.valueOf (25.50),createdProductDTO.getPrice());
        Assertions.assertEquals("Food",createdProductDTO.getCategoryTitle());
    }

    @Test
    public void updateProductTest() {
        ProductDto someProductDto = new ProductDto (1L,"apple", BigDecimal.valueOf (25.50), "Food");
        Product oldProductFromDB = new Product ();
        oldProductFromDB.setId (1L);
        oldProductFromDB.setTitle ("orange");
        oldProductFromDB.setPrice (BigDecimal.valueOf (15.00));
        Mockito.doReturn (Optional.of(oldProductFromDB)).when(productRepository).findById (1L);
        ProductDto updatedProductDto = productService.updateProduct (someProductDto);
        Assertions.assertNotNull (updatedProductDto);
        Assertions.assertEquals("apple",updatedProductDto.getTitle());
        Assertions.assertEquals(BigDecimal.valueOf (25.50),updatedProductDto.getPrice());
        Assertions.assertEquals("Food",updatedProductDto.getCategoryTitle());
    }
}