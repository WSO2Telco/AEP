package com.wso2telco.tip.dao;

import java.util.List;

/**
 * Created by yasith on 2/6/17.
 */
public interface PersistableReferenceDAO {

    String getDialogReferenceFromTelcoReference(String telcoReference);

    String getTelcoReferenceFromDialogReference(String dialogReference);

    String getCallbackFromDialogReference(String dialogReference);

    String getCallbackFromTelcoReference(String telcoReference);

    String getLimitFromTelcoReference(String telcoReference);

    String getLimitFromDialogReference(String dialogReference);

    String getMsisdnFromTelcoReference(String telcoReference);

    String getMsisdnFromDialogReference(String dialogReference);

    List<String> getDialogReferenceListForMsisdn(String msisdn);

    void setReferenceEntry(String telcoReference, String dialogReference);

    void setMsisdnReferenceEntry(String msisdn, String dialogReference);

    void setCallbackEntry(String dialogReference, String telcoReference , String msisdn, String limit, String callback);
}
