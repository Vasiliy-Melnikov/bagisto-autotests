package api.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterCustomerRequest {

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("last_name")
    public String lastName;

    public String email;
    public String password;

    @JsonProperty("password_confirmation")
    public String passwordConfirmation;

    public RegisterCustomerRequest(
            String firstName,
            String lastName,
            String email,
            String password
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = password;
    }
}
