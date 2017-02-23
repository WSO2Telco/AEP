
package com.wso2telco.tip.messenger.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "mid",
    "seq",
    "text"
})
public class Message {

    @JsonProperty("mid")
    private String mid;
    @JsonProperty("seq")
    private Long seq;
    @JsonProperty("text")
    private String text;

    @JsonProperty("mid")
    public String getMid() {
        return mid;
    }

    @JsonProperty("mid")
    public void setMid(String mid) {
        this.mid = mid;
    }

    @JsonProperty("seq")
    public Long getSeq() {
        return seq;
    }

    @JsonProperty("seq")
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

}
