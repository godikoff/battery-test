package ru.batterytest.chrome;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

public class MusicPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.precondition();
        step.browserStart(60000);
        step.operUrlFirstTab("https://m.vk.com");
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
