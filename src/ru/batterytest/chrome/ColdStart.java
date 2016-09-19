package ru.batterytest.chrome;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.ChromeObjects;

public class ColdStart extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeObjects object = new ChromeObjects();
        BrowserSteps step = new BrowserSteps();
        sleep(5000);
        step.logStart();
        step.clickOn(object.browserHomescreenIcon);
        step.shouldBe(object.omnibox);
        step.logPass();
    }
}
