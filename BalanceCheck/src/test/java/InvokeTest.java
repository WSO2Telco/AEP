import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wso2telco.tip.client.Invoke;
import com.wso2telco.tip.dao.impl.ReferenceDaoImpl;
import com.wso2telco.tip.exception.BalanceCheckException;
import com.wso2telco.tip.exception.ErrorCodes;
import com.wso2telco.tip.model.references.ReferenceResponse;
import org.apache.http.client.utils.URIBuilder;
import org.glassfish.jersey.uri.UriTemplate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by yasith on 1/27/17.
 */
public class InvokeTest {

    @Test
    public void sendPostTest(){
        //Invoke invoker = new Invoke();
        //JSONObject resultJson = invoker.sendPost("91123456789",500);
        //System.out.println(resultJson);
        JSONObject requestJson = new JSONObject();
        requestJson.put("code", ErrorCodes.MSISDN_FORMAT_ERROR.getKey());
        requestJson.put("message", ErrorCodes.MSISDN_FORMAT_ERROR.getCode());
        System.out.println(requestJson.toString());


        try {
            String s = (String) requestJson.get("ss");
            System.out.println(s);
        }catch (JSONException e){
            String s = (String) requestJson.get("message");
            System.out.println(s);
        }

    }

    @Test
    public void urlCheck(){
        String template = "http://www.wso2telco.com/something/{msisdn}";
        UriBuilder builder = UriBuilder.fromPath(template);
        URI output = builder.build("94771353682");
        //System.out.println(output.toString());
    }

    @Test
    public void redisCheck(){
        String entry = "http://192.168.1.1:8181/host/get::1234ert4322d";

        String url = entry.split("::")[0];
        String ref = entry.split("::")[1];

        System.out.println(url);
        System.out.println(ref);
    }

    @Test
    public void jacksonJsonTest(){
        String jsonPayload;
        ObjectMapper mapper = new ObjectMapper();
        ReferenceResponse referenceResponse = new ReferenceResponse();

        ReferenceDaoImpl referenceDao = new ReferenceDaoImpl();
        List<String> dialogReferenceList = null;

        try {
            jsonPayload = mapper.writeValueAsString(referenceResponse);
            System.out.println(jsonPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
