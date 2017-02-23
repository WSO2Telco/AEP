
package com.wso2telco.tip.messenger.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "time",
    "messaging"
})
public class Entry {

    @JsonProperty("id")
    private String id;
    @JsonProperty("time")
    private Long time;
    @JsonProperty("messaging")
    private List<Messaging> messaging = null;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("time")
    public Long getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(Long time) {
        this.time = time;
    }

    @JsonProperty("messaging")
    public List<Messaging> getMessaging() {
        return messaging;
    }

    @JsonProperty("messaging")
    public void setMessaging(List<Messaging> messaging) {
        this.messaging = messaging;
    }

}
