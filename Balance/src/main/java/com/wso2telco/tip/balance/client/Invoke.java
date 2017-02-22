package com.wso2telco.tip.balance.client;

import com.wso2telco.tip.balance.conf.ConfigReader;
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

    public Invoke(){
        ConfigReader configReader = ConfigReader.getInstance();
        Map<Object,Object> dialog = (Map<Object, Object>) configReader.getApplicationConfiguration().getRemote();
        url = (String) dialog.get("url");
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
        String parsedResponse = responseString.substring(1,responseString.length()-1);
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
