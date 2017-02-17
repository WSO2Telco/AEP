package com.wso2telco.tip.messenger.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    public Response callback(String jsonBody) {
        if(log.isInfoEnabled())
            log.info(jsonBody);
        return Response.status(HttpServletResponse.SC_OK).build();
    }

}
