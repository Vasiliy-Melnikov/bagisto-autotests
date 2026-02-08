package web.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
        System.out.println("env = " + System.getProperty("env"));
        System.out.println("cfg.isRemote = " + cfg.isRemote());
        System.out.println("cfg.remoteUrl = " + cfg.remoteUrl());
        System.out.println("cfg.browser = " + cfg.browser());
        System.out.println("cfg.browserVersion = " + cfg.browserVersion());
        System.out.println("cfg.browserSize = " + cfg.browserSize());
        System.out.println("cfg.baseUrl = " + cfg.baseUrl());

        Configuration.baseUrl = cfg.baseUrl();
        Configuration.browser = cfg.browser();
        Configuration.browserSize = cfg.browserSize();
        Configuration.pageLoadStrategy = cfg.pageLoadStrategy();

        if (!cfg.browserVersion().isBlank()) {
            Configuration.browserVersion = cfg.browserVersion();
        }

        boolean forceRemoteByEnv = "remote".equalsIgnoreCase(System.getProperty("env", ""));

        if (forceRemoteByEnv || cfg.isRemote()) {
            String remoteUrl = cfg.remoteUrl();
            if (remoteUrl == null || remoteUrl.isBlank()) {
                throw new IllegalStateException("Remote run requested, but remoteUrl is empty");
            }

            Configuration.remote = remoteUrl;

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("selenoid:options", Map.of(
                    "enableVNC", cfg.enableVNC(),
                    "enableVideo", cfg.enableVideo()
            ));
            Configuration.browserCapabilities = caps;
        } else {
            Configuration.remote = null;
        }

        SelenideLogger.addListener("allure",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
        );
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







