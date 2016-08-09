package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class ColdStart extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        sleep(5000);
        step.logStart();
        step.browserStart(3000);
    }
}