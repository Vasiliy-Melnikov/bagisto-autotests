package api.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseImage {

    @JsonProperty("small_image_url")
    public String smallImageUrl;

    @JsonProperty("medium_image_url")
    public String mediumImageUrl;

    @JsonProperty("large_image_url")
    public String largeImageUrl;

    @JsonProperty("original_image_url")
    public String originalImageUrl;

    public BaseImage() {
    }
}

