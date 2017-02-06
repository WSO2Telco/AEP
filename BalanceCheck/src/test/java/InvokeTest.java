import com.wso2telco.tip.client.Invoke;
import com.wso2telco.tip.exception.BalanceCheckException;
import com.wso2telco.tip.exception.ErrorCodes;
import org.apache.http.client.utils.URIBuilder;
import org.glassfish.jersey.uri.UriTemplate;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by yasith on 1/27/17.
 */
public class InvokeTest {

    @Test
    public void sendPostTest(){
        Invoke invoker = new Invoke();
        //JSONObject resultJson = invoker.sendPost("91123456789",500);
        //System.out.println(resultJson);

    }

    @Test
    public void urlCheck(){
        String template = "http://www.wso2telco.com/something/{msisdn}";
        UriBuilder builder = UriBuilder.fromPath(template);
        URI output = builder.build("94771353682");
        //System.out.println(output.toString());
    }
}
