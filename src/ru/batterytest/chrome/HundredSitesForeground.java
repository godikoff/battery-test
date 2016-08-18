package ru.batterytest.chrome;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

import java.io.IOException;

public class HundredSitesForeground extends UiAutomatorTestCase {
    private boolean openTab() {
        try {
            Runtime.getRuntime().exec(
                    "am start -n com.android.chrome/com.google.android.apps.chrome.Main -d www.yandex.ru");
            sleep(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            sleep(1000);
            if (getUiDevice().getCurrentPackageName().contains(
                    "com.android.chrome")) {
                return true;
            }
        }
        return false;
    }

    public void test() throws Exception {
        ChromeSteps step = new ChromeSteps();
        step.browserStart(60000);
        for (int i=0;i<100;i++) {
            openTab();
        }
        sleep(40000);
        step.logStart();
    }
}
