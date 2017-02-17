package com.wso2telco.tip.messenger.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Created by yasith on 1/18/17.
 */
public class ApplicationConfiguration extends Configuration {

    /** The redis. */
    @JsonProperty
    private Object redis;

    /** The remote. */
    @JsonProperty
    private Object remote;

    /** The callback. */
    @JsonProperty
    private Object dialog;

    /**
     * Gets the redis.
     *
     * @return the redis
     */
    public Object getRedis() {
        return redis;
    }

    /**
     * Gets the remote.
     *
     * @return the remote
     */
    public Object getRemote() {
        return remote;
    }

    /**
     * Gets the callback.
     *
     * @return the callback
     */
    public Object getDialog() {
        return dialog;
    }

}
