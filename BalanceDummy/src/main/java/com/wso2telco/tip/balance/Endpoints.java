package com.wso2telco.tip.balance;

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Created by yasith on 2/17/17.
 */
@Path("/balance")
public class Endpoints {

    @GET
    @Path("{msisdn}")
    public Response balance(@PathParam("msisdn") String msisdn){
        JSONObject requestJson = new JSONObject();
        requestJson.put("amont", "100");
        requestJson.put("currency", "LKR");
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(requestJson.toString()).build();
    }
}
