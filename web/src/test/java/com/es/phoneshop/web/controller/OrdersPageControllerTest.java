package com.es.phoneshop.web.controller;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderNotFound;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml", "classpath:spring-security-test.xml", "classpath:dispatcher-servlet-test.xml"})
public class OrdersPageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private OrderService orderService;


    @Before
    public void setup() throws OrderNotFound, OutOfStockException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Order order = new Order();
        order.setOrderItems(new ArrayList<>());
        order.setStatus(OrderStatus.NEW);
        orderService.placeOrder(order);
    }


    @Test
    public void testGetOrders() throws Exception {
        mockMvc.perform(get("/admin/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminOrders"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    public void testGetNonExistOrder() throws Exception {
        mockMvc.perform(get("/admin/orders/" + Long.MAX_VALUE))
                .andExpect(status().isOk())
                .andExpect(view().name("adminOrderNotFound"));
    }

    @Test
    public void testGetOrder() throws Exception {
        mockMvc.perform(get("/admin/orders/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminOrderOverview"));
    }

    @Test
    public void testSetStatus() throws Exception {
        mockMvc.perform(post("/admin/orders/1")
                        .param("status", "REJECTED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/orders/1"));
    }
}
