package com.wso2telco.tip.messenger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by yasith on 2/17/17.
 */

@Path("/")
public class Endpoints {

    @GET
    @Path("/callback")
    public Response event(){
        System.out.println("callback GET triggered");
        return Response.status(HttpServletResponse.SC_OK).build();
    }

    @POST
    @Path("/callback")
    public Response event(String jsonBody){
        System.out.println("callback POST triggered");
        return Response.status(HttpServletResponse.SC_OK).build();
    }

}
