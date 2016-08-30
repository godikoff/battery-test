package ru.batterytest.steps;

import android.os.RemoteException;
import android.util.Log;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class YaBroSteps extends UiAutomatorTestCase {

    public void precondition() throws RemoteException {
        UiDevice.getInstance().pressHome();
        UiDevice.getInstance().setOrientationNatural();
    }


    //menu in omni tap
    public void menuButtonTap() throws UiObjectNotFoundException {
        UiObject menuButton = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_omnibar_address_button_menu"));
        if (menuButton.exists()) {
            menuButton.click();
        }
        else{
            logFail(menuButton);
        }
    }

    //Yabro start (time for sleep after start)
    public void browserStart(int s) throws UiObjectNotFoundException {

        UiObject yandexBrowser = new UiObject(new UiSelector().text("Яндекс\n" + "Браузер"));
        if (yandexBrowser.exists())
            yandexBrowser.click();
        else {
            logFail(yandexBrowser);
        }
        sleep(5000);
        UiObject tuorialCloseButton = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/activity_tutorial_close_button"));
        if (tuorialCloseButton.exists())
            tuorialCloseButton.click();
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_sentry_bar_fake_text"));
        if (!(omnibox.exists())) {
            logFail(omnibox);
        }
        sleep(s);
    }

    //omnibox tap
    public void omniboxTap() throws UiObjectNotFoundException {
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_sentry_bar_fake_text"));
        if (omnibox.exists()) {
            omnibox.click();
        }
        else{
            logFail(omnibox);
        }
    }

    //omnibox in tab tap
    public void omniboxInTabTap() throws UiObjectNotFoundException {
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_omnibar_address_title_text"));
        if (omnibox.exists()) {
            omnibox.click();
        }
        else{
            logFail(omnibox);
        }
    }

    //omnibox text input
    public void omniboxInput(String url) throws UiObjectNotFoundException {
        UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_sentry_bar_input_edittext"));
        if (omniboxTextField.exists()) {
            omniboxTextField.setText(url);
        }
        else{
            logFail(omniboxTextField);
        }
    }

    //tap on "+" button
    public void menuNewTabButtonTap() throws UiObjectNotFoundException {
        UiObject menuNewTab = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_menu_item_new_tab"));
        if (menuNewTab.exists()) {
            menuNewTab.click();
        }
        else{
            logFail(menuNewTab);
        }
    }

    public void openUrlInCurrentTab(String url) throws UiObjectNotFoundException {
        omniboxInTabTap();
        omniboxInput(url);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    //open url in new tab
    public void openUrlInNewTab(String url) throws UiObjectNotFoundException {
        menuButtonTap();
        menuNewTabButtonTap();
        omniboxTap();
        omniboxInput(url);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    //open url from Sentry
    public void openUrlFromSentry(String url) throws UiObjectNotFoundException {
        omniboxTap();
        omniboxInput(url);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }


    public void scrollDown(int count) throws Exception {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        waitForWebView();
            for (int x = 0; x < count; x++) {
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight / 7), screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), 20);
        }
    }

    public void scrollUp(int count) throws Exception {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        waitForWebView();
            for (int x = 0; x < count; x++) {
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), screenWidth / 2, screenHeight - (screenHeight / 7), 20);
        }
    }

    public void vkLogin() throws Exception{
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
        waitForWebView();
    }

    public void vkMusicStart() throws Exception{
        UiObject rootLayout = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_root_layout"));
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        int offset = rootLayout.getBounds().top - webView.getBounds().top;
        UiObject firstSong = webView.getChild(new UiSelector().description("Scorpions – Humanity"));
        UiDevice.getInstance().click(firstSong.getBounds().centerX(), firstSong.getBounds().centerY()+offset);
        waitForWebView();
    }

    public void youtubeVideoPlayFullscreen() throws Exception{
        UiObject rootLayout = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_root_layout"));
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        int offset = rootLayout.getBounds().top - webView.getBounds().top;
        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));
        UiDevice.getInstance().click(playButton.getBounds().centerX(), playButton.getBounds().centerY()+offset);
        sleep(3000);
        UiObject videoContainer = new UiObject(new UiSelector().resourceId("koya_elem_0_11"));
        UiDevice.getInstance().click((int)((videoContainer.getBounds().right)*(90.0f/100.0f)), (int)((videoContainer.getBounds().bottom)*(90.0f/100.0f))+offset);
        sleep(1000);
        UiDevice.getInstance().setOrientationLeft();
        waitForWebView();
    }

    public void logStart() throws Exception {
        Log.i("power measurement", "start measurement");
        sleep(1000);
    }

    public void logFail(UiObject elementToCheck) {
        Log.i("power measurement", "battery test failed: " + elementToCheck.getSelector() + " not found");
    }

    public void logPass() {
        Log.i("power measurement", "battery test passed");
    }

    public void waitForWebView() {
        long endTime = System.currentTimeMillis()+15000;
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        while (!webView.exists() && System.currentTimeMillis() < endTime){
        }
        if (!webView.exists()) {
            logFail(webView);
        }
    }
}
