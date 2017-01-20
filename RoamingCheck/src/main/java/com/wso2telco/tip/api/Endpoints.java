package com.wso2telco.tip.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by yasith on 1/17/17.
 */

@Path("/status")
public class Endpoints {

    @Path("{msisdn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response check(@PathParam("msisdn") String msisdn){
        return null;
    }
}
