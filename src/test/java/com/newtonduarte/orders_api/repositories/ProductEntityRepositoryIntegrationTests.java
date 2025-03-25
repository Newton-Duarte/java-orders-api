package com.newtonduarte.orders_api.repositories;

import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductEntityRepositoryIntegrationTests {
    private final ProductRepository underTest;

    @Autowired
    public ProductEntityRepositoryIntegrationTests(ProductRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatProductCanBeCreatedAndRecalled() {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        underTest.save(testProductEntityA);

        Optional<ProductEntity> result = underTest.findById(testProductEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testProductEntityA);
    }

    @Test
    public void testThatMultipleProductsCanBeCreatedAndRecalled() {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        underTest.save(testProductEntityA);

        ProductEntity testProductEntityB = TestDataUtils.createTestProductEntityB();
        underTest.save(testProductEntityB);

        ProductEntity testProductEntityC = TestDataUtils.createTestProductEntityC();
        underTest.save(testProductEntityC);

        Iterable<ProductEntity> result = underTest.findAll();

        assertThat(result).hasSize(3);
    }

    @Test
    public void testThatProductCanBeUpdated() {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        underTest.save(testProductEntityA);

        testProductEntityA.setName("UPDATED");
        underTest.save(testProductEntityA);

        Optional<ProductEntity> result = underTest.findById(testProductEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("UPDATED");
    }

    @Test
    public void testThatProductCanBeDeleted() {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        underTest.save(testProductEntityA);
        underTest.deleteById(testProductEntityA.getId());

        Optional<ProductEntity> result = underTest.findById(testProductEntityA.getId());

        assertThat(result).isEmpty();
    }
}
