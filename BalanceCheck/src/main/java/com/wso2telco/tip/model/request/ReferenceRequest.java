
package com.wso2telco.tip.model.request;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "request"
})
public class ReferenceRequest {

    @JsonProperty("request")
    private List<Reference> references = null;

    @JsonProperty("request")
    public List<Reference> getReferences() {
        return references;
    }

    @JsonProperty("request")
    public void setReferences(List<Reference> references) {
        this.references = references;
    }

}
