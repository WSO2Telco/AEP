package com.wso2telco.tip.messenger.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by yasith on 2/18/17.
 */
public class Invoke {

    final static String REMOTE_URL = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAaTZA5zMvxUBABkbuvxcvlgDFif7AjALrX2lGDsvrNuCN6FotbZBae6cJBCFXUfr0JdZBtZCCkD14MpDFKNOnzP6H6aHhHuM5DA3IZAEDZBIsE2FvZCtnU9XnQtdvqeyN4mccx0wDFbqK1Nuy3HY0uecB8qkZBGPhY8xOoRfvRlKQZDZD";
    final static Logger log = LoggerFactory.getLogger(Invoke.class);
    final static String BALANCE_REMOTE_URL = "http://209.249.227.63:8280/accountbalance/v1/{msisdn}";
    public void sendMessage(String jsonbody) throws IOException, URISyntaxException {

        //UriBuilder builder = UriBuilder.fromPath(REMOTE_URL);
        //URI callUrl = builder.build();

        URI uri = new URI(REMOTE_URL);
        if (log.isDebugEnabled()) {
            log.debug("Remote Call URL : " + uri);
        }
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uri);
        HttpResponse response;
        StringEntity stringEntity = new StringEntity(jsonbody, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        post.setHeader("Content-type", "application/json");
        response = client.execute(post);
        String responseString = EntityUtils.toString(response.getEntity());
        if (log.isDebugEnabled()) {
            log.debug("response String: " + responseString);
        }
    }

    public String getBalance(String msisdn) throws IOException{

        UriBuilder builder = UriBuilder.fromPath(BALANCE_REMOTE_URL);
        URI callUrl = builder.build(msisdn);

        if (log.isDebugEnabled()) {
            log.debug("Remote Call URL : " + callUrl);
        }
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(callUrl);
        request.setHeader("Authorization","Bearer 922f38fe-d4a7-3872-b995-83886ef7c00f");
        HttpResponse response;
        JSONObject jsonResponse;

        response = client.execute(request);
        String responseString = EntityUtils.toString(response.getEntity());
        jsonResponse = new JSONObject(responseString);
        if(log.isDebugEnabled()){
            log.debug("Response Code : " + response.getStatusLine().getStatusCode());
            log.debug("JSON Response : " + jsonResponse.toString());
        }

        JSONObject accinfo  = jsonResponse.getJSONObject("accountInfo");
        double balanceValue = accinfo.getDouble("balance");
        String balance = String.valueOf(balanceValue);
        return balance;
    }
}
