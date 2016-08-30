package ru.batterytest.chrome;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

public class UrlOpen extends UiAutomatorTestCase {
    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.precondition();
        step.browserStart(60000);
        step.omniboxTap();
        step.omniboxInput("www.bash.im");
        sleep(10000);
        step.logStart();
        UiDevice.getInstance().pressEnter();
        step.waitForWebView();
        step.logPass();
    }
}
