package ru.batterytest.opera;


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
        step.omniboxTap();
        step.omniboxInput("www.worldoftanks.ru");
        getUiDevice().pressEnter();
        sleep(10000);

        for (int i=0; i<9; i++){
            step.carouselButtonTap();
            step.carouselNewTabButtonTap();
            step.omniboxTap();
            step.omniboxInput(siteList[i]);
            getUiDevice().pressEnter();
            sleep(10000);
        }
        sleep(20000);
        step.logStart();
    }
}
