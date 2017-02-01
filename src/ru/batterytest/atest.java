package ru.batterytest;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

public class atest extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        YabroObjects object = new YabroObjects();
        for (int i=0;i<100;i++) {
            step.clickOn(object.omniboxInTab);
            step.pressBack();
        }
    }
}
