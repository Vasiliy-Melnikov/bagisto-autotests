package web.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class TestData {

    private static final Faker faker = new Faker(Locale.US);

    public String invalidCoupon() {
        return faker.regexify("[A-Z0-9]{10}");
    }
}
