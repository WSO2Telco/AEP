package com.wso2telco.tip.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wso2telco.tip.dao.impl.ReferenceDaoImpl;
import com.wso2telco.tip.exception.BalanceCheckException;
import com.wso2telco.tip.exception.ErrorCodes;
import com.wso2telco.tip.model.response.Balancelimitrefs;
import com.wso2telco.tip.model.response.Reference;
import com.wso2telco.tip.model.response.ReferenceResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by yasith on 2/10/17.
 */
public class Updator {

    final static Logger log = LoggerFactory.getLogger(Updator.class);

    public void update(String msisdn) throws BalanceCheckException {

        ObjectMapper mapper = new ObjectMapper();
        JSONObject resultJson;

        try {
            Invoke invoke = new Invoke();
            resultJson = invoke.sendGet(msisdn);

            if(log.isDebugEnabled())
                log.debug("Result Json Received : " + resultJson );
            ReferenceResponse referenceResponse = mapper.readValue(resultJson.toString(), ReferenceResponse.class);
            List<Reference> referenceList = referenceResponse.getReferences();
            ReferenceDaoImpl referenceDao = new ReferenceDaoImpl();
            ArrayList<String> dialogReferenceList = (ArrayList<String>) referenceDao.getDialogReferenceListForMsisdn(msisdn);

            for (Reference reference :  referenceList) {

                Balancelimitrefs balancelimitrefs = reference.getBalancelimitrefs();
                int dialogReferenceNumber = balancelimitrefs.getRefnumber();
                String dialogReference = String.valueOf(dialogReferenceNumber);
                int limitValue = balancelimitrefs.getLimit();
                String limit = String.valueOf(limitValue);
                String notifyUrl = balancelimitrefs.getNotifyURLBalanceDown();
                if(log.isDebugEnabled()) {
                    log.debug("dialog Reference Number: " + dialogReferenceNumber);
                    log.debug("limitValue: " + limitValue);
                    log.debug("notifyUrl: " + notifyUrl);
                }
                if(!dialogReferenceList.contains(dialogReference)){
                    if(log.isDebugEnabled())
                        log.debug("reference not found in  the local map, updating : " + dialogReference);
                    String telcoReference = UUID.randomUUID().toString();
                    referenceDao.setReferenceEntry(telcoReference, dialogReference);
                    referenceDao.setCallbackEntry(dialogReference, telcoReference, msisdn, limit, notifyUrl);
                    referenceDao.setMsisdnReferenceEntry(msisdn, dialogReference);
                }
            }
        } catch (IOException e) {
            log.error("Exception Occured while updating map" , e);
            throw new BalanceCheckException(ErrorCodes.INTERNAL_SERVER_ERROR.getCode());
        }
    }
}
