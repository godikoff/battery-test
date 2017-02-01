package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;

public class Background extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        step.precondition(getParams().getString("browser"));
        step.pressHome();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}