package com.wso2telco.tip.dao.impl;

import com.wso2telco.tip.dao.PersistableReferenceDAO;
import com.wso2telco.tip.util.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasith on 2/6/17.
 */
public class ReferenceDaoImpl implements PersistableReferenceDAO{

    public static final String SEPERATOR = "::";

    @Override
    public String getDialogReferenceFromTelcoReference(String telcoReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String dialogReference = jedis.get(telcoReference);
        jedis.close();
        return dialogReference;
    }

    @Override
    public String getTelcoReferenceFromDialogReference(String dialogReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String urlRef = jedis.get(dialogReference);
        String telcoReference = urlRef.split(SEPERATOR)[0];
        jedis.close();
        return telcoReference;
    }

    @Override
    public String getCallbackFromDialogReference(String dialogReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String urlRef = jedis.get(dialogReference);
        String callback = urlRef.split(SEPERATOR)[3];
        jedis.close();
        return callback;
    }

    @Override
    public String getCallbackFromTelcoReference(String telcoReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String dialogReference = jedis.get(telcoReference);
        String urlRef = jedis.get(dialogReference);
        String callback = urlRef.split(SEPERATOR)[3];
        jedis.close();
        return callback;
    }

    @Override
    public String getLimitFromTelcoReference(String telcoReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String dialogReference = jedis.get(telcoReference);
        String urlRef = jedis.get(dialogReference);
        String limit = urlRef.split(SEPERATOR)[2];
        jedis.close();
        return limit;
    }

    @Override
    public String getLimitFromDialogReference(String dialogReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String urlRef = jedis.get(dialogReference);
        String limit = urlRef.split(SEPERATOR)[2];
        jedis.close();
        return limit;
    }

    @Override
    public String getMsisdnFromTelcoReference(String telcoReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String dialogReference = jedis.get(telcoReference);
        String urlRef = jedis.get(dialogReference);
        String msisdn = urlRef.split(SEPERATOR)[1];
        jedis.close();
        return msisdn;
    }

    @Override
    public String getMsisdnFromDialogReference(String dialogReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        String urlRef = jedis.get(dialogReference);
        String msisdn = urlRef.split(SEPERATOR)[1];
        jedis.close();
        return msisdn;
    }

    @Override
    public List<String> getDialogReferenceListForMsisdn(String msisdn) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        List<String> referenceList = jedis.lrange(msisdn , 0 , -1);
        jedis.close();
        return referenceList;
    }

    @Override
    public void setReferenceEntry(String telcoReference, String dialogReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        jedis.set(telcoReference,dialogReference);
        jedis.close();
    }

    @Override
    public void setMsisdnReferenceEntry(String msisdn, String dialogReference) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        jedis.rpush(msisdn , dialogReference);
        jedis.close();
    }

    @Override
    public void setCallbackEntry(String dialogReference, String telcoReference , String msisdn, String limit, String callback) {
        Jedis jedis = RedisUtil.getInstance().getResource();
        jedis.set(dialogReference,  telcoReference + SEPERATOR + msisdn + SEPERATOR + limit + SEPERATOR + callback);
        jedis.close();
    }
}
