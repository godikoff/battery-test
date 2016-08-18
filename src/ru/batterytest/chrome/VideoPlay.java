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
        step.omniboxTap();
        step.omniboxInput("http://www.youtube.com/watch?v=ZtaKWt26dNs");
        getUiDevice().pressEnter();
        sleep(10000);

        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));
        playButton.click();
        sleep(2000);

        UiObject videoContainer = new UiObject(new UiSelector().resourceId("koya_elem_0_11"));
        UiDevice.getInstance().click((int)((videoContainer.getBounds().right)*(90.0f/100.0f)), (int)((videoContainer.getBounds().bottom)*(90.0f/100.0f)));

        getUiDevice().setOrientationLeft();
        sleep(2000);

        UiObject notificationCloseButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/infobar_close_button"));
        if (notificationCloseButton.exists())
            notificationCloseButton.click();
        sleep(10000);
        step.logStart();
    }
}
