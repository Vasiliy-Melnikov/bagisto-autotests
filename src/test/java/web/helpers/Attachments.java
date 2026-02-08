package web.helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Attachments {

    public static void screenshotAs(String name) {
        byte[] bytes = Selenide.screenshot(OutputType.BYTES);
        Allure.getLifecycle().addAttachment(name, "image/png", "png", bytes);
    }

    public static void pageSource() {
        String source = getWebDriver().getPageSource();
        Allure.addAttachment("Page source", "text/html", source, ".html");
    }

    public static void browserConsoleLogs() {
        try {
            var logs = getWebDriver().manage().logs().get("browser").getAll();
            Allure.addAttachment("Browser console logs", "text/plain", logs.toString(), ".log");
        } catch (Exception ignored) {
            Allure.addAttachment("Browser console logs", "text/plain",
                    "No browser logs or not supported", ".log");
        }
    }

    public static void addVideo() {
        String videoUrlPattern = System.getProperty(
                "videoUrlPattern",
                "https://selenoid.autotests.cloud/video/{sessionId}.mp4"
        );

        String sId = Selenide.sessionId() != null ? Selenide.sessionId().toString() : null;
        if (sId == null || sId.isBlank()) return;

        String url = videoUrlPattern.replace("{sessionId}", sId);
        Allure.addAttachment("Video", "text/uri-list", url);
    }

}

