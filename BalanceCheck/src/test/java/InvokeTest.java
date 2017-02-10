import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wso2telco.tip.dao.impl.ReferenceDaoImpl;
import com.wso2telco.tip.exception.ErrorCodes;
import com.wso2telco.tip.model.references.Reference;
import com.wso2telco.tip.model.references.ReferenceResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
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

        Reference reference1 = new Reference();

        reference1.setNotifyURL("111");
        reference1.setLimit(100);
        reference1.setReferenceId("1234");

        Reference reference2 = new Reference();
        reference2.setNotifyURL("111");
        reference2.setLimit(100);
        reference2.setReferenceId("1234");

        ReferenceDaoImpl referenceDao = new ReferenceDaoImpl();
        List<Reference> dialogReferenceList = new ArrayList<>();

        dialogReferenceList.add(reference1);
        dialogReferenceList.add(reference2);
        referenceResponse.setReferences(dialogReferenceList);

        try {
            jsonPayload = mapper.writeValueAsString(referenceResponse);
            System.out.println(jsonPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jsonTest(){
        JSONObject jsonResponse;
        String jsonPayload = "{\"balancelimitref\":{\"refnumber\":123}}";

        jsonResponse = new JSONObject(jsonPayload);

        JSONObject balancelimitref = (JSONObject) jsonResponse.get("balancelimitref");
        System.out.println(balancelimitref.toString());
        int refnumber = (int) balancelimitref.get("refnumber");
        System.out.println(refnumber);

    }

    @Test
    public void errorJsonTest(){
        JSONObject error = new JSONObject();
        JSONObject requestJson = new JSONObject();
        requestJson.put("code", ErrorCodes.INTERNAL_SERVER_ERROR.getKey());
        requestJson.put("message", ErrorCodes.INTERNAL_SERVER_ERROR.getCode());
        error.put("Error", requestJson);
        System.out.println(error.toString());
    }

    @Test
    public void deleteTest(){
        JSONObject balancelimitref = new JSONObject();
        JSONObject requestJson = new JSONObject();
        requestJson.put("refnumber","123");
        balancelimitref.put("balancelimitref",requestJson);

        System.out.println(balancelimitref.toString());
    }


}
