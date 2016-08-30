package ru.batterytest.yabro;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class Background extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.precondition();
        sleep(2000);
        step.browserStart(3000);
        sleep(22000);
        UiDevice.getInstance().pressHome();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}