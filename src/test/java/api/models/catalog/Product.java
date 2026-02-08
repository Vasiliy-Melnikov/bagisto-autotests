package api.models.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    public Integer id;
    public String sku;
    public String type;
    public String name;
    public String url;

    @JsonProperty("price")
    public String price;

    @JsonProperty("formated_price")
    public String formattedPrice;

    @JsonProperty("short_description")
    public String shortDescription;

    public String description;

    @JsonProperty("images")
    public BaseImage images;

    public Product() {
    }
}
