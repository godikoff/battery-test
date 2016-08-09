package ru.batterytest.chrome;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.browserStart(60000);
        step.openUrlInCurrentTab("http://www.youtube.com/watch?v=ZtaKWt26dNs&autoplay=1");
        sleep(10000);
        getUiDevice().setOrientationLeft();
        sleep(10000);
        step.logStart();
    }
}
