import com.google.gson.*;
import org.junit.Test;
import org.wso2.carbon.apimgt.api.model.Application;

/**
 * Created by yasith on 1/30/17.
 */
public class JsonTest {

    @Test
    public void testGson(){

        String fromDate = null;
        String toDate = null;
        String subscriber = null;
        String api = null;
        int applicationId = 0;
        String operator = null;
        int offset = 0;
        int count = 0;

        String jsonBody = "{\"fromDate\": null,\"toDate\":\"aa\",\"subscriber\":\"aa\",\"api\":\"ss\",\"applicationId\":1,\"operator\":\"aa\",\"offset\":1,\"count\":1}";

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonBody).getAsJsonObject();

        JsonElement fromDateElement = jsonObject.get("fromDate");
        JsonElement toDateElement = jsonObject.get("toDate");
        JsonElement subscriberElement = jsonObject.get("subscriber");
        JsonElement apiElement = jsonObject.get("api");
        JsonElement applicationIdElement = jsonObject.get("applicationId");
        JsonElement operatorElement = jsonObject.get("operator");
        JsonElement offsetElement = jsonObject.get("offset");
        JsonElement countElement = jsonObject.get("count");


        if(!(fromDateElement instanceof JsonNull)) fromDate = fromDateElement.getAsString();
        if(!(toDateElement instanceof JsonNull)) toDate = toDateElement.getAsString();
        if(!(subscriberElement instanceof JsonNull)) subscriber = subscriberElement.getAsString();
        if(!(apiElement instanceof JsonNull)) api = apiElement.getAsString();
        if(!(applicationIdElement instanceof JsonNull)) applicationId = applicationIdElement.getAsInt();
        if(!(operatorElement instanceof JsonNull)) operator = operatorElement.getAsString();
        if(!(offsetElement instanceof JsonNull)) offset = offsetElement.getAsInt();
        if(!(countElement instanceof JsonNull)) count = countElement.getAsInt();

        System.out.println(fromDate);
        System.out.println(toDate);
        System.out.println(subscriber);
        System.out.println(api);
        System.out.println(applicationId);
        System.out.println(operator);
        System.out.println(offset);
        System.out.println(count);
    }


    @Test
    public void testJson(){

        Application application1 = new Application("aaaa");
        Application application2 = new Application("ccccc");
        Application application3 = new Application("ffff");
        Application application4 = new Application("dwwwww");

        Application[] applist = {application1,application2,application3,application4};

        String jsonPayload = new Gson().toJson(applist);
        System.out.println(jsonPayload);

    }
}
