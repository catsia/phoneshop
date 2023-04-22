package core.model.phone;

import com.es.core.cart.CartService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-test.xml")
public class HttpSessionCartServiceTest {

    @Resource
    private CartService cartService;

    @Test
    public void testAddPhoneAddsPhone() {
        cartService.addPhone(1001L, 1L);
        cartService.addPhone(1002L, 1L);
        Assert.assertEquals(Long.valueOf(2L), cartService.getCart().getTotalQuantity());
    }

    @Test
    public void testAddPhoneThatWasAlreadyInCart() {
        Long previousQuantity = cartService.getCart().getTotalQuantity();
        int previousSize = cartService.getCart().getCartItems().size();
        cartService.addPhone(1001L, 1L);
        cartService.addPhone(1001L, 1L);
        Assert.assertEquals(Long.valueOf(previousQuantity + 2L), cartService.getCart().getTotalQuantity());
        Assert.assertEquals(previousSize, cartService.getCart().getCartItems().size());
    }

    @Test
    public void testAddPhoneNot() {
        int previousSize = cartService.getCart().getCartItems().size();
        cartService.addPhone(Long.MIN_VALUE, 1L);
        cartService.addPhone(Long.MAX_VALUE, 1L);
        Assert.assertEquals(previousSize, cartService.getCart().getCartItems().size());
    }
}
