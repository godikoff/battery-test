package ru.batterytest.yabro;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class TenSitesBackground extends UiAutomatorTestCase {
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
        YaBroSteps step = new YaBroSteps();
        step.precondition();
        step.browserStart(3000);
        step.openUrlFirstTab("www.worldoftanks.ru");
        sleep(10000);

        for (int i=0; i<9; i++){
            step.openUrlInNewTab(siteList[i]);
            sleep(10000);
        }
        sleep(20000);
        UiDevice.getInstance().pressHome();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}
