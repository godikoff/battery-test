package ru.batterytest.chrome;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.precondition();
        step.browserStart(60000);
        step.operUrlFirstTab("http://www.youtube.com/watch?v=ZtaKWt26dNs");
        sleep(10000);
        step.youtubeVideoPlayFullscreen();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}
