package web.helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;

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
            String text = logs.toString();
            Allure.addAttachment("Browser console logs", "text/plain",
                    text, ".log");
        } catch (Exception ignored) {
            Allure.addAttachment("Browser console logs", "text/plain",
                    "No browser logs or not supported", ".log");
        }
    }

    public static void addVideo() {
        String videoUrlPattern = System.getProperty("videoUrlPattern");
        if (videoUrlPattern == null || videoUrlPattern.isBlank()) return;

        String sessionId = getWebDriver().manage().getCookieNamed("session") != null
                ? getWebDriver().manage().getCookieNamed("session").getValue()
                : null;
        if (sessionId == null) return;

        String url = videoUrlPattern.replace("{sessionId}", sessionId);
        Allure.addAttachment("Video", "text/uri-list", url);
    }
}
