package ru.geekbrains.april.market.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.geekbrains.april.market.models.Product;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void genreRepositoryTest() {
        Product product = new Product ();
        product.setTitle("Grape");
        entityManager.persist(product);
        entityManager.flush();

        Page<Product> products = productRepository.findAllBy(Pageable.unpaged ());
        Product productFromDB = productRepository.findById (15L).get ();

        Assertions.assertEquals(15, products.getTotalElements ());
        Assertions.assertEquals ("Grape",productFromDB.getTitle ());
    }


}