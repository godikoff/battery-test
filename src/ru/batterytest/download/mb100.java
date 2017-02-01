package ru.batterytest.download;

import android.os.SystemClock;
import android.util.Log;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

import java.util.regex.Pattern;


public class mb100 extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        YabroObjects objects = new YabroObjects();
        System.out.println("start");
        sleep(5000);
        step.logStart();
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "http://www.thinkbroadband.com/download.html");
        step.pressEnter();
        sleep(5000);
        step.clickOn(objects.download100mb);
        step.clickOn(objects.infobarAcceptButton);
    }
}
