package ru.batterytest.opera;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.OperaSteps;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        OperaSteps step = new OperaSteps();
        step.browserStart(60000);
        step.omniboxTap();
        step.omniboxInput("http://www.youtube.com/watch_popup?v=ZtaKWt26dNs");
        getUiDevice().pressEnter();
        sleep(10000);

        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Посмотреть ✔ Space Exploration - \"Our Universe\" (Episode 01) [2016 Documentary]"));
        playButton.click();

        UiObject videoContainer = new UiObject(new UiSelector().className("android.webkit.WebView"));
        UiDevice.getInstance().click((int) ((videoContainer.getBounds().right) * (90.0f / 100.0f)), (int) ((videoContainer.getBounds().bottom) * (95.0f / 100.0f)));

        getUiDevice().setOrientationLeft();

        sleep(10000);
        step.logStart();
    }
}
