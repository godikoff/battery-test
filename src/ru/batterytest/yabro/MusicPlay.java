package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class MusicPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.precondition();
        step.browserStart(60000);
        step.openUrlFromSentry("https://m.vk.com");
        sleep(10000);
        step.vkLogin();
        sleep(10000);
        step.openUrlInCurrentTab("https://m.vk.com/audio");
        sleep(20000);
        step.vkMusicStart();
        step.logStart();
        step.logPass();
    }
}
