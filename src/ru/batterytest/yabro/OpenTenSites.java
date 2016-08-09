package ru.batterytest.yabro;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class OpenTenSites extends UiAutomatorTestCase {
    public void test() throws Exception {
        YaBroSteps step = new YaBroSteps();
        step.browserStart(3000);
        step.openUrlInCurrentTab("www.worldoftanks.ru");
        sleep(10000);
        step.openUrlInNewTab("www.cat.com");
        sleep(10000);
        step.openUrlInNewTab("www.microsoft.com");
        sleep(10000);
        step.openUrlInNewTab("www.4pda.ru");
        sleep(10000);
        step.openUrlInNewTab("www.yaplakal.com");
        sleep(10000);
        step.openUrlInNewTab("www.youtube.com");
        sleep(10000);
        step.openUrlInNewTab("www.1tv.ru");
        sleep(10000);
        step.openUrlInNewTab("www.rutube.ru");
        sleep(10000);
        step.openUrlInNewTab("www.championat.com");
        sleep(10000);
        step.openUrlInNewTab("www.bash.im");
        sleep(30000);
        step.logStart();
    }
}
