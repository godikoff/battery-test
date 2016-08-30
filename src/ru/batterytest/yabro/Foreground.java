package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class Foreground extends UiAutomatorTestCase {
    public void tests() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.precondition();
        sleep(2000);
        step.browserStart(3000);
        sleep(30000);
        step.logStart();
        step.logPass();
    }
}