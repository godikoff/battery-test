package ru.batterytest.chrome;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.ChromeObjects;

public class Scroll extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        ChromeObjects objects = new ChromeObjects();
        step.precondition(getParams().getString("browser"));
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "www.bash.im");
        step.pressEnter();
        sleep(30000);
        step.scrollDown(5);
        sleep(5000);
        step.logStart();
        for (int i = 0; i < 20; i++) {
            step.scrollDown(10);
            step.scrollUp(10);
        }
        step.shouldBe(objects.webView);
        step.logPass();
    }
}
