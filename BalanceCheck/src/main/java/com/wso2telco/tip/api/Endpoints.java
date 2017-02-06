package com.wso2telco.tip.api;

import com.wso2telco.tip.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created by yasith on 1/18/17.
 */

@Path("/balance")
public class Endpoints {

    final static Logger log = LoggerFactory.getLogger(Endpoints.class);

    @GET
    @Path("{msisdn}")
    public Response setBalanceLimit(@PathParam("msisdn") String msisdn){
        if(log.isInfoEnabled())
            log.info("msisdn : " + msisdn);

        if(Validator.validateMsisdn(msisdn)){

        }
        return null;
    }
}
