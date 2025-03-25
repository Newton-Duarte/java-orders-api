package com.newtonduarte.orders_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.dto.CreateProductDto;
import com.newtonduarte.orders_api.domain.entities.ProductEntity;
import com.newtonduarte.orders_api.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
    @WithMockUser
    public void testThatGetProductsReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @WithMockUser
    public void testThatGetProductsReturnsListOfProducts() throws Exception {
        CreateProductDto testCreateProductDto = TestDataUtils.createTestCreateProductDtoA();
        ProductEntity savedProductEntity = productService.createProduct(testCreateProductDto);

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
    public void testThatGetProductsReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    @WithMockUser
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
    @WithMockUser
    public void testThatCreateProductReturnsCreatedProduct() throws Exception {
        CreateProductDto testCreateProductDto = TestDataUtils.createTestCreateProductDtoA();
        String productJson = objectMapper.writeValueAsString(testCreateProductDto);

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
    public void testThatCreateProductReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    @WithMockUser
    public void testThatGetProductReturnsHttp200WhenProductExists() throws Exception {
        CreateProductDto testCreateProductDto = TestDataUtils.createTestCreateProductDtoA();
        ProductEntity savedProduct = productService.createProduct(testCreateProductDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @WithMockUser
    public void testThatGetProductReturnsHttp404WhenProductDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    @WithMockUser
    public void testThatGetProductReturnsProductWhenProductExists() throws Exception {
        CreateProductDto testCreateProductDto = TestDataUtils.createTestCreateProductDtoA();
        ProductEntity savedProduct = productService.createProduct(testCreateProductDto);

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
    public void testThatGetProductReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    @WithMockUser
    public void testThatUpdateProductReturnsHttp200WhenProductExists() throws Exception {
        CreateProductDto testCreateProductDto = TestDataUtils.createTestCreateProductDtoA();
        ProductEntity savedProduct = productService.createProduct(testCreateProductDto);

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
    @WithMockUser
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
    @WithMockUser
    public void testThatUpdateProductReturnsUpdatedProductWhenProductExists() throws Exception {
        CreateProductDto testCreateProductDto = TestDataUtils.createTestCreateProductDtoA();
        ProductEntity savedProduct = productService.createProduct(testCreateProductDto);

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
    public void testThatUpdateProductReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    @WithMockUser
    public void testThatDeleteProductReturnsHttp204WhenProductExists() throws Exception {
        CreateProductDto testCreateProductDto = TestDataUtils.createTestCreateProductDtoA();
        ProductEntity savedProduct = productService.createProduct(testCreateProductDto);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/" + savedProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteProductReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    @WithMockUser
    public void testThatDeleteProductReturnsHttp404WhenProductDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
