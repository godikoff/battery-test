package ru.batterytest.opera;


import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.OperaSteps;

public class TenSitesForeground extends UiAutomatorTestCase {
    String[] siteList = {
            "www.cat.com",
            "www.microsoft.com",
            "www.4pda.ru",
            "www.yaplakal.com",
            "www.youtube.com",
            "www.1tv.ru",
            "www.rutube.ru",
            "www.championat.com",
            "www.bash.im"};

    public void test() throws Exception {
        OperaSteps step = new OperaSteps();
        step.browserStart(3000);
        step.openUrlInCurrentTab("www.worldoftanks.ru");
        sleep(10000);
        step.dialogButtonNegativeClick();

        for (int i= 0; i < 9; i++) {
            step.openUrlInNewTab(siteList[i]);
            sleep(10000);
            step.dialogButtonNegativeClick();
        }
        sleep(20000);
        step.logStart();
        step.logPass();
    }
}
