package com.wso2telco.tip.messenger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wso2telco.tip.messenger.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;


/**
 * Created by yasith on 1/18/17.
 */

@Path("/messenger")
public class Endpoints {

    final static Logger log = LoggerFactory.getLogger(Endpoints.class);

    @GET
    @Path("/version")
    public String printVersion() {
        if(log.isInfoEnabled())
            log.info("version endpoint called");
        return "v1.0";
    }

    @GET
    @Path("/callback")
    public Response callback(@QueryParam("hub.challenge") String challenge, @QueryParam("hub.verify_token") String token) {
        if(log.isInfoEnabled()) {
            log.info("callback GET invoked : ");
            log.info("hub.challenge : " + challenge);
            log.info("hub verify token : " + token);
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", MediaType.TEXT_PLAIN).entity(challenge).build();
    }

    @POST
    @Path("/callback")
    public Response callback(String jsonBody) {

        String responseJson = "{\"recipient\":{\"id\":\"[id]\"},\"message\":{\"text\":\"[message]\"}}";
        if(log.isInfoEnabled())
            log.info("callback POST invoked : " + jsonBody);
        ObjectMapper mapper = new ObjectMapper();
        try {
            MessageResponse messageResponse = mapper.readValue(jsonBody.toString(), MessageResponse.class);
            List<Entry> entries = messageResponse.getEntry();
            Entry entry = entries.get(0);
            List<Messaging> messages = entry.getMessaging();
            Messaging messaging = messages.get(0);
            Message message = messaging.getMessage();
            Sender sender = messaging.getSender();
            String senderId = sender.getId();
            String text = message.getText();
            String parsedjson;

            if(log.isInfoEnabled()){
                log.info("message : " + message);
                log.info("sender Id : " + senderId);
            }
            Invoke invoke = new Invoke();


            if(text.toLowerCase().startsWith("get balance")){
                String msisdn = text.split(" ")[2];
                if(log.isInfoEnabled()){
                    log.info("msisdn : " + msisdn);
                }
                String balance = invoke.getBalance(msisdn);
                parsedjson = responseJson.replace("[id]",senderId).replace("[message]","Your current balance is Rs "+balance);
            }else{
                parsedjson = responseJson.replace("[id]",senderId).replace("[message]","type get balance [your_phone_number] get balance 94xxxxxxxxx");
            }

            if(log.isInfoEnabled()){
                log.info("parsed Json : " + parsedjson);
            }

            invoke.sendMessage(parsedjson);
        } catch (Exception e) {
            log.error("Exception occured",e);
            return Response.status(HttpServletResponse.SC_OK).build();
        }
        return Response.status(HttpServletResponse.SC_OK).build();
    }

}
