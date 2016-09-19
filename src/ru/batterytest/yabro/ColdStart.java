package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YabroObjects;
import ru.batterytest.steps.BrowserSteps;

public class ColdStart extends UiAutomatorTestCase {
    public void test() throws Exception {
        YabroObjects object = new YabroObjects();
        BrowserSteps step = new BrowserSteps();
        step.precondition(getParams().getString("browser"));
        sleep(5000);
        step.logStart();
        step.clickOn(object.browserHomescreenIcon);
        step.shouldBe(object.omnibox);
        step.logPass();
    }
}