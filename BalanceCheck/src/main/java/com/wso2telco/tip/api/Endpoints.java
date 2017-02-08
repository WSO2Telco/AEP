package com.wso2telco.tip.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wso2telco.tip.client.Invoke;
import com.wso2telco.tip.dao.impl.ReferenceDaoImpl;
import com.wso2telco.tip.exception.BalanceCheckException;
import com.wso2telco.tip.exception.ErrorCodes;
import com.wso2telco.tip.model.references.Reference;
import com.wso2telco.tip.model.references.ReferenceResponse;
import com.wso2telco.tip.util.Validator;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yasith on 1/18/17.
 */

@Path("/balance")
public class Endpoints {

    final static Logger log = LoggerFactory.getLogger(Endpoints.class);

    @GET
    @Path("/version")
    public String print(){
        return "v1.0";
    }

    @GET
    @Path("{msisdn}")
    public Response getBalanceLimit(@PathParam("msisdn") String msisdn){
        String jsonPayload = null;
        if(log.isInfoEnabled())
            log.info("msisdn : " + msisdn);

        if(!Validator.validateMsisdn(msisdn)) {
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.MSISDN_FORMAT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.MSISDN_FORMAT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        ObjectMapper mapper = new ObjectMapper();
        ReferenceResponse referenceResponse = new ReferenceResponse();
        ReferenceDaoImpl referenceDao = new ReferenceDaoImpl();
        List<String> dialogReferenceList = referenceDao.getDialogReferenceListForMsisdn(msisdn);
        List<Reference> references = new ArrayList<>();
        if(dialogReferenceList == null || dialogReferenceList.isEmpty()){
            referenceResponse = null;
        }else{
            for (String dialogReference: dialogReferenceList) {
                Reference reference = new Reference();
                String telcoReference = referenceDao.getTelcoReferenceFromDialogReference(dialogReference);
                String limit = referenceDao.getLimitFromDialogReference(dialogReference);
                String callback = referenceDao.getCallbackFromDialogReference(dialogReference);
                int balancelimit = Integer.parseInt(limit);
                reference.setReferenceId(telcoReference);
                reference.setLimit(balancelimit);
                reference.setNotifyURL(callback);
                references.add(reference);
            }
            referenceResponse.setReferences(references);
        }
        try {
            jsonPayload = mapper.writeValueAsString(referenceResponse);
        } catch (JsonProcessingException e) {
            log.error("JSON Processing Error" , e);
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.JSON_PROCESSING_ERROR.getKey());
            requestJson.put("message", ErrorCodes.JSON_PROCESSING_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonPayload).build();
    }

    @POST
    @Path("{msisdn}")
    public Response setBalanceLimit(@PathParam("msisdn") String msisdn, String jsonbody){

        JSONObject resultJson;
        int limit = 0;
        int dialogReferenceNumber;
        String dialogReference;
        String telcoReference;
        String notifyUrl = null;
        String limitStr;

        if(log.isInfoEnabled()) {
            log.info("msisdn : " + msisdn);
            log.info("json body : " + jsonbody);
        }

        if(!Validator.validateMsisdn(msisdn)) {
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.MSISDN_FORMAT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.MSISDN_FORMAT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        ReferenceDaoImpl referenceDao = new ReferenceDaoImpl();
        Invoke invoke =  new Invoke();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonbody).getAsJsonObject();
        JsonElement limitElement = jsonObject.get("limit");
        JsonElement notifyUrlElement = jsonObject.get("notifyURL");

        if((limitElement instanceof JsonNull) || (notifyUrlElement instanceof JsonNull)){
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INPUT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INPUT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }else{
            limit = limitElement.getAsInt();
            notifyUrl = notifyUrlElement.getAsString();
        }

        if(!Validator.validateUrl(notifyUrl)) {
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.URL_FORMAT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.URL_FORMAT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        try {
            resultJson = invoke.sendPost(msisdn, limit);
            if(log.isInfoEnabled())
                log.info("Json result " + resultJson);
            dialogReferenceNumber = (int) resultJson.get("refnumber");
            dialogReference = String.valueOf(dialogReferenceNumber);
            limitStr = String.valueOf(limit);
            telcoReference = UUID.randomUUID().toString();
            referenceDao.setReferenceEntry(telcoReference, dialogReference);
            referenceDao.setCallbackEntry(dialogReference, telcoReference, msisdn, limitStr, notifyUrl);
            referenceDao.setMsisdnReferenceEntry(msisdn, dialogReference);
        } catch (BalanceCheckException e) {
            log.error("Error While Sending POST" , e);
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INTERNAL_SERVER_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INTERNAL_SERVER_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        } catch (JSONException e){
            log.error("Error While Sending POST" , e);
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.JSON_PROCESSING_ERROR.getKey());
            requestJson.put("message", ErrorCodes.JSON_PROCESSING_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        return Response.status(HttpServletResponse.SC_CREATED).build();
    }

    @DELETE
    @Path("{msisdn}")
    public Response deleteBalanceLimit(@PathParam("msisdn") String msisdn, String jsonbody){

        String telcoReference;
        String dialogReference;
        int dialogReferenceNumber;

        if(log.isInfoEnabled())
            log.info("msisdn : " + msisdn);

        if(!Validator.validateMsisdn(msisdn)) {
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.MSISDN_FORMAT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.MSISDN_FORMAT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        ReferenceDaoImpl referenceDao = new ReferenceDaoImpl();
        Invoke invoke =  new Invoke();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonbody).getAsJsonObject();
        JsonElement referenceIdElement = jsonObject.get("referenceId");

        if((referenceIdElement instanceof JsonNull)){
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INPUT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INPUT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }else{
            telcoReference = referenceIdElement.getAsString();
        }

        dialogReference = referenceDao.getDialogReferenceFromTelcoReference(telcoReference);
        dialogReferenceNumber = Integer.parseInt(dialogReference);
        try {
            invoke.sendDelete(msisdn,dialogReferenceNumber);
        } catch (BalanceCheckException e) {
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INTERNAL_SERVER_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INTERNAL_SERVER_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }

        JSONObject requestJson = new JSONObject();
        requestJson.put("referenceId", telcoReference);

        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(requestJson.toString()).build();
    }

    @POST
    @Path("/callback/up")
    public Response upCallback(String jsonBody){
        String jsonPayload = null;
        String dialogReference;
        String msisdn;
        String balanceLimit;
        String triggerType;

        if(log.isInfoEnabled())
            log.info("callback up json body : " + jsonBody);

        Invoke invoke =  new Invoke();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonBody).getAsJsonObject();

        JsonElement referenceIdElement = jsonObject.get("refNumber");
        JsonElement msisdnElement = jsonObject.get("msisdn");
        JsonElement balanceElement = jsonObject.get("BalanceLimit");
        JsonElement triggerTypeElement = jsonObject.get("trigger_type");

        if((referenceIdElement instanceof JsonNull) || (msisdnElement instanceof JsonNull) || (balanceElement instanceof JsonNull) || (triggerTypeElement instanceof JsonNull)){
            JSONObject requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INPUT_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INPUT_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_BAD_REQUEST).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }else{
            dialogReference = referenceIdElement.getAsString();
            msisdn = msisdnElement.getAsString();
            balanceLimit = balanceElement.getAsString();
            triggerType = balanceElement.getAsString();
        }

        return Response.status(HttpServletResponse.SC_OK).build();
    }

    @POST
    @Path("/callback/down")
    public Response downCallback(String jsonBody){
        String jsonPayload = null;
        if(log.isInfoEnabled())
            log.info("callback down json body : " + jsonBody);

        //return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonPayload).build();
    }
}
