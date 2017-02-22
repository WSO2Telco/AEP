import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;

/**
 * Created by yasith on 2/21/17.
 */
public class Test {

    @org.junit.Test
    public void test(){
        String jsonstring = "\"{\"endUserId\":\"94771353682\",\"referenceCode\":\"DLG28960505-1487654730628\",\"accountInfo\":{\"accountType\":\"POSTPAID\",\"accountStatus\":\"ACTIVE\",\"creditLimit\":2321.23,\"balance\":2321.23}}\"";

        String s = jsonstring.substring(1,jsonstring.length()-1);
        System.out.println(s);
        JSONObject jsonResponse = new JSONObject(s);

        String msisdn = "94771353682";
        System.out.println(msisdn.substring(2));

        System.out.println(jsonResponse.toString());
    }


    @org.junit.Test
    public void testJson(){
        String jsonResponse = "{\"accountInfo\":{\"accountStatus\":\"ACTIVE\",\"balance\":[amount],\"accountType\":\"POSTPAID\",\"creditLimit\":[amount]},\"endUserId\":\"[msisdn]\",\"referenceCode\":\"dummy\"}";

        jsonResponse = jsonResponse.replace("[amount]","500").replace("[msisdn]","94771353682");

        System.out.println(jsonResponse);
    }

    @org.junit.Test
    public void amountTest(){
        String amount = "120.45";
        double amountvalue = Double.parseDouble(amount);
        System.out.println(amountvalue);


        String jsonBody = "{\"msisdn\":\"94771353682\",\"rechargeAmount\":105.25}";
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonBody).getAsJsonObject();

        JsonElement msisdnElement = jsonObject.get("msisdn");
        JsonElement rechargeAmountElement = jsonObject.get("rechargeAmount");

        String msisdn = msisdnElement.getAsString();
        double rechargeAmount = rechargeAmountElement.getAsDouble();

        System.out.println(msisdn);
        System.out.println(rechargeAmount);
    }
}
