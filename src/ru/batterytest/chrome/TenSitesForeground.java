package ru.batterytest.chrome;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.ChromeSteps;

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
        ChromeSteps step = new ChromeSteps();
        step.browserStart(3000);
        step.omniboxTap();
        step.omniboxInput("www.worldoftanks.ru");
        getUiDevice().pressEnter();
        sleep(10000);

        for (int i=0; i<9; i++){
            step.menuButtonTap();
            step.menuNewTabButtonTap();
            step.omniboxTap();
            step.omniboxInput(siteList[i]);
            getUiDevice().pressEnter();
            sleep(10000);
        }
        sleep(20000);
        step.logStart();
    }
}
