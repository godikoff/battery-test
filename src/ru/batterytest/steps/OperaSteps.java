package ru.batterytest.steps;

import android.util.Log;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class OperaSteps extends UiAutomatorTestCase {

    //Opera start (time for sleep after start)
    public void browserStart(int s) throws UiObjectNotFoundException {

        UiObject yandexBrowser = new UiObject(new UiSelector().text("Opera"));
        if (yandexBrowser.exists())
            yandexBrowser.click();
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
    }

    //carousel tap
    public void carouselButtonTap() throws UiObjectNotFoundException {
        UiObject carouselButton = new UiObject(new UiSelector().resourceId("com.opera.browser:id/bottom_navigation_bar_tab_count_button"));
        carouselButton.click();
    }

    //tap on "new tab" button in carousel
    public void carouselNewTabButtonTap() throws UiObjectNotFoundException {
        UiObject carouselNewTabButton = new UiObject(new UiSelector().resourceId("com.opera.browser:id/tab_menu_add_tab"));
        carouselNewTabButton.click();
    }

    //open url in new tab
    public void openUrlInNewTab(String url) throws UiObjectNotFoundException {
        carouselButtonTap();
        carouselNewTabButtonTap();
        omniboxTap();
        omniboxInput(url);
        getUiDevice().pressEnter();
    }

    //omnibox tap
    public void omniboxTap() throws UiObjectNotFoundException {
        UiObject omnibox = new UiObject(new UiSelector().resourceId("com.opera.browser:id/url_field"));
        omnibox.click();
    }

    //omnibox text input
    public void omniboxInput(String url) throws UiObjectNotFoundException {
        UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.opera.browser:id/url_field"));
        omniboxTextField.setText(url);
    }

    //open url in current tab
    public void openUrlInCurrentTab(String url) throws UiObjectNotFoundException {
        omniboxTap();
        omniboxInput(url);
        sleep(1000);
        getUiDevice().pressEnter();
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
        for (int x = 0; x < count; x++) {
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight / 7), screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), 20);
        }
    }

    public void scrollUp(int count) throws Exception {
        int screenHeight = UiDevice.getInstance().getDisplayHeight();
        int screenWidth = UiDevice.getInstance().getDisplayWidth();
        for (int x = 0; x < count; x++) {
            UiDevice.getInstance().drag(screenWidth / 2, screenHeight - (screenHeight - (screenHeight / 7)), screenWidth / 2, screenHeight - (screenHeight / 7), 20);
        }
    }

    public void logStart() throws Exception {
        Log.i("power", "start measurement");
        sleep(1000);
    }

}
