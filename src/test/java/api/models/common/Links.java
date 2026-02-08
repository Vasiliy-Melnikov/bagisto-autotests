package api.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {
    public String first;
    public String last;

    @JsonProperty("prev")
    public String prev;

    @JsonProperty("next")
    public String next;

    public Links() {
    }
}

