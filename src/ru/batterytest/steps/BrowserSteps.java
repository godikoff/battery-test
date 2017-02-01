package ru.batterytest.steps;

import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import java.util.regex.Pattern;

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
        long endTime = time + 30000;
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

    public void pressBack() {
        Log.i("power measurement", "Press Back");
        UiDevice.getInstance().pressBack();
    }

    public void setOrientationLandscape() throws RemoteException {
        Log.i("power measurement", "Set orientation to Landscape");
        UiDevice.getInstance().setOrientationLeft();
    }


    public void clickOnRightOf(UiObject yabroObject) throws Exception{
        long time = System.currentTimeMillis();
        long endTime = time + 10000;
        Log.i("power measurement", "Try to tap on right of " + yabroObject.getSelector());
        while (System.currentTimeMillis() < endTime) {
            if (yabroObject.exists()) {
                Log.i("power measurement", "Tap on right of " + yabroObject.getSelector());
                UiDevice.getInstance().click((int) ((yabroObject.getBounds().right) * (90.0f / 100.0f)), (int) ((yabroObject.getBounds().centerY())));
                return;
            }
            sleep(1000);
        }
    }

    public void ybroVkLogin() throws Exception{
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject rootLayout = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_root_layout"));
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        int offset = rootLayout.getBounds().top - webView.getBounds().top;
        UiObject loginView = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
        UiObject passwordView = new UiObject(new UiSelector().className("android.widget.ListView").index(1));
        UiObject loginTextField = loginView.getChild(new UiSelector().className("android.widget.EditText"));
        UiObject passwordTextField = passwordView.getChild(new UiSelector().className("android.widget.EditText"));
        UiDevice.getInstance().click(loginTextField.getBounds().centerX(), loginTextField.getBounds().centerY()+offset);
        sleep(3000);
        loginTextField.setText("yabrotest@gmail.com");
        passwordTextField.setText("yabrotest123");
        UiDevice.getInstance().pressEnter();
    }

    public void ybroVkMusicStart() throws Exception{
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject rootLayout = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_root_layout"));
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        int offset = rootLayout.getBounds().top - webView.getBounds().top;
        UiObject firstSong = webView.getChild(new UiSelector().description("Skorpions – Humanity"));
        UiDevice.getInstance().click(firstSong.getBounds().centerX(), firstSong.getBounds().centerY()+offset);
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
        //Log.i("power measurement", "start measurement");
        sleep(1000);

        /* Experiment for synchronization device log and power data */
        long time = System.currentTimeMillis();
        long endTime = time+5000;
        Log.i("power measurement", "start garbage");
        while(System.currentTimeMillis() < endTime) {
            Pattern p = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)\b");
        }
        Log.i("cr_Ya:DownloadTracking", "synchronize " + SystemClock.elapsedRealtime());
        sleep(5000);
    }

    public void logFail(UiObject elementToCheck) {
        Log.i("power measurement", "battery test failed: " + elementToCheck.getSelector() + " not found");
    }

    public void logPass() {
        Log.i("power measurement", "battery test passed");
    }

}
