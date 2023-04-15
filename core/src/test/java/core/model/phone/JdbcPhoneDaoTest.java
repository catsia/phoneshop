package core.model.phone;

import com.es.core.model.phone.JdbcPhoneDao;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.PhoneDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

@ContextConfiguration(locations = {"context/applicationContext-core.xml"})
public class JdbcPhoneDaoTest {

    PhoneDao jdbcPhoneDao;

    JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        jdbcPhoneDao = new JdbcPhoneDao();
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("/db/schema.sql")
                .addScript("file:C:\\Users\\Katya\\Documents\\GitHub\\phoneshop\\demodata\\src\\main\\resources\\db\\demodata-phones.sql")
                .build();
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        ReflectionTestUtils.setField(jdbcPhoneDao, "jdbcTemplate", jdbcTemplate);
    }

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
    public void testSaveObject() {
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
