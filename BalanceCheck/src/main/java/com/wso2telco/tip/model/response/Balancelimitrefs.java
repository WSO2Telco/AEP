
package com.wso2telco.tip.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "refnumber",
    "limit",
    "notifyURL_balance_up",
    "notifyURL_balance_down"
})
public class Balancelimitrefs {

    @JsonProperty("refnumber")
    private Integer refnumber;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("notifyURL_balance_up")
    private String notifyURLBalanceUp;
    @JsonProperty("notifyURL_balance_down")
    private String notifyURLBalanceDown;

    @JsonProperty("refnumber")
    public Integer getRefnumber() {
        return refnumber;
    }

    @JsonProperty("refnumber")
    public void setRefnumber(Integer refnumber) {
        this.refnumber = refnumber;
    }

    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @JsonProperty("notifyURL_balance_up")
    public String getNotifyURLBalanceUp() {
        return notifyURLBalanceUp;
    }

    @JsonProperty("notifyURL_balance_up")
    public void setNotifyURLBalanceUp(String notifyURLBalanceUp) {
        this.notifyURLBalanceUp = notifyURLBalanceUp;
    }

    @JsonProperty("notifyURL_balance_down")
    public String getNotifyURLBalanceDown() {
        return notifyURLBalanceDown;
    }

    @JsonProperty("notifyURL_balance_down")
    public void setNotifyURLBalanceDown(String notifyURLBalanceDown) {
        this.notifyURLBalanceDown = notifyURLBalanceDown;
    }

}
