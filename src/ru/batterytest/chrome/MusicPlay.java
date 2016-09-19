package ru.batterytest.chrome;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.ChromeObjects;

public class MusicPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        ChromeObjects objects = new ChromeObjects();
        step.precondition(getParams().getString("browser"));
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "https://m.vk.com");
        step.pressEnter();
        step.clickOn(objects.loginTextField);
        step.inputText(objects.loginTextField, "yabrotest@gmail.com");
        step.clickOn(objects.passwordTextField);
        step.inputText(objects.passwordTextField, "yabrotest123");
        step.pressEnter();
        step.clickOn(objects.omniboxInTab);
        step.inputText(objects.omniboxTextField, "https://m.vk.com/audio");
        step.pressEnter();
        step.clickOn(objects.firstSong);
        step.logStart();
        step.shouldBe(objects.webView);
        step.logPass();
    }
}
