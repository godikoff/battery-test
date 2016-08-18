package ru.batterytest.chrome;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

public class MusicPlay extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.browserStart(60000);
        step.omniboxTap();
        step.omniboxInput("https://m.vk.com");
        getUiDevice().pressEnter();
        sleep(10000);

        UiObject loginView = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
        UiObject passwordView = new UiObject(new UiSelector().className("android.widget.ListView").index(1));

        loginView.getChild(new UiSelector().className("android.widget.EditText")).setText("yabrotest@gmail.com");
        passwordView.getChild(new UiSelector().className("android.widget.EditText")).setText("yabrotest123");
        getUiDevice().pressEnter();
        sleep(10000);

        step.omniboxInTabTap();
        step.omniboxInput("https://m.vk.com/audio");
        getUiDevice().pressEnter();
        sleep(20000);

        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));

        step.logStart();
        webView.getChild(new UiSelector().description("Scorpions – Humanity")).click();
    }
}
