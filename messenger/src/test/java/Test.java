import com.wso2telco.tip.messenger.api.Invoke;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by yasith on 2/18/17.
 */
public class Test {
    final static Logger log = LoggerFactory.getLogger(Test.class);

    @org.junit.Test
    public void TestInvoke() throws IOException, URISyntaxException {

        Invoke invoke = new Invoke();
        String senderId = "1266629966753418";
        String responseJson = "{\"recipient\":{\"id\":\"[id]\"},\"message\":{\"text\":\"[message]\"}}";
        String parsedjson = responseJson.replace("[id]",senderId).replace("[message]","Your current balance is Rs 100");
        log.info("parsed json : " + parsedjson);
        invoke.sendMessage(parsedjson);
    }

    @org.junit.Test
    public void balanceTest() throws IOException {
        Invoke invoke = new Invoke();
        System.out.println(invoke.getBalance("94771252682"));

    }
}
