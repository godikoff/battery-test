package ru.batterytest.steps;

import android.os.RemoteException;
import android.util.Log;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BrowserSteps extends UiAutomatorTestCase {
    YabroObjects yabroObjects = new YabroObjects();
    ChromeObjects chromeObjects = new ChromeObjects();

    public BrowserSteps() throws UiObjectNotFoundException {
    }

    public void precondition(String browserType) throws RemoteException, UiObjectNotFoundException {
        UiDevice.getInstance().pressHome();
        UiDevice.getInstance().setOrientationNatural();
        if (browserType.equals("yabro")) {
            YabroStart(60);
        }
        else if (browserType.equals("chrome")) {
            ChromeStart(60);
        }
    }


    public void clickOn(UiObject yabroObject) throws UiObjectNotFoundException {
        long time = System.currentTimeMillis();
        long endTime = time + 10000;
        Log.i("power measurement", "Try to tap on " + yabroObject.getSelector());
        while (System.currentTimeMillis() < endTime) {
            if (yabroObject.exists()) {
                Log.i("power measurement", "Tap on " + yabroObject.getSelector());
                yabroObject.click();
                return;
            }
            sleep(1000);
        }
    }

    public void inputText(UiObject yabroObject, String text) throws UiObjectNotFoundException {
        long time = System.currentTimeMillis();
        long endTime = time + 10000;
        Log.i("power measurement", "Try to input text " + text + " to " + yabroObject.getSelector());
        while (System.currentTimeMillis() < endTime) {
            if (yabroObject.exists()) {
                Log.i("power measurement", "Input text " + text + " to " + yabroObject.getSelector());
                yabroObject.setText(text);
                return;
            }
            sleep(1000);
        }
    }

    public void shouldBe(UiObject yabroObject) throws UiObjectNotFoundException {
        long time = System.currentTimeMillis();
        long endTime = time + 30000;
        Log.i("power measurement", "Try to find " + yabroObject.getSelector());
        while (System.currentTimeMillis() < endTime) {
            if (yabroObject.exists()){
                Log.i("power measurement", "Found " + yabroObject.getSelector());
                return;
            }
            sleep(1000);
        }
        assertTrue(yabroObject.getSelector() + " not found", yabroObject.exists());
    }

    public void scrollDown(int count) throws Exception {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        for (int x = 0; x < count; x++) {
            Log.i("power measurement", "Scroll down, count=" + x + 1);
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight / 7), screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), 20);
        }
    }

    public void scrollUp(int count) throws Exception {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        for (int x = 0; x < count; x++) {
            Log.i("power measurement", "Scroll up, count=" + x + 1);
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), screenWidth / 2, screenHeight - (screenHeight / 7), 20);
        }
    }

    public void pressEnter() {
        Log.i("power measurement", "Press Enter");
        UiDevice.getInstance().pressEnter();
    }

    public void pressHome() {
        Log.i("power measurement", "Press Home");
        UiDevice.getInstance().pressHome();
    }

    public void pressDelete() {
        Log.i("power measurement", "Press Delete");
        UiDevice.getInstance().pressDelete();
    }

    public void setOrientationLandscape() throws RemoteException {
        Log.i("power measurement", "Set orientation to Landscape");
        UiDevice.getInstance().setOrientationLeft();
    }


    public void clickOnLeftBottomOf(UiObject yabroObject) throws Exception{
        long time = System.currentTimeMillis();
        long endTime = time + 10000;
        Log.i("power measurement", "Try to tap on left bottom of " + yabroObject.getSelector());
        while (System.currentTimeMillis() < endTime) {
            if (yabroObject.exists()) {
                Log.i("power measurement", "Tap on left bottom of " + yabroObject.getSelector());
                UiDevice.getInstance().click((int) ((yabroObject.getBounds().right) * (90.0f / 100.0f)), (int) ((yabroObject.getBounds().bottom) * (90.0f / 100.0f)));
                return;
            }
            sleep(1000);
        }
    }

    public void YabroStart(int s) throws UiObjectNotFoundException {
        clickOn(yabroObjects.browserHomescreenIcon);
        clickOn(yabroObjects.tuorialCloseButton);
        shouldBe(yabroObjects.omnibox);
        sleep(s*1000);
    }

    public void ChromeStart(int s) throws UiObjectNotFoundException {
        clickOn(chromeObjects.browserHomescreenIcon);
        clickOn(chromeObjects.reportCheckBox);
        clickOn(chromeObjects.acceptButton);
        clickOn(chromeObjects.skipLogin);
        shouldBe(chromeObjects.omnibox);
        sleep(s*1000);

    }

    public void logStart() throws Exception {
        Log.i("power measurement", "start measurement");
        sleep(1000);

        /* Experiment for synchronization device log and power data
        long time = System.currentTimeMillis();
        long endTime = time+5000;
        Log.i("power measurement", "start garbage");
        while(System.currentTimeMillis() < endTime) {
            Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)\b");
        }
        Log.i("power measurement", "stop garbage");
        Log.i("power measurement", "control message");
        sleep(1000);
        */
    }

    public void logFail(UiObject elementToCheck) {
        Log.i("power measurement", "battery test failed: " + elementToCheck.getSelector() + " not found");
    }

    public void logPass() {
        Log.i("power measurement", "battery test passed");
    }

}
