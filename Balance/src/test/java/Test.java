import org.json.JSONObject;

/**
 * Created by yasith on 2/21/17.
 */
public class Test {

    @org.junit.Test
    public void test(){
        String jsonstring = "{\"endUserId\":\"94771353682\",\"referenceCode\":\"DLG28960505-1487654730628\",\"accountInfo\":{\"accountType\":\"POSTPAID\",\"accountStatus\":\"ACTIVE\",\"creditLimit\":2321.23,\"balance\":2321.23}}";

        JSONObject jsonResponse = new JSONObject(jsonstring);
        String msisdn = "94771353682";
        System.out.println(msisdn.substring(2));
        System.out.println(jsonResponse.toString());
    }
}