package ru.batterytest.opera;


import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.OperaSteps;

public class ColdStart extends UiAutomatorTestCase {
    public void test() throws Exception {
        OperaSteps step = new OperaSteps();
        step.precondition();
        sleep(5000);
        step.logStart();
        step.browserStart(3000);
        step.logPass();
    }
}
