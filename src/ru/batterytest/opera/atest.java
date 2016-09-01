package ru.batterytest.opera;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.OperaSteps;

public class atest extends UiAutomatorTestCase {
    public void test() throws Exception {
        OperaSteps step = new OperaSteps();
        step.precondition();
        step.browserStart(3000);
        step.openUrlInCurrentTab("http://www.youtube.com/watch?v=ZtaKWt26dNs");
        sleep(2000);
        step.dialogButtonNegativeClick();
        step.youtubeVideoPlayFullscreen();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}