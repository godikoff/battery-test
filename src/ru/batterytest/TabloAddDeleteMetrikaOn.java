package ru.batterytest;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

public class TabloAddDeleteMetrikaOn extends UiAutomatorTestCase {
    public void test() throws Exception {
        BrowserSteps step = new BrowserSteps();
        YabroObjects objects = new YabroObjects();
        step.logStart();
        sleep(1000);
        for (int i=0; i<20; i++){
            step.clickOn(objects.omnibox);
            step.inputText(objects.omniboxTextField, "m.vk.com");
            step.pressEnter();
            sleep(5000);
            step.clickOn(objects.tabMenuButton);
            sleep(500);
            step.clickOn(objects.menuDeleteFromTabloButton);
            sleep(500);
            step.pressBack();
            sleep(500);
            step.clickOn(objects.omnibox);
            step.inputText(objects.omniboxTextField, "m.vk.com");
            step.pressEnter();
            sleep(5000);
            step.clickOn(objects.tabMenuButton);
            sleep(500);
            step.clickOn(objects.menuAddToTabloButton);
            sleep(500);
            step.pressBack();
        }
    }
}
