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
        assertTrue("Valid Number tel:+91123456789",Validator.validateMsisdn("tel:+91123456789"));
        assertFalse("InValid Number tel:+9112345678",Validator.validateMsisdn("tel:+9112345678"));
        assertFalse("InValid Number tel:+911234567890",Validator.validateMsisdn("tel:+911234567890"));
        assertFalse("InValid Number 91123456789",Validator.validateMsisdn("91123456789"));
        assertFalse("InValid Number tl:+91123456789",Validator.validateMsisdn("tl:+91123456789"));
    }
}
