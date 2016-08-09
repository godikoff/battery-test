package ru.batterytest.yabro;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class Scroll extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.browserStart(3000);
        step.openUrlInCurrentTab("www.bash.im");
        sleep(30000);
        step.scrollDown(2);
        sleep(5000);
        step.logStart();
        for (int i = 0; i < 20; i++) {
            step.scrollDown(10);
            step.scrollUp(10);
        }
    }
}
