package com.wso2telco.tip.balance.client;

import com.wso2telco.tip.balance.conf.ConfigReader;
import com.wso2telco.tip.balance.publish.DataPublisher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * Created by yasith on 2/21/17.
 */
public class Invoke {

    private Log log = LogFactory.getLog(Invoke.class);

    String url = null;
    boolean isRemoteEnabled=true;

    public Invoke(){
        ConfigReader configReader = ConfigReader.getInstance();
        Map<Object,Object> remote = (Map<Object, Object>) configReader.getApplicationConfiguration().getRemote();
        url = (String) remote.get("url");
        isRemoteEnabled = (Boolean) remote.get("isEnabled");
    }

    public JSONObject getBalance(String msisdn) throws Exception {
        if(isRemoteEnabled){
            if(log.isInfoEnabled())
                log.info("remote enabled, invoking the operator api");
            return sendGet(msisdn.substring(2));
        }else{
            if(log.isInfoEnabled())
                log.info("remote disabled, invoking the dummy api");
            String jsonString = "{\"accountInfo\":{\"accountStatus\":\"ACTIVE\",\"balance\":[amount],\"accountType\":\"POSTPAID\",\"creditLimit\":[amount]},\"endUserId\":\"[msisdn]\",\"referenceCode\":\"dummyAPI\"}";
            DataPublisher dataPublisher = new DataPublisher();
            String amount = dataPublisher.getAmount(msisdn);
            if(amount == null || amount.isEmpty()){
                if(log.isInfoEnabled())
                    log.info("msisdn not found on the dummy dataset, creating new entry with default amount");
                dataPublisher.publishData(msisdn,"100.00");
                amount = "100.00";
            }

            jsonString = jsonString.replace("[amount]",amount).replace("[msisdn]",msisdn);
            if(log.isInfoEnabled())
                log.info("json String for dummy api: " + jsonString);

            JSONObject jsonResponse = new JSONObject(jsonString);
            return jsonResponse;
        }
    }

    public JSONObject sendGet(String msisdn) throws Exception {

        UriBuilder builder = UriBuilder.fromPath(url);
        URI callUrl = builder.build(msisdn);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(callUrl);

        if(log.isInfoEnabled())
            log.info("Request Url : " + callUrl.toString());

        HttpResponse response;
        JSONObject jsonResponse;
        response = client.execute(request);
        String responseString = EntityUtils.toString(response.getEntity());
        String parsedResponse = responseString.substring(1,responseString.length()-1).replace("\\","");
        if(log.isInfoEnabled())
            log.info("response String : " + parsedResponse);
        jsonResponse = new JSONObject(parsedResponse);
        if(log.isInfoEnabled()){
            log.info("Response Code : " + response.getStatusLine().getStatusCode());
            log.info("JSON Response : " + jsonResponse.toString());
        }
        return jsonResponse;
    }
}
