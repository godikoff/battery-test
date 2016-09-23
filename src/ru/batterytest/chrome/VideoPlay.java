package ru.batterytest.chrome;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.ChromeObjects;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        ChromeObjects objects = new ChromeObjects();
        step.precondition(getParams().getString("browser"));
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "http://www.youtube.com/watch?v=ZtaKWt26dNs");
        step.pressEnter();
        sleep(10000);
        step.clickOn(objects.playButton);
        sleep(30000);
        step.clickOn(objects.youtubeSkipAdButton);
        step.clickOnRightOf(objects.videoContainer);
        step.setOrientationLandscape();
        step.clickOn(objects.notificationCloseButton);
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}
