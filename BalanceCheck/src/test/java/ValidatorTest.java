import com.wso2telco.tip.util.Validator;
import org.junit.Test;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by yasith on 1/26/17.
 */
public class ValidatorTest {

    @Test
    public void Test(){
        assertTrue("Valid Number tel:+91123456789",Validator.validateTelMsisdn("tel:+91123456789"));
        assertFalse("InValid Number tel:+9112345678",Validator.validateTelMsisdn("tel:+9112345678"));
        assertFalse("InValid Number tel:+911234567890",Validator.validateTelMsisdn("tel:+911234567890"));
        assertFalse("InValid Number 91123456789",Validator.validateTelMsisdn("91123456789"));
        assertFalse("InValid Number tl:+91123456789",Validator.validateTelMsisdn("tl:+91123456789"));
    }

    @Test
    public void MsisdnTest(){
        assertTrue("Valid Number 94123456789",Validator.validateMsisdn("94771353682"));
        assertFalse("InValid Number tel:+9112345678",Validator.validateTelMsisdn("tel:+9112345678"));
        assertFalse("InValid Number tel:+911234567890",Validator.validateTelMsisdn("tel:+911234567890"));
        assertFalse("InValid Number 91123456789",Validator.validateTelMsisdn("91123456789"));
        assertFalse("InValid Number tl:+91123456789",Validator.validateTelMsisdn("tl:+91123456789"));
    }

    @Test
    public void urlTest(){
        assertTrue("Valid url http://www.google.com",Validator.validateUrl("http://www.google.com"));
        assertTrue("Valid url http://www.google.com/test",Validator.validateUrl("http://www.google.com/test"));
        assertTrue("Valid url http://192.168.1.2/test",Validator.validateUrl("http://192.168.1.2/test"));
        assertTrue("Valid url http://192.168.1.2:8080/test",Validator.validateUrl("http://192.168.1.2:8080/test"));
        assertTrue("Valid url 192.168.1.2:8080/test",Validator.validateUrl("192.168.1.2:8080/test"));
        assertTrue("Valid url www.google.com/test",Validator.validateUrl("www.google.com/test"));
        assertTrue("Valid url https://www.google.com",Validator.validateUrl("https://www.google.com"));
        assertTrue("Valid url 192.168.1.2",Validator.validateUrl("192.168.1.2"));
        assertTrue("Valid url 192.168.1.2:8080",Validator.validateUrl("192.168.1.2:8080"));
        assertTrue("Valid url google.com",Validator.validateUrl("google.com"));

        assertFalse("InValid url tcp://www.google.com/",Validator.validateUrl("tcp://www.google.com/"));
        assertFalse("InValid url 12345",Validator.validateUrl("12345"));

    }

}
