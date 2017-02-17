package com.wso2telco.tip.messenger.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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
        if(log.isInfoEnabled())
            log.info("callback POST invoked : " + jsonBody);
        return Response.status(HttpServletResponse.SC_OK).build();
    }

}
