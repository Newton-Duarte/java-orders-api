package com.newtonduarte.orders_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ProductControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductControllerIntegrationTests(MockMvc mockMvc, ProductService productService) {
        this.mockMvc = mockMvc;
        this.productService = productService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatGetProductsReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetProductsReturnsListOfProducts() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        ProductEntity savedProductEntity = productService.save(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").value(savedProductEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(savedProductEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].price").value(savedProductEntity.getPrice())
        );
    }

    @Test
    public void testThatCreateProductReturnsHttp201Created() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        String productJson = objectMapper.writeValueAsString(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateProductReturnsCreatedProduct() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        String productJson = objectMapper.writeValueAsString(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").isNumber()
        );
    }

    @Test
    public void testThatGetProductReturnsHttp200WhenProductExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetProductReturnsHttp404WhenProductDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetProductReturnsProductWhenProductExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").isNumber()
        );
    }

    @Test
    public void testThatUpdateProductReturnsHttp200WhenProductExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        String productJson = objectMapper.writeValueAsString(savedProduct);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateProductReturnsHttp404WhenProductDoesExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();

        String productJson = objectMapper.writeValueAsString(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateProductReturnsUpdatedProductWhenProductExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        savedProduct.setName("UPDATED");
        savedProduct.setPrice(5.00);

        String productJson = objectMapper.writeValueAsString(savedProduct);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.price").value(5.00)
        );
    }

    @Test
    public void testThatPartialUpdateProductReturnsHttp200WhenProductExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        String productJson = objectMapper.writeValueAsString(savedProduct);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateProductReturnsHttp404WhenProductDoesNotExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();

        String productJson = objectMapper.writeValueAsString(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeleteProductReturnsHttp204WhenProductExists() throws Exception {
        ProductEntity testProductEntityA = TestDataUtils.createTestProductEntityA();
        ProductEntity savedProduct = productService.save(testProductEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteProductReturnsHttp404WhenProductDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
