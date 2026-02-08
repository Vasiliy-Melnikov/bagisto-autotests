package api.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Translation {

    public Integer id;

    @JsonProperty("category_id")
    public Integer categoryId;

    public String name;
    public String slug;

    @JsonProperty("url_path")
    public String urlPath;

    public String description;

    @JsonProperty("meta_title")
    public String metaTitle;

    @JsonProperty("meta_description")
    public String metaDescription;

    @JsonProperty("meta_keywords")
    public String metaKeywords;

    @JsonProperty("locale_id")
    public Integer localeId;

    public String locale;

    public Translation() {
    }
}

