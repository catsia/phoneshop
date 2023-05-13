package core.model.phone;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.dao.OrderDao;
import com.es.core.model.order.dao.OrderItemDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.Stock;
import com.es.core.model.phone.dao.StockDao;
import com.es.core.order.OrderNotFound;
import com.es.core.order.OrderService;
import com.es.core.order.OrderServiceImpl;
import com.es.core.order.OutOfStockException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private final Long idFromSave = 1L;
    @Mock
    private Cart mockCart;
    @Mock
    private OrderDao mockOrderDao;

    @Mock
    private OrderItemDao mockOrderItemDao;

    @Mock
    private StockDao mockStockDao;

    private Order order;

    @InjectMocks
    private OrderService orderService = new OrderServiceImpl(mockOrderDao, mockOrderItemDao, mockStockDao);


    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Phone phone = new Phone();
            phone.setBrand("brand" + i);
            phone.setModel("model" + i);
            phone.setPrice(new BigDecimal(100 + i));
            phone.setId(1000L + i);
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(phone);
            orderItem.setQuantity(1L);
            orderItems.add(orderItem);
            cartItems.add(new CartItem(phone, 1L));
        }
        order = new Order();
        order.setOrderItems(orderItems);

        Stock stock = new Stock();
        stock.setStock(2);

        when(mockCart.getCartItems()).thenReturn(cartItems);
        when(mockCart.getTotalCost()).thenReturn(new BigDecimal(303));

        when(mockOrderDao.save(order)).thenReturn(idFromSave);
        when(mockOrderDao.get(1L)).thenReturn(Optional.ofNullable(order));

        when(mockStockDao.get(anyLong())).thenReturn(Optional.of(stock));

        BigDecimal newDeliveryPrice = new BigDecimal("5");

        Field field = orderService.getClass().getDeclaredField("deliveryPrice");
        field.setAccessible(true);
        field.set(orderService, newDeliveryPrice);
    }

    @Test
    public void testCreateOrder() {
        Order testOrder = orderService.createOrder(mockCart);

        verify(mockCart).getCartItems();
        verify(mockCart, atLeast(2)).getTotalCost();

        Assert.assertEquals(testOrder.getSubtotal(), mockCart.getTotalCost());
        Assert.assertEquals(testOrder.getOrderItems().size(), mockCart.getCartItems().size());
    }

    @Test
    public void testPlaceOrderWithNormalStock() throws OutOfStockException {
        Long id = orderService.placeOrder(order);

        verify(mockOrderDao).save(order);
        verify(mockStockDao, atLeast(3)).get(anyLong());
        verify(mockOrderItemDao).save(order);

        Assert.assertEquals(id, idFromSave);
    }

    @Test(expected = OutOfStockException.class)
    public void testPlaceOrderWithMoreThanInStock() throws OutOfStockException {
        order.getOrderItems().get(0).setQuantity(3L);

        orderService.placeOrder(order);
    }

    @Test
    public void testGetExistingOrder() throws OrderNotFound {
        Order newOrder = orderService.getOrder(1L);

        verify(mockOrderDao).get(1L);
        verify(mockOrderItemDao).getOrderItems(1L);

        Assert.assertEquals(newOrder, order);
    }

    @Test(expected = OrderNotFound.class)
    public void testGetNonExistingOrder() throws OrderNotFound {
        orderService.getOrder(2L);
    }
}
