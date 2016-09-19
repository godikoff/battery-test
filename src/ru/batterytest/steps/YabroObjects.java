package ru.batterytest.steps;

import android.util.Log;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class YabroObjects extends UiAutomatorTestCase{
    public YabroObjects() throws UiObjectNotFoundException {}

    public UiObject menuButton = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_omnibar_address_button_menu"));
    public UiObject omnibox = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_sentry_bar_fake_text"));
    public UiObject tuorialCloseButton = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/activity_tutorial_close_button"));
    public UiObject omniboxInTab = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_omnibar_address_title_text"));
    public UiObject omniboxTextField = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_sentry_bar_input_edittext"));
    public UiObject menuNewTabButton = new UiObject(new UiSelector().resourceId("com.yandex.browser:id/bro_menu_item_new_tab"));
    public UiObject videoContainer = new UiObject(new UiSelector().resourceId("koya_elem_0_11"));
    public UiObject webView = new UiObject(new UiSelector().className("android.webkit.WebView"));
    public UiObject browserHomescreenIcon = new UiObject(new UiSelector().textMatches("Яндекс(.*\\s?)Браузер"));
    public UiObject firstSong = new UiObject(new UiSelector().description("Scorpions – Humanity"));
    public UiObject loginView = new UiObject(new UiSelector().className("android.widget.ListView").index(0));
    public UiObject passwordView = new UiObject(new UiSelector().className("android.widget.ListView").index(1));
    public UiObject playButton = new UiObject(new UiSelector().className("android.widget.Button").description("Смотреть"));
    public UiObject loginTextField = loginView.getChild(new UiSelector().className("android.widget.EditText"));
    public UiObject passwordTextField = passwordView.getChild(new UiSelector().className("android.widget.EditText"));
    public UiObject youtubeSkipAdButton = new UiObject(new UiSelector().description("Skip Ad"));
}
