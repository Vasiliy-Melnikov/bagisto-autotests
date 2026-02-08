package api.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
    @JsonProperty("current_page")
    public Integer currentPage;

    public Integer from;

    @JsonProperty("last_page")
    public Integer lastPage;

    public List<MetaLink> links;

    public String path;

    @JsonProperty("per_page")
    public Integer perPage;

    public Integer to;
    public Integer total;

    public Meta() {
    }
}
