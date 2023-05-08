package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.dao.OrderDao;
import com.es.core.model.order.dao.OrderItemDao;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    private OrderDao orderDao;

    private OrderItemDao orderItemDao;

    private StockDao stockDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, StockDao stockDao) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.stockDao = stockDao;
    }


    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
        order.setSecureId(UUID.randomUUID().toString());
        order.setOrderItems(cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(cartItem.getPhone());
            orderItem.setQuantity(cartItem.getQuantity());
            return orderItem;
        }).collect(Collectors.toList()));
        order.setTotalPrice(cart.getTotalCost().add(deliveryPrice));
        order.setSubtotal(cart.getTotalCost());
        order.setDeliveryPrice(deliveryPrice);
        return order;
    }

    @Override
    public long placeOrder(Order order) throws OutOfStockException {
        long id = orderDao.save(order);
        order.setId(id);
        for (OrderItem orderItem : order.getOrderItems()) {
            Stock stock = stockDao.get(orderItem.getPhone().getId()).get();
            if (stock.getStock() < orderItem.getQuantity()) {
                throw new OutOfStockException("Phone " + orderItem.getPhone().getModel() + "doesn't have " + orderItem.getQuantity() + "left in stock");
            }
        }
        order.getOrderItems().removeIf(orderItem -> stockDao.get(orderItem.getPhone().getId()).get().getStock() < orderItem.getQuantity());
        orderItemDao.save(order);
        return id;
    }

    @Override
    public Order getOrder(Long id) throws OrderNotFound {
        Optional<Order> order = orderDao.get(id);
        if (!order.isPresent()) {
            throw new OrderNotFound("No order with number " + id + " found");
        }
        order.get().setOrderItems(orderItemDao.getOrderItems(id));
        return order.get();
    }

    @Override
    public Order getOrderBySecureId(String id) throws OrderNotFound {
        Optional<Order> order = orderDao.getBySecureId(id);
        if (!order.isPresent()) {
            throw new OrderNotFound("No order found");
        }
        order.get().setOrderItems(orderItemDao.getOrderItems(order.get().getId()));
        return order.get();
    }
}
