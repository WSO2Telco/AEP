
package com.wso2telco.tip.model.references;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "balancelimitrefs"
})
public class Reference {

    @JsonProperty("balancelimitrefs")
    private Balancelimitrefs balancelimitrefs;

    @JsonProperty("balancelimitrefs")
    public Balancelimitrefs getBalancelimitrefs() {
        return balancelimitrefs;
    }

    @JsonProperty("balancelimitrefs")
    public void setBalancelimitrefs(Balancelimitrefs balancelimitrefs) {
        this.balancelimitrefs = balancelimitrefs;
    }

}
