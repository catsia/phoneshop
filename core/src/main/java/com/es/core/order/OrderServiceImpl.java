package com.es.core.order;

import com.es.core.cart.Cart;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.dao.OrderDao;
import com.es.core.model.order.dao.OrderItemDao;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.StockDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private StockDao stockDao;


    @Override
    public Order createOrder(Cart cart) {
        Order order = new Order();
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
    public void placeOrder(Order order) throws OutOfStockException {
        order.setId(orderDao.save(order));
        for (OrderItem orderItem : order.getOrderItems()) {
            Stock stock = stockDao.get(orderItem.getPhone().getId()).get();
            if (stock.getStock() < orderItem.getQuantity()) {
                throw new OutOfStockException("Phone " + orderItem.getPhone().getModel() + "doesn't have " + orderItem.getQuantity() + "left in stock");
            }
        }
        order.getOrderItems().removeIf(orderItem -> stockDao.get(orderItem.getPhone().getId()).get().getStock() < orderItem.getQuantity());
        orderItemDao.save(order);
    }
}
