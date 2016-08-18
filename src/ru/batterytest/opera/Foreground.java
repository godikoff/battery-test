package ru.batterytest.opera;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.OperaSteps;

public class Foreground extends UiAutomatorTestCase {
    public void test() throws Exception {
        OperaSteps step = new OperaSteps();
        sleep(2000);
        step.browserStart(3000);
        sleep(30000);
        step.logStart();
    }
}
