package com.wso2telco.tip.balance.invoke;

import com.wso2telco.tip.balance.conf.ResourceLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * Created by yasith on 2/21/17.
 */
public class Invoke {

    private Log log = LogFactory.getLog(Invoke.class);

    public JSONObject sendGet(String msisdn) throws Exception {

        String remoteURL = ResourceLoader.getRemoteUrl();
        UriBuilder builder = UriBuilder.fromPath(remoteURL);
        URI callUrl = builder.build(msisdn);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(callUrl);

        if(log.isInfoEnabled())
            log.info("Request Url : " + callUrl.toString());

        HttpResponse response;
        JSONObject jsonResponse;
        response = client.execute(request);
        String responseString = EntityUtils.toString(response.getEntity());
        jsonResponse = new JSONObject(responseString.substring(1,responseString.length()-1));
        if(log.isInfoEnabled()){
            log.info("Response Code : " + response.getStatusLine().getStatusCode());
            log.info("JSON Response : " + jsonResponse.toString());
        }
        return jsonResponse;
    }
}
