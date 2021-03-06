package ru.batterytest.opera;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.OperaSteps;

public class Scroll extends UiAutomatorTestCase {
    public void test() throws Exception {
        OperaSteps step = new OperaSteps();
        step.precondition();
        step.browserStart(30000);
        step.openUrlInCurrentTab("www.bash.im");
        sleep(30000);
        step.dialogButtonNegativeClick();
        step.scrollDown(5);
        sleep(5000);
        step.logStart();
        for (int i = 0; i < 20; i++) {
            step.scrollDown(10);
            step.scrollUp(10);
        }
        step.logPass();
    }
}
