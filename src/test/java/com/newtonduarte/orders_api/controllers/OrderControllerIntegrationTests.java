package com.newtonduarte.orders_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newtonduarte.orders_api.TestDataUtils;
import com.newtonduarte.orders_api.domain.CreateOrderRequest;
import com.newtonduarte.orders_api.domain.OrderStatus;
import com.newtonduarte.orders_api.domain.UpdateOrderRequest;
import com.newtonduarte.orders_api.domain.dto.CreateOrderDto;
import com.newtonduarte.orders_api.domain.dto.CreateProductDto;
import com.newtonduarte.orders_api.domain.dto.CreateUserDto;
import com.newtonduarte.orders_api.domain.entities.OrderEntity;
import com.newtonduarte.orders_api.domain.entities.UserEntity;
import com.newtonduarte.orders_api.security.ApiUserDetails;
import com.newtonduarte.orders_api.services.OrderService;
import com.newtonduarte.orders_api.services.ProductService;
import com.newtonduarte.orders_api.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderControllerIntegrationTests(
            OrderService orderService,
            MockMvc mockMvc,
            ProductService productService,
            UserService userService
    ) {
        this.mockMvc = mockMvc;
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser
    public void testThatGetOrdersReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @WithMockUser
    public void testThatGetOrdersReturnsListOfOrders() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);

        CreateOrderRequest createOrderRequest = TestDataUtils.createCreateOrderRequest();
        orderService.createOrder(savedUser.getId(), createOrderRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetOrdersReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    public void testThatCreateOrderReturns201Created() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);
        CreateOrderDto createOrderDto = TestDataUtils.createCreateOrderDto();
        String createOrderJson = objectMapper.writeValueAsString(createOrderDto);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateOrderReturnsOrder() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);
        CreateOrderDto createOrderDto = TestDataUtils.createCreateOrderDto();
        String createOrderJson = objectMapper.writeValueAsString(createOrderDto);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createOrderJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.products").isArray()
        );
    }

    @Test
    public void testThatCreateOrderReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    public void testThatGetOrderReturnsHttp200WhenOrderExists() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        CreateOrderRequest createOrderRequest = TestDataUtils.createCreateOrderRequest();
        OrderEntity savedOrder = orderService.createOrder(savedUser.getId(), createOrderRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetOrderReturnsOrderWhenOrderExists() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        CreateOrderRequest createOrderRequest = TestDataUtils.createCreateOrderRequest();
        OrderEntity savedOrder = orderService.createOrder(savedUser.getId(), createOrderRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedOrder.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.products").isArray()
        );
    }

    @Test
    public void testThatGetOrderReturnsHttp404WhenOrderDoesNotExists() throws Exception {
        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetOrderReturnsHttp403WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/orders/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    public void testThatUpdateOrderReturns200Ok() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        CreateOrderRequest createOrderRequest = TestDataUtils.createCreateOrderRequest();
        OrderEntity savedOrder = orderService.createOrder(savedUser.getId(), createOrderRequest);

        UpdateOrderRequest updateOrderRequest = TestDataUtils.createUpdateOrderRequest();

        updateOrderRequest.setStatus(OrderStatus.COMPLETE);
        String updateOrderJson = objectMapper.writeValueAsString(updateOrderRequest);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateOrderJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateOrderReturnsUpdatedOrder() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        CreateOrderRequest createOrderRequest = TestDataUtils.createCreateOrderRequest();
        OrderEntity savedOrder = orderService.createOrder(savedUser.getId(), createOrderRequest);

        UpdateOrderRequest updateOrderRequest = TestDataUtils.createUpdateOrderRequest();

        updateOrderRequest.setStatus(OrderStatus.COMPLETE);
        String updateOrderJson = objectMapper.writeValueAsString(updateOrderRequest);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateOrderJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.status").value(OrderStatus.COMPLETE.toString())
        );
    }

    @Test
    public void testThatUpdateOrderReturnsIllegal() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);
        ApiUserDetails apiUserDetails = new ApiUserDetails(savedUser);

        CreateOrderRequest createOrderRequest = TestDataUtils.createCreateOrderRequest();
        createOrderRequest.setStatus(OrderStatus.COMPLETE);
        OrderEntity savedOrder = orderService.createOrder(savedUser.getId(), createOrderRequest);

        UpdateOrderRequest updateOrderRequest = TestDataUtils.createUpdateOrderRequest();
        updateOrderRequest.setId(savedOrder.getId());

        String updateOrderJson = objectMapper.writeValueAsString(updateOrderRequest);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateOrderJson)
                        .with(SecurityMockMvcRequestPostProcessors.user(apiUserDetails))
                        .requestAttr("userId", 1L)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void testThatUpdateOrderReturns403WhenWhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/orders/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }

    @Test
    @WithMockUser
    public void testThatDeleteOrdersReturns204WhenOrderExists() throws Exception {
        CreateProductDto testCreateProductDtoA = TestDataUtils.createTestCreateProductDtoA();
        productService.createProduct(testCreateProductDtoA);

        CreateUserDto testCreateUserDtoA = TestDataUtils.createTestCreateUserDtoA();
        UserEntity savedUser = userService.createUser(testCreateUserDtoA);

        CreateOrderRequest createOrderRequest = TestDataUtils.createCreateOrderRequest();
        OrderEntity savedOrder = orderService.createOrder(savedUser.getId(), createOrderRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/orders/" + savedOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteOrdersReturns403WhenWhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/orders/999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isForbidden()
        );
    }
}
