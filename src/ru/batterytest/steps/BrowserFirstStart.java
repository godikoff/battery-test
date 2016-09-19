package ru.batterytest.steps;

import android.os.RemoteException;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YabroObjects;
import ru.batterytest.steps.BrowserSteps;

public class BrowserFirstStart extends UiAutomatorTestCase{
    public void test() throws UiObjectNotFoundException, RemoteException {
        BrowserSteps step = new BrowserSteps();
        step.precondition(getParams().getString("browser"));
    }
}
