package ru.batterytest.yabro;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class UrlOpen extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.browserStart(60000);
        step.omniboxTap();
        step.omniboxInput("www.bash.im");
        sleep(10000);
        step.logStart();
        getUiDevice().pressEnter();
    }
}
