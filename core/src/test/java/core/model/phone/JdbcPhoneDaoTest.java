package core.model.phone;

import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@ContextConfiguration("/context/applicationContext-test.xml")
public class JdbcPhoneDaoTest {

    @Resource
    PhoneDao jdbcPhoneDao;

    @Test
    public void testFindAllReturnsNotNull() {
        Assert.assertNotNull(jdbcPhoneDao.findAll(0, 10));
    }

    @Test
    public void testFindAllSizeEqualsLimit() {
        int limit = 10;
        Assert.assertEquals(jdbcPhoneDao.findAll(0, limit).size(), limit);
    }

    @Test
    public void testSaveNewObject() {
        Phone phone = new Phone();
        phone.setBrand("brand");
        phone.setModel("model");
        jdbcPhoneDao.save(phone);
        Assert.assertEquals(phone.getBrand(), jdbcPhoneDao.get(8764L).get().getBrand());
    }

    @Test
    public void testGetExistingObject() {
        Long id = 1000L;
        if (jdbcPhoneDao.get(id).isPresent()) {
            Assert.assertEquals(id, jdbcPhoneDao.get(id).get().getId());
        }
    }

    @Test
    public void testGetNonExistingObject() {
        Long id = 1L;
        Assert.assertFalse(jdbcPhoneDao.get(id).isPresent());
    }
}
