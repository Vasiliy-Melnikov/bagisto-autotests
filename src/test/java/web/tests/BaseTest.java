package web.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import web.config.WebDriverConfig;
import web.config.WebDriverConfigProvider;
import web.helpers.Attachments;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    @BeforeAll
    static void setupSelenideConfig() {
        WebDriverConfig cfg = WebDriverConfigProvider.get();

        Configuration.baseUrl = cfg.baseUrl();
        Configuration.browser = cfg.browser();
        Configuration.browserSize = cfg.browserSize();
        Configuration.pageLoadStrategy = cfg.pageLoadStrategy();

        if (!cfg.browserVersion().isBlank()) {
            Configuration.browserVersion = cfg.browserVersion();
        } else {
            Configuration.browserVersion = null;
        }

        if (cfg.isRemote()) {
            if (cfg.remoteUrl().isBlank()) {
                throw new IllegalStateException(
                        "env=remote, но remoteUrl пустой в config/remote.properties"
                );
            }

            Configuration.remote = cfg.remoteUrl();

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("selenoid:options", Map.of(
                    "enableVNC", cfg.enableVNC(),
                    "enableVideo", cfg.enableVideo()
            ));

            Configuration.browserCapabilities = caps;

        } else {
            Configuration.remote = null;
            Configuration.browserCapabilities = new DesiredCapabilities();
        }

        SelenideLogger.addListener(
                "allure",
                new AllureSelenide().screenshots(true).savePageSource(false)
        );
    }

    @BeforeEach
    void addAllureListener() {
    }

    @AfterEach
    void addAttachments() {
        Attachments.screenshotAs("Last screenshot");
        Attachments.pageSource();
        Attachments.browserConsoleLogs();
        Attachments.addVideo();
        closeWebDriver();
    }
}






