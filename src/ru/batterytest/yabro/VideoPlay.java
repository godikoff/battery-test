package ru.batterytest.yabro;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        YabroObjects objects = new YabroObjects();
        step.precondition(getParams().getString("browser"));
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "http://www.youtube.com/watch?v=ZtaKWt26dNs");
        step.pressEnter();
        sleep(10000);
        step.clickOn(objects.playButton);
        sleep(30000);
        step.clickOn(objects.youtubeSkipAdButton);
        step.clickOnLeftBottomOf(objects.videoContainer);
        step.setOrientationLandscape();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}
