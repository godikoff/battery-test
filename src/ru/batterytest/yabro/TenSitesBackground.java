package ru.batterytest.yabro;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

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
        BrowserSteps step = new BrowserSteps();
        YabroObjects objects = new YabroObjects();
        step.precondition(getParams().getString("browser"));
        step.clickOn(objects.omnibox);
        step.inputText(objects.omniboxTextField, "www.worldoftanks.ru");
        step.pressEnter();
        sleep(10000);
        for (int i=0; i<9; i++){
            step.clickOn(objects.menuButton);
            step.clickOn(objects.menuNewTabButton);
            step.clickOn(objects.omnibox);
            step.inputText(objects.omniboxTextField, siteList[i]);
            step.pressEnter();
            sleep(10000);
        }
        sleep(20000);
        step.shouldBe(objects.webView);
        step.pressHome();
        sleep(10000);
        step.logStart();
        step.logPass();
    }
}
