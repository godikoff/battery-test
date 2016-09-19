package ru.batterytest.yabro;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

public class UrlOpen extends UiAutomatorTestCase {

    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        YabroObjects objects = new YabroObjects();
        step.precondition(getParams().getString("browser"));
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "www.bash.im");
        sleep(10000);
        step.logStart();
        step.pressEnter();
        step.logPass();
    }
}
