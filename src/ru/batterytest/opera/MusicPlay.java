package ru.batterytest.opera;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.OperaSteps;

public class MusicPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        OperaSteps step = new OperaSteps();
        step.browserStart(60000);
        step.openUrlInCurrentTab("https://m.vk.com");
        sleep(10000);
        step.dialogButtonNegativeClick();
        step.vkLogin();
        sleep(10000);
        step.dialogButtonNegativeClick();
        sleep(2000);
        step.openUrlInCurrentTab("https://m.vk.com/audio");
        sleep(20000);
        step.dialogButtonNegativeClick();
        step.vkMusicStart();
        step.logStart();
        step.logPass();
    }
}
