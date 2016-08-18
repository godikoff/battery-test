package ru.batterytest.yabro;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class VideoPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.browserStart(60000);
        step.omniboxTap();
        step.omniboxInput("http://www.youtube.com/watch?v=ZtaKWt26dNs");
        getUiDevice().pressEnter();
        sleep(10000);

        UiObject rootLayout = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_root_layout"));
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        int offset = rootLayout.getBounds().top - webView.getBounds().top;

        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));

        UiDevice.getInstance().click(playButton.getBounds().centerX(), playButton.getBounds().centerY()+offset);
        sleep(3000);

        UiObject videoContainer = new UiObject(new UiSelector().resourceId("koya_elem_0_11"));
        UiDevice.getInstance().click((int)((videoContainer.getBounds().right)*(90.0f/100.0f)), (int)((videoContainer.getBounds().bottom)*(90.0f/100.0f))+offset);
        sleep(1000);

        getUiDevice().setOrientationLeft();

        sleep(10000);
        step.logStart();
    }
}