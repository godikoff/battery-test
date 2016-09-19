package ru.batterytest.yabro;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;

public class Foreground extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        step.precondition(getParams().getString("browser"));
        step.logStart();
        step.logPass();
    }
}