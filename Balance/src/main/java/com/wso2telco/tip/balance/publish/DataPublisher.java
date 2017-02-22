package com.wso2telco.tip.balance.publish;

import com.wso2telco.tip.balance.util.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * Created by yasith on 2/22/17.
 */
public class DataPublisher {

    public void publishData(String msisdn, String amount){
        Jedis jedis = RedisUtil.getInstance().getResource();
        jedis.set("balance:"+msisdn,amount);
        jedis.close();
    }

    public String getAmount(String msisdn){
        Jedis jedis = RedisUtil.getInstance().getResource();
        String amount = jedis.get("balance:"+msisdn);
        jedis.close();
        return amount;
    }
}
