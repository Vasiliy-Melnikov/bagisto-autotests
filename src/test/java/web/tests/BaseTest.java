package web.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import web.helpers.Attachments;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = System.getProperty("baseUrl", "https://demo.bagisto.com");
        Configuration.browser = System.getProperty("browser", "chrome");

        String browserVersion = System.getProperty("browserVersion");
        if (browserVersion != null && !browserVersion.isBlank()) {
            Configuration.browserVersion = browserVersion;
        }

        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = "eager";
        String env = System.getProperty("env", "local").toLowerCase();
        String remoteUrl = System.getProperty("remote"); // важно: -Dremote=...
        if ("remote".equals(env)) {
            if (remoteUrl == null || remoteUrl.isBlank()) {
                throw new IllegalStateException(
                        "env=remote задан, но не передан -Dremote=<selenoid/grid url>. " +
                                "Пример: -Dremote=https://selenoid.autotests.cloud/wd/hub"
                );
            }
            Configuration.remote = remoteUrl;
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("enableVNC", true);
            caps.setCapability("enableVideo", true);
            Configuration.browserCapabilities = caps;
        } else {
            Configuration.remote = null;
        }

        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
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



