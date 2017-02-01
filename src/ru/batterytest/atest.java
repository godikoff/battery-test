package ru.batterytest;

import com.android.uiautomator.core.Configurator;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

public class atest extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        YabroObjects objects = new YabroObjects();
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "http://www.youtube.com/watch?v=ZtaKWt26dNs");
        step.pressEnter();
        sleep(10000);
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject rootLayout = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_root_layout"));
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        int offset = rootLayout.getBounds().top - webView.getBounds().top;
        UiDevice.getInstance().click(objects.playButton.getBounds().centerX(), objects.playButton.getBounds().centerY() + offset);
        sleep(30000);
        //UiDevice.getInstance().click(objects.youtubeSkipAdButton.getBounds().centerX(), objects.youtubeSkipAdButton.getBounds().centerY() + offset);
        //UiDevice.getInstance().click(objects.videoContainer.getBounds().centerX(), objects.videoContainer.getBounds().centerY() + offset);
        step.setOrientationLandscape();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}


