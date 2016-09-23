package ru.batterytest.steps;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class ChromeObjects extends UiAutomatorTestCase{
    public ChromeObjects() throws UiObjectNotFoundException {}

    public UiObject menuButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/menu_button"));
    public UiObject omnibox = new UiObject(new UiSelector().resourceId("com.android.chrome:id/search_box"));
    public UiObject reportCheckBox = new UiObject(new UiSelector().resourceId("com.android.chrome:id/send_report_checkbox"));
    public UiObject acceptButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/terms_accept"));
    public UiObject skipLogin = new UiObject(new UiSelector().resourceId("com.android.chrome:id/negative_button"));
    public UiObject omniboxInTab = new UiObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
    public UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
    public UiObject menuNewTabButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/menu_item_text").description("Новая вкладка"));
    public UiObject videoContainer = new UiObject(new UiSelector().className("android.view.View").index(1).description("видео видео"));
    public UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
    public UiObject browserHomescreenIcon = new UiObject(new UiSelector().textMatches("Chrome"));
    public UiObject firstSong = new UiObject(new UiSelector().description("Scorpions – Humanity"));
    public UiObject loginView = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
    public UiObject passwordView = new UiObject(new UiSelector().className("android.widget.ListView").index(1));
    public UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));
    public UiObject loginTextField = loginView.getChild(new UiSelector().className("android.widget.EditText"));
    public UiObject passwordTextField = passwordView.getChild(new UiSelector().className("android.widget.EditText"));
    public UiObject notificationCloseButton = new UiObject(new UiSelector().resourceId("com.android.chrome:id/infobar_close_button"));
    public UiObject youtubeSkipAdButton = new UiObject(new UiSelector().description("Skip Ad"));

}
