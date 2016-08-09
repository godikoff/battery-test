package ru.batterytest.steps;

import android.util.Log;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class YaBroSteps extends UiAutomatorTestCase {

    //menu in omni tap
    public void menuTap() throws UiObjectNotFoundException {
        UiObject menuButton = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_omnibar_address_button_menu"));
        menuButton.click();
    }

    //Yabro start (time for sleep after start)
    public void browserStart(int s) throws UiObjectNotFoundException {

        UiObject yandexBrowser = new UiObject(new UiSelector().text("Яндекс\n" + "Браузер"));
        if (yandexBrowser.exists())
            yandexBrowser.click();
        sleep(5000);

        UiObject tuorialCloseButton = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/activity_tutorial_close_button"));
        if (tuorialCloseButton.exists())
            tuorialCloseButton.click();
        else {
        }
        sleep(s);
    }

    //omnibox tap
    public void omniboxTap() throws UiObjectNotFoundException {
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_sentry_bar_fake_text"));
        omnibox.click();
    }

    //omnibox text input
    public void omniboxInput(String url) throws UiObjectNotFoundException {
        UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_sentry_bar_input_edittext"));
        omniboxTextField.setText(url);
    }

    //tap on "+" button
    public void menuNewTab() throws UiObjectNotFoundException {
        UiObject menuNewTab = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_menu_item_new_tab"));
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

    public void youtubePlay() throws UiObjectNotFoundException {
        UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button"));
        playButton.click();
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
