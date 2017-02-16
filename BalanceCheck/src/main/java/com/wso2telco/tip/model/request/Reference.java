
package com.wso2telco.tip.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "referenceId",
    "limit",
    "notifyURL"
})
public class Reference {

    @JsonProperty("referenceId")
    private String referenceId;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("notifyURL")
    private String notifyURL;

    @JsonProperty("referenceId")
    public String getReferenceId() {
        return referenceId;
    }

    @JsonProperty("referenceId")
    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @JsonProperty("notifyURL")
    public String getNotifyURL() {
        return notifyURL;
    }

    @JsonProperty("notifyURL")
    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

}
