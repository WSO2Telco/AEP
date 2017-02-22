package com.wso2telco.tip.balance.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wso2telco.tip.balance.exception.ErrorCodes;
import com.wso2telco.tip.balance.client.Invoke;
import com.wso2telco.tip.balance.publish.DataPublisher;
import com.wso2telco.tip.balance.util.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by yasith on 2/21/17.
 */

@Path("/balance")
public class Endpoints {

    private Log log = LogFactory.getLog(Endpoints.class);
    @GET
    @Path("/version")
    public String getVersion(){
        if(log.isInfoEnabled())
            log.info("version endpoint invoked");
        return "v1.0";
    }

    @GET
    @Path("{msisdn}")
    public Response getBalance(@PathParam("msisdn") String msisdn){
        if(log.isInfoEnabled())
            log.info("get balance for the msisdn : " + msisdn);

        if(!Validator.validateMsisdn(msisdn)) {
            if(log.isInfoEnabled())
                log.info("msisdn format error : " + msisdn);
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.MSISDN_FORMAT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.MSISDN_FORMAT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        Invoke invoke = new Invoke();
        JSONObject requestJson = null;
        try {
            requestJson = invoke.getBalance(msisdn.substring(2));
        } catch (Exception e) {
            log.error("Error Occured for the msisdn : " + msisdn, e);
            requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INTERNAL_SERVER_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INTERNAL_SERVER_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(requestJson.toString()).build();

    }

    @POST
    @Path("{msisdn}")
    public Response topup(@PathParam("msisdn") String msisdn, String jsonBody){

        if(log.isInfoEnabled()) {
            log.info("get balance for the msisdn : " + msisdn);
            log.info("json body : " + jsonBody);
        }

        if(!Validator.validateMsisdn(msisdn)) {
            if(log.isInfoEnabled())
                log.info("msisdn format error : " + msisdn);
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.MSISDN_FORMAT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.MSISDN_FORMAT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        JsonParser parser = new JsonParser();
        JSONObject jsonObjectBalance;
        Invoke invoke = new Invoke();
        JsonObject jsonObject = parser.parse(jsonBody).getAsJsonObject();
        DataPublisher dataPublisher = new DataPublisher();
        JsonElement rechargeAmountElement = jsonObject.get("rechargeAmount");
        double rechargeAmount = rechargeAmountElement.getAsDouble();
        if(log.isInfoEnabled())
            log.info("recharge amount : " + rechargeAmount);

        String existingBalance = dataPublisher.getAmount(msisdn);
        if(existingBalance == null || existingBalance.isEmpty()){
            if(log.isInfoEnabled())
                log.info("no msisdn found in the dummy dataset");
            existingBalance = "100.00";
        }

        if(log.isInfoEnabled())
            log.info("Existing Balance : " + existingBalance);

        double existingBalanceValue = Double.parseDouble(existingBalance);
        double newAmount = existingBalanceValue + rechargeAmount;

        if(log.isInfoEnabled())
            log.info("new amount : " + newAmount);
        dataPublisher.publishData(msisdn,String.valueOf(newAmount));

        try {
            jsonObjectBalance = invoke.getBalance(msisdn);
        } catch (Exception e) {
            log.error("exception occured while POST : " + msisdn, e);
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INTERNAL_SERVER_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INTERNAL_SERVER_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonObjectBalance.toString()).build();
    }
}
