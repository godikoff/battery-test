package ru.batterytest.steps;

import android.os.RemoteException;
import android.util.Log;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class OperaSteps extends UiAutomatorTestCase {

    public void precondition() throws RemoteException {
        UiDevice.getInstance().pressHome();
        UiDevice.getInstance().setOrientationNatural();
    }


    //Opera start (time for sleep after start)
    public void browserStart(int s) throws UiObjectNotFoundException {
        UiObject operaBrowser = new UiObject(new UiSelector().text("Opera"));
        if (operaBrowser.exists())
            operaBrowser.click();
        else {
            logFail(operaBrowser);
        }
        sleep(5000);
        UiObject continueButton = new UiObject(new UiSelector().resourceId("com.opera.browser:id/continue_button"));
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.opera.browser:id/omnibar_layout"));
        UiObject guideFinishButton = new UiObject(new UiSelector().resourceId("com.opera.browser:id/guide_finish_button"));
        while (!(omnibox.exists())) {
            if (continueButton.exists())
                continueButton.click();
            while (!(guideFinishButton.exists())) {
                swipeLeft();
            }
            guideFinishButton.click();
        }
        sleep(s);
        if (!omnibox.exists()) {
            logFail(omnibox);
        }
    }

    //carousel tap
    public void carouselButtonTap() throws UiObjectNotFoundException {
        UiObject carouselButton = new UiObject(new UiSelector().resourceId("com.opera.browser:id/bottom_navigation_bar_tab_count_button"));
        if (carouselButton.exists()) {
            carouselButton.click();
        }
        else{
            logFail(carouselButton);
        }
    }

    //tap on "new tab" button in carousel
    public void carouselNewTabButtonTap() throws UiObjectNotFoundException {
        UiObject carouselNewTabButton = new UiObject(new UiSelector().resourceId("com.opera.browser:id/tab_menu_add_tab"));
        if (carouselNewTabButton.exists()) {
            carouselNewTabButton.click();
        }
        else{
            logFail(carouselNewTabButton);
        }
    }

    //open url in new tab
    public void openUrlInNewTab(String url) throws UiObjectNotFoundException {
        carouselButtonTap();
        carouselNewTabButtonTap();
        omniboxTap();
        omniboxInput(url);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    //omnibox tap
    public void omniboxTap() throws UiObjectNotFoundException {
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.opera.browser:id/url_field"));
        if (omnibox.exists()) {
            omnibox.click();
        }
        else{
            logFail(omnibox);
        }
    }

    //omnibox text input
    public void omniboxInput(String url) throws UiObjectNotFoundException {
        UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.opera.browser:id/url_field"));
        if (omniboxTextField.exists()) {
            omniboxTextField.setText(url);
        }
        else{
            logFail(omniboxTextField);
        }
    }

    //open url in current tab
    public void openUrlInCurrentTab(String url) throws UiObjectNotFoundException {
        omniboxTap();
        omniboxInput(url);
        sleep(1000);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }


    public void swipeLeft() {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        for (int x = 0; x < 1; x++) {
            UiDevice.getInstance().swipe((screenWidth - (screenWidth / 7)), screenHeight / 2, screenWidth / 7, screenHeight / 2, 20);
            sleep(1000);
        }
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

    public void dialogButtonNegativeClick() throws Exception {
        UiObject dialogButtonNegative = new UiObject(new UiSelector().resourceId("com.opera.browser:id/opera_dialog_button_negative"));
        if (dialogButtonNegative.exists()) {
            dialogButtonNegative.click();
        }
    }

    public void vkLogin() throws Exception{
        UiObject loginView = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
        UiObject passwordView = new UiObject(new UiSelector().className("android.widget.ListView").index(1));
        loginView.getChild(new UiSelector().className("android.widget.EditText")).setText("yabrotest@gmail.com");
        passwordView.getChild(new UiSelector().className("android.widget.EditText")).setText("yabrotest123");
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    public void vkMusicStart() throws Exception{
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        webView.getChild(new UiSelector().description("Scorpions – Humanity")).click();
        waitForWebView();
    }

    public void youtubeVideoPlayFullscreen() throws Exception{
        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));
        playButton.click();
        sleep(2000);
        UiObject videoContainer = new UiObject(new UiSelector().resourceId("koya_elem_0_11"));
        UiDevice.getInstance().click((int)((videoContainer.getBounds().right) * (90.0f / 100.0f)), (int) ((videoContainer.getBounds().bottom) * (95.0f / 100.0f)));
        sleep(2000);
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
