package ru.batterytest.steps;

import android.os.RemoteException;
import android.util.Log;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class ChromeSteps extends UiAutomatorTestCase {

    public void precondition() throws RemoteException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiDevice.getInstance().pressHome();
        UiDevice.getInstance().setOrientationNatural();
    }

    //start Chrome from homescreen
    public void browserStart(int s) throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject chromeBrowser = new UiObject(new UiSelector().text("Chrome"));
        if (chromeBrowser.exists())
            chromeBrowser.click();
        else{
            logFail(chromeBrowser);
        }
        sleep(4000);
        UiObject reportCheckBox = new UiObject(new UiSelector().resourceId("com.android.chrome:id/send_report_checkbox"));
        UiObject acceptButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/terms_accept"));
        if (reportCheckBox.exists()){
            reportCheckBox.click();
            acceptButton.click();
        }
        sleep(2000);
        UiObject skipLogin = new UiObject(new UiSelector().resourceId("com.android.chrome:id/negative_button"));
        if (skipLogin.exists()){
            skipLogin.click();
        }
        sleep(s-2000);
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.android.chrome:id/search_box"));
        if (!omnibox.exists()){
            logFail(omnibox);
        }
    }

    //menu in omni tap
    public void menuButtonTap() throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject menuButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/menu_button"));
        if (menuButton.exists()) {
            menuButton.click();
        }
        else{
            logFail(menuButton);
        }
    }

    //omnibox tap
    public void omniboxTap() throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.android.chrome:id/search_box"));
        if (omnibox.exists()) {
            omnibox.click();
        }
        else{
            logFail(omnibox);
        }
    }

    //omnibox in tab tap
    public void omniboxInTabTap() throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
        if (omnibox.exists()) {
            omnibox.click();
        }
        else{
            logFail(omnibox);
        }
    }

    //omnibox text input
    public void omniboxInput(String url) throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
        if (omniboxTextField.exists()) {
            omniboxTextField.setText(url);
        }
        else{
            logFail(omniboxTextField);
        }
    }

    //tap on "new tab" button
    public void menuNewTabButtonTap() throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject menuNewTab = new UiObject(new UiSelector().resourceId("com.android.chrome:id/menu_item_text").description("Новая вкладка"));
        if (menuNewTab.exists()) {
            menuNewTab.click();
        }
        else{
            logFail(menuNewTab);
        }
    }

    //open url in new tab
    public void openUrlInNewTab(String url) throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        menuButtonTap();
        menuNewTabButtonTap();
        omniboxTap();
        omniboxInput(url);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    //open url first tab
    public void operUrlFirstTab(String url) throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        omniboxTap();
        omniboxInput(url);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    //open url in current tab on tab
    public void openUrlInCurrentTab(String url) throws UiObjectNotFoundException {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        omniboxInTabTap();
        omniboxInput(url);
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    public void scrollDown(int count) throws Exception {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        waitForWebView();
        for (int x = 0; x < count; x++) {
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight / 7), screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), 20);
        }
    }

    public void scrollUp(int count) throws Exception {
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        waitForWebView();
        for (int x = 0; x < count; x++) {
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), screenWidth / 2, screenHeight - (screenHeight / 7), 20);
        }
    }

    public void vkLogin() throws Exception{
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject loginView = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
        UiObject passwordView = new UiObject(new UiSelector().className("android.widget.ListView").index(1));
        loginView.getChild(new UiSelector().className("android.widget.EditText")).setText("yabrotest@gmail.com");
        passwordView.getChild(new UiSelector().className("android.widget.EditText")).setText("yabrotest123");
        UiDevice.getInstance().pressEnter();
        waitForWebView();
    }

    public void vkMusicStart() throws Exception{
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        webView.getChild(new UiSelector().description("Scorpions – Humanity")).click();
        waitForWebView();
    }

    public void youtubeVideoPlayFullscreen() throws Exception{
        Configurator.getInstance().setWaitForSelectorTimeout(10000);
        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));
        playButton.click();
        sleep(2000);
        UiObject videoContainer = new UiObject(new UiSelector().resourceId("koya_elem_0_11"));
        UiDevice.getInstance().click((int)((videoContainer.getBounds().right)*(90.0f/100.0f)), (int)((videoContainer.getBounds().bottom)*(90.0f/100.0f)));
        sleep(2000);
        UiDevice.getInstance().setOrientationLeft();
        UiObject notificationCloseButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/infobar_close_button"));
        if (notificationCloseButton.exists())
            notificationCloseButton.click();
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
        long endTime = System.currentTimeMillis()+30000;
        UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
        while (!webView.exists() && System.currentTimeMillis() < endTime){
        }
        if (!webView.exists()) {
            logFail(webView);
        }
    }
}
