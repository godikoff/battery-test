package ru.batterytest;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;

public class Control extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        sleep(5000);
        step.logStart();
        step.logPass();
    }
}
