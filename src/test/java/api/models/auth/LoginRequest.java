package api.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    public String email;
    public String password;

    @JsonProperty("device_name")
    public String deviceName;

    public LoginRequest(String email, String password, String deviceName) {
        this.email = email;
        this.password = password;
        this.deviceName = deviceName;
    }
}


