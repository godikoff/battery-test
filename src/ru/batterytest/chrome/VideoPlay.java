package ru.batterytest.chrome;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

import java.io.File;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.browserStart(60000);
        step.openUrlInCurrentTab("http://www.youtube.com/watch?v=ZtaKWt26dNs");
        sleep(10000);
        step.youtubeVideoPlayFullscreen();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}
