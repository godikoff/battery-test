package ru.batterytest;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.YabroObjects;

public class UnlockDevice extends UiAutomatorTestCase {

    public void test() throws UiObjectNotFoundException {
        int screenHeight = (int) UiDevice.getInstance().getDisplayHeight();
        int screenWidth = (int) UiDevice.getInstance().getDisplayWidth();

        BrowserSteps step = new BrowserSteps();
        YabroObjects yabroObjects = new YabroObjects();
        if (!yabroObjects.browserHomescreenIcon.exists())
            UiDevice.getInstance().swipe(screenWidth / 2, screenHeight - (screenHeight / 7), screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), 20);
        sleep(5000);
        if (!yabroObjects.browserHomescreenIcon.exists())
            step.logFail(yabroObjects.browserHomescreenIcon);
    }
}
