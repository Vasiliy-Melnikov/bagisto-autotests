package api.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaLink {
    public String url;
    public String label;
    public Boolean active;

    public MetaLink() {
    }
}
