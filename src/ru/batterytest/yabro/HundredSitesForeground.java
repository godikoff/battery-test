package ru.batterytest.yabro;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

import java.io.IOException;

public class HundredSitesForeground extends UiAutomatorTestCase {
    private boolean openTab() {
        try {
            Runtime.getRuntime().exec(
                    "am start -n com.yandex.browser/com.yandex.browser.YandexBrowserActivity -d www.yandex.ru");
            sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            sleep(1000);
            if (getUiDevice().getCurrentPackageName().contains(
                    "com.yandex.browser")) {
                return true;
            }
        }
        return false;
    }

    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        YabroObjects object = new YabroObjects();
        step.precondition(getParams().getString("browser"));
        for (int i=0;i<100;i++) {
            openTab();
        }
        sleep(40000);
        step.shouldBe(object.webView);
        step.logStart();
        step.logPass();
    }
}

