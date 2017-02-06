package com.wso2telco.tip.client;

import com.wso2telco.tip.conf.ConfigReader;
import com.wso2telco.tip.exception.BalanceCheckException;
import com.wso2telco.tip.exception.ErrorCodes;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
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
import java.util.Map;

/**
 * Created by yasith on 1/26/17.
 */
public class Invoke {

    final static Logger log = LoggerFactory.getLogger(Invoke.class);

    String remoteUrl = null;
    String balanceUp = null;
    String balanceDown = null;

    public Invoke(){
        ConfigReader configReader = ConfigReader.getInstance();
        Map<Object,Object> callback = (Map<Object, Object>) configReader.getApplicationConfiguration().getCallback();
        Map<Object,Object> remote = (Map<Object, Object>) configReader.getApplicationConfiguration().getRemote();
        balanceUp = (String) callback.get("balanceUp");
        balanceDown = (String) callback.get("balanceDown");
        remoteUrl = (String) remote.get("url");
    }

    public JSONObject sendPost(String msisdn, int limit) throws BalanceCheckException {

        if(log.isDebugEnabled()){
            log.debug("Notify URL Balance UP   : " + balanceUp);
            log.debug("Notify URL Balance Down : " + balanceDown);
            log.debug("Remote URL : " + remoteUrl);
        }

        JSONObject requestJson = new JSONObject();
        requestJson.put("limit",limit);
        requestJson.put("notifyURL_balance_up",balanceUp);
        requestJson.put("notifyURL_balance_down",balanceDown);

        if (log.isDebugEnabled()) {
            log.debug("Request JSON : " + requestJson.toString());
        }

        UriBuilder builder = UriBuilder.fromPath(remoteUrl);
        URI callUrl = builder.build(msisdn);

        if (log.isDebugEnabled()) {
            log.debug("Remote Call URL : " + callUrl);
        }

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(callUrl);
        HttpResponse response;
        JSONObject jsonResponse;

        StringEntity stringEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        post.setHeader("Content-type", "application/json");

        try {
            response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity());
            jsonResponse = new JSONObject(responseString);
            if(log.isDebugEnabled()){
                log.debug("Response Code : " + response.getStatusLine().getStatusCode());
                log.debug("JSON Response : " + jsonResponse.toString());
            }
        } catch (IOException e) {
            log.error("IO exception while calling remote URL : " + callUrl);
            log.error("IO exception while calling remote URL : " , e);
            throw new BalanceCheckException(ErrorCodes.IO_EXCEPTION.getCode() , e);
        }
        return jsonResponse;
    }


    public JSONObject sendGet(String msisdn) throws BalanceCheckException {

        UriBuilder builder = UriBuilder.fromPath(remoteUrl);
        URI callUrl = builder.build(msisdn);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(callUrl);

        HttpResponse response;
        JSONObject jsonResponse;

        try {
            response = client.execute(request);
            String responseString = EntityUtils.toString(response.getEntity());
            jsonResponse = new JSONObject(responseString);
            if(log.isDebugEnabled()){
                log.debug("Response Code : " + response.getStatusLine().getStatusCode());
                log.debug("JSON Response : " + jsonResponse.toString());
            }
        } catch (IOException e) {
            log.error("Exception Occured while Calling GET request for MSISDN : " + msisdn + "\r\n" , e);
            throw new BalanceCheckException(ErrorCodes.IO_EXCEPTION.getCode());
        }

        return jsonResponse;
    }

    public JSONObject sendDelete(String msisdn, int reference) throws BalanceCheckException {

        UriBuilder builder = UriBuilder.fromPath(remoteUrl);
        URI callUrl = builder.build(msisdn);
        HttpClient client = HttpClientBuilder.create().build();
        HttpDeleteWithBody request = new HttpDeleteWithBody(callUrl);
        JSONObject requestJson = new JSONObject();
        requestJson.put("refnumber",reference);

        if (log.isDebugEnabled()) {
            log.debug("Request JSON : " + requestJson.toString());
        }

        StringEntity stringEntity = new StringEntity(requestJson.toString(), ContentType.APPLICATION_JSON);
        request.setEntity(stringEntity);
        request.setHeader("Content-type", "application/json");
        HttpResponse response;
        JSONObject jsonResponse;

        try {
            response = client.execute(request);
            String responseString  = EntityUtils.toString(response.getEntity());
            jsonResponse = new JSONObject(responseString);
            if(log.isDebugEnabled()){
                log.debug("Response Code : " + response.getStatusLine().getStatusCode());
                log.debug("JSON Response : " + jsonResponse.toString());
            }
        } catch (IOException e) {
            log.error("Exception Occured while Calling DELETE request for MSISDN : " + msisdn + "\r\n" , e);
            throw new BalanceCheckException(ErrorCodes.IO_EXCEPTION.getCode());
        }
        return jsonResponse;
    }

    @NotThreadSafe
    class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";

        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }
    }

}
