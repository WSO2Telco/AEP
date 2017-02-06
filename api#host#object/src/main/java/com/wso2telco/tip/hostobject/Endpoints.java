package com.wso2telco.tip.hostobject;

import com.google.gson.*;
import com.wso2telco.dep.reportingservice.southbound.SbHostObjectUtils;
import org.apache.log4j.Logger;
import org.wso2.carbon.apimgt.api.APIConsumer;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.Application;
import org.wso2.carbon.apimgt.api.model.Subscriber;
import org.wso2.carbon.apimgt.impl.APIManagerFactory;
import org.wso2.carbon.apimgt.usage.client.exception.APIMgtUsageQueryServiceClientException;
import org.wso2.carbon.identity.base.IdentityException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by yasith on 1/30/17.
 */

@Path("/")
public class Endpoints {

    private static final Logger log = Logger.getLogger(Endpoints.class);

    @GET
    @Path("/version")
    public String printVersion(){
        return "v1.0";
    }


    @POST
    @Path("/approval")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApprovalHistory(String jsonBody){

        /**
        {
            "fromDate": "www",
            "toDate": "aa",
            "subscriber": "aa",
            "api": "ss",
            "applicationId": 1,
            "operator": "aa",
            "offset": 1,
            "count": 1
        }
        */

        String fromDate = null;
        String toDate = null;
        String subscriber = null;
        String api = null;
        int applicationId = 0;
        String operator = null;
        int offset = 0;
        int count = 0;

        String jsonPayload;

        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonBody).getAsJsonObject();

            JsonElement fromDateElement = jsonObject.get("fromDate");
            JsonElement toDateElement = jsonObject.get("toDate");
            JsonElement subscriberElement = jsonObject.get("subscriber");
            JsonElement apiElement = jsonObject.get("api");
            JsonElement applicationIdElement = jsonObject.get("applicationId");
            JsonElement operatorElement = jsonObject.get("operator");
            JsonElement offsetElement = jsonObject.get("offset");
            JsonElement countElement = jsonObject.get("count");

            if(!(fromDateElement instanceof JsonNull)) fromDate = fromDateElement.getAsString();
            if(!(toDateElement instanceof JsonNull)) toDate = toDateElement.getAsString();
            if(!(subscriberElement instanceof JsonNull)) subscriber = subscriberElement.getAsString();
            if(!(apiElement instanceof JsonNull)) api = apiElement.getAsString();
            if(!(applicationIdElement instanceof JsonNull)) applicationId = applicationIdElement.getAsInt();
            if(!(operatorElement instanceof JsonNull)) operator = operatorElement.getAsString();
            if(!(offsetElement instanceof JsonNull)) offset = offsetElement.getAsInt();
            if(!(countElement instanceof JsonNull)) count = countElement.getAsInt();

            fromDateElement.getAsString();
            List<String[]> api_requests = SbHostObjectUtils.getApprovalHistory(fromDate, toDate, subscriber, api, applicationId, operator, offset, count);
            jsonPayload = new Gson().toJson(api_requests);
        } catch (Exception e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonPayload).build();
    }

    @GET
    @Path("/subscribers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscribers(){
        String jsonPayload;
        try {
            List<String> subscribers = SbHostObjectUtils.getAllSubscribers();
            jsonPayload = new Gson().toJson(subscribers);
        } catch (Exception e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonPayload).build();
    }


    @GET
    @Path("/apis/{subscriberName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAPIsBySubscriber(@PathParam("subscriberName") String subscriberName){

        String jsonPayload;
        try {
            List<String> apis = SbHostObjectUtils.getAPIsBySubscriber(subscriberName);
            jsonPayload = new Gson().toJson(apis);
        } catch (SQLException e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        } catch (APIMgtUsageQueryServiceClientException e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        } catch (APIManagementException e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        } catch (IdentityException e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonPayload).build();
    }


    @GET
    @Path("/operators")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOperators(){

        String jsonPayload;
        try {
            List<String> operators = SbHostObjectUtils.getAllOperators();
            jsonPayload = new Gson().toJson(operators);
        } catch (SQLException e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        } catch (APIMgtUsageQueryServiceClientException e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonPayload).build();
    }


    @GET
    @Path("/applications/{subscriberName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplications(@PathParam("subscriberName") String subscriberName){
        String jsonPayload;
        try {
            APIConsumer apiConsumer = APIManagerFactory.getInstance().getAPIConsumer();
            Subscriber subscriber = new Subscriber(subscriberName);
            Application[] applicationList = apiConsumer.getApplications(subscriber, "");
            jsonPayload = new Gson().toJson(applicationList);
        } catch (APIManagementException e) {
            log.error(e);
            return Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(HttpServletResponse.SC_OK).header("Content-Type", "application/json").entity(jsonPayload).build();
    }

}
