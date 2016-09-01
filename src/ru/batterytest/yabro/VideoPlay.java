package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.precondition();
        step.browserStart(60000);
        step.openUrlFromSentry("http://www.youtube.com/watch_popup?v=ZtaKWt26dNs");
        sleep(10000);
        step.youtubeVideoPlayFullscreen();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}
