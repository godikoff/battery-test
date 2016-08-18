package ru.batterytest.yabro;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class MusicPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.browserStart(60000);
        step.omniboxTap();
        step.omniboxInput("https://m.vk.com");
        getUiDevice().pressEnter();
        sleep(10000);

        UiObject rootLayout = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_root_layout"));
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        int offset = rootLayout.getBounds().top - webView.getBounds().top;

        UiObject loginView = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
        UiObject passwordView = new UiObject(new UiSelector().className("android.widget.ListView").index(1));

        UiObject loginTextField = loginView.getChild(new UiSelector().className("android.widget.EditText"));
        UiObject passwordTextField = passwordView.getChild(new UiSelector().className("android.widget.EditText"));

        UiDevice.getInstance().click(loginTextField.getBounds().centerX(), loginTextField.getBounds().centerY()+offset);
        sleep(3000);

        loginTextField.setText("yabrotest@gmail.com");
        passwordTextField.setText("yabrotest123");
        getUiDevice().pressEnter();
        sleep(10000);

        step.omniboxInTabTap();
        step.omniboxInput("https://m.vk.com/audio");
        getUiDevice().pressEnter();
        sleep(20000);

        UiObject firstSong = webView.getChild(new UiSelector().description("Scorpions – Humanity"));
        step.logStart();
        UiDevice.getInstance().click(firstSong.getBounds().centerX(), firstSong.getBounds().centerY()+offset);
    }
}
