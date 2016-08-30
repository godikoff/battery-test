package ru.batterytest.chrome;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

public class ColdStart extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.precondition();
        sleep(5000);
        step.logStart();
        step.browserStart(3000);
        step.logPass();
    }
}
