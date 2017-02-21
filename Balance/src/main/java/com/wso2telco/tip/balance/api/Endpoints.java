package com.wso2telco.tip.balance.api;

import com.wso2telco.tip.balance.exception.ErrorCodes;
import com.wso2telco.tip.balance.invoke.Invoke;
import com.wso2telco.tip.balance.util.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by yasith on 2/21/17.
 */

@Path("/")
public class Endpoints {

    static
    {
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(new ConsoleAppender(
                new PatternLayout("%-6r [%p] %c - %m%n")));
    }

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
        BasicConfigurator.configure();
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
            requestJson = invoke.sendGet(msisdn.substring(2));
        } catch (IOException e) {
            log.error("Error Occured for the msisdn : " + msisdn, e);
            requestJson = new JSONObject();
            requestJson.put("code", ErrorCodes.INTERNAL_SERVER_ERROR.getKey());
            requestJson.put("message", ErrorCodes.INTERNAL_SERVER_ERROR.getCode());
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).header("Content-Type", "application/json").entity(requestJson.toString()).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(requestJson.toString()).build();

    }
}
