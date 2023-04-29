package core.model.phone;

import com.es.core.cart.Cart;
import com.es.core.cart.CartItem;
import com.es.core.cart.CartService;
import com.es.core.cart.HttpSessionCartService;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {

    private List<Phone> phones;
    private List<CartItem> cartItems;
    @Mock
    private Cart mockCart;
    @Mock
    private PhoneDao mockPhoneDao;
    @InjectMocks
    private CartService cartService = new HttpSessionCartService(mockCart, mockPhoneDao);


    @Before
    public void setup() {
        phones = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Phone phone = new Phone();
            phone.setBrand("brand" + i);
            phone.setModel("model" + i);
            phone.setPrice(new BigDecimal(100 + i));
            phone.setId(1000L + i);
            phones.add(phone);
        }
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem(phones.get(0), 1L));
        cartItems.add(new CartItem(phones.get(1), 1L));
        cartItems.add(new CartItem(phones.get(2), 1L));
        when(mockCart.getCartItems()).thenReturn(cartItems);
        when(mockCart.getTotalCost()).thenReturn(new BigDecimal(303));
        when(mockCart.getTotalQuantity()).thenReturn(3L);

        for (Phone phone : phones) {
            when(mockPhoneDao.get(phone.getId())).thenReturn(Optional.of(phone));
        }
    }

    @Test
    public void testAddPhoneAddsPhone() {
        int expectedSize = cartService.getCart().getCartItems().size() + 1;

        Phone phone = phones.get(3);
        cartService.addPhone(phone.getId(), 1L);

        verify(mockPhoneDao).get(eq(phone.getId()));

        BigDecimal totalCost = phone.getPrice().add(mockCart.getTotalCost());
        verify(mockCart).setTotalCost(totalCost);

        Long totalQuantity = 1L + mockCart.getTotalQuantity();
        verify(mockCart).setTotalQuantity(eq(totalQuantity));

        Assert.assertEquals(expectedSize, cartService.getCart().getCartItems().size());
    }

    @Test
    public void testAddPhoneThatWasAlreadyInCart() {
        int previousSize = cartService.getCart().getCartItems().size();

        Phone phone = phones.get(0);
        cartService.addPhone(phone.getId(), 1L);

        verify(mockPhoneDao).get(eq(phone.getId()));

        BigDecimal totalCost = phone.getPrice().add(mockCart.getTotalCost());
        verify(mockCart).setTotalCost(totalCost);

        Long totalQuantity = 1L + mockCart.getTotalQuantity();
        verify(mockCart).setTotalQuantity(eq(totalQuantity));

        Assert.assertEquals(previousSize, cartService.getCart().getCartItems().size());
    }

    @Test
    public void testAddPhoneNot() {
        int previousSize = cartService.getCart().getCartItems().size();
        cartService.addPhone(Long.MIN_VALUE, 1L);
        cartService.addPhone(Long.MAX_VALUE, 1L);
        Assert.assertEquals(previousSize, cartService.getCart().getCartItems().size());
    }

    @Test
    public void testRemoveRemovesCorrectly() {
        int expectedSize = cartService.getCart().getCartItems().size() - 1;

        Phone phone = phones.get(2);
        cartService.remove(phone.getId());

        BigDecimal expectedCost = mockCart.getTotalCost().subtract(phone.getPrice());
        verify(mockCart).setTotalCost(eq(expectedCost));

        Assert.assertEquals(expectedSize, cartService.getCart().getCartItems().size());
    }

    @Test
    public void testRemovePhoneNotExist() {
        int expectedSize = cartService.getCart().getCartItems().size();

        cartService.remove(null);
        cartService.remove(Long.MIN_VALUE);
        cartService.remove(Long.MAX_VALUE);

        Assert.assertEquals(expectedSize, cartService.getCart().getCartItems().size());
    }

    @Test
    public void shouldUpdateCart1Correctly() {
        List<CartItem> cartItemsUpdate = new ArrayList<>();
        cartItemsUpdate.add(new CartItem(phones.get(0), 2L));
        cartItemsUpdate.add(new CartItem(phones.get(1), 2L));
        cartItemsUpdate.add(new CartItem(phones.get(2), 2L));
        cartService.update(cartItemsUpdate);

        verify(mockCart, atLeast(3)).getCartItems();

        Assert.assertEquals(3, cartService.getCart().getCartItems().size());
        Assert.assertEquals(cartItemsUpdate, cartService.getCart().getCartItems());
    }
}
