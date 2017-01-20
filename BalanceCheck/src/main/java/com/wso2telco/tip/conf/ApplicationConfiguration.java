package com.wso2telco.tip.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Created by yasith on 1/18/17.
 */
public class ApplicationConfiguration extends Configuration {

    /** The redis. */
    @JsonProperty
    private Object redis;


    /**
     * Gets the redis.
     *
     * @return the redis
     */
    public Object getRedis() {
        return redis;
    }

}
