package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class Background extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        sleep(2000);
        step.browserStart(3000);
        sleep(22000);
        getUiDevice().pressHome();
        sleep(10000);
        step.logStart();
    }
}