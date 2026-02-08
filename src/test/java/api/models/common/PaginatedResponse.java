package api.models.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResponse<T> {
    public List<T> data;
    public Links links;
    public Meta meta;

    public PaginatedResponse() {
    }
}
