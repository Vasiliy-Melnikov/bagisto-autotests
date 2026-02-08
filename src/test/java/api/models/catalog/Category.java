package api.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    public Integer id;
    public String name;
    public String slug;

    @JsonProperty("display_mode")
    public String displayMode;

    public String description;

    @JsonProperty("meta_title")
    public String metaTitle;

    @JsonProperty("meta_description")
    public String metaDescription;

    @JsonProperty("meta_keywords")
    public String metaKeywords;

    public Integer status;

    @JsonProperty("banner_url")
    public String bannerUrl;

    @JsonProperty("logo_url")
    public String logoUrl;

    public List<Translation> translations;

    public Integer position;

    public Object additional;

    @JsonProperty("created_at")
    public String createdAt;

    @JsonProperty("updated_at")
    public String updatedAt;

    public Category() {
    }
}
