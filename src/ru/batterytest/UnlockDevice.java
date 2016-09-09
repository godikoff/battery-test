package ru.batterytest;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.YaBroSteps;

public class UnlockDevice extends UiAutomatorTestCase {

    public void test() throws UiObjectNotFoundException {
        int screenHeight = (int) UiDevice.getInstance().getDisplayHeight();
        int screenWidth = (int) UiDevice.getInstance().getDisplayWidth();

        YaBroSteps step = new YaBroSteps();
        UiObject yandexBrowser = new UiObject(new UiSelector().text("Яндекс\n" + "Браузер"));
        if (!yandexBrowser.exists())
            UiDevice.getInstance().swipe(screenWidth / 2, screenHeight - (screenHeight / 7), screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), 20);
        sleep(5000);
        if (!yandexBrowser.exists())
            step.logFail(yandexBrowser);
    }
}
