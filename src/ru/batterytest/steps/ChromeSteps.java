package ru.batterytest.steps;

import android.util.Log;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class ChromeSteps extends UiAutomatorTestCase {

    //start Chrome from homescreen
    public void browserStart(int s) throws UiObjectNotFoundException {
        UiObject chromeBrowser = new UiObject(new UiSelector().text("Chrome"));
        if (chromeBrowser.exists())
            chromeBrowser.click();
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
    }

    //menu in omni tap
    public void menuTap() throws UiObjectNotFoundException {
        UiObject menuButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/menu_button"));
        menuButton.click();
    }

    //omnibox tap
    public void omniboxTap() throws UiObjectNotFoundException {
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.android.chrome:id/search_box"));
        omnibox.click();
    }

    //omnibox text input
    public void omniboxInput(String url) throws UiObjectNotFoundException {
        UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
        omniboxTextField.setText(url);
    }

    //tap on "new tab" button
    public void menuNewTab() throws UiObjectNotFoundException {
        UiObject menuNewTab = new UiObject(new UiSelector().resourceId("com.android.chrome:id/menu_item_text").description("Новая вкладка"));
        menuNewTab.click();
    }

    //open url in new tab
    public void openUrlInNewTab(String url) throws UiObjectNotFoundException {
        menuTap();
        menuNewTab();
        omniboxTap();
        omniboxInput(url);
        getUiDevice().pressEnter();
    }

    //open url in current tab
    public void openUrlInCurrentTab(String url) throws UiObjectNotFoundException {
        omniboxTap();
        omniboxInput(url);
        getUiDevice().pressEnter();
    }

    //tap play button on Youtube
    public void youtubePlay() throws UiObjectNotFoundException {
        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));
        playButton.click();
    }

    public void youtubeFullscreen() throws UiObjectNotFoundException {
        UiObject videoField = new UiObject(new UiSelector().className("android.view.View").description("видео"));
        videoField.click();
        UiObject fullscreenButton = videoField.getChild(new UiSelector().className("android.widget.Button").description("полноэкранный режим"));
        fullscreenButton.click();
    }

    public void scrollDown(int count) throws Exception {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        for (int x = 0; x < count; x++) {
            UiDevice.getInstance().swipe(screenWidth / 2, screenHeight - (screenHeight / 7), screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), 20);
            sleep(1000);
        }
    }

    public void scrollUp(int count) throws Exception {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        for (int x = 0; x < count; x++) {
            UiDevice.getInstance().swipe(screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), screenWidth / 2, screenHeight - (screenHeight / 7), 20);
            sleep(1000);
        }
    }

    public void logStart() throws Exception {
        Log.i("power", "start measurement");
        sleep(1000);
    }
}
