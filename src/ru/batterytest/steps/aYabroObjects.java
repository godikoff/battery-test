package ru.batterytest.steps;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class aYabroObjects extends UiAutomatorTestCase {
    public UiObject findByClassNameWithIndex(String element, int index) {
        UiObject elementObject = new UiObject(new UiSelector().className(element).index(index));
        elementObject.waitForExists(10000);
        return elementObject;
    }

    public UiObject findByClassNameWithDescription(String element, String description) {
        UiObject elementObject = new UiObject(new UiSelector().className(element).description(description));
        elementObject.waitForExists(10000);
        return elementObject;
    }

    public UiObject getChild(UiObject object, String selector) throws UiObjectNotFoundException {
        object.getChild(new UiSelector().className(selector));
        return object;
    }

    public UiObject findElement(String locator, int timeToWait) {
        System.out.println("Try to find element: " + locator);
        UiObject element = null;
        long time = System.currentTimeMillis();
        long endTime = time + timeToWait * 1000;
        while (System.currentTimeMillis() < endTime) {
            element = new UiObject(new UiSelector().resourceId(locator));
            if (element.exists())
                return element;
            element = new UiObject(new UiSelector().className(locator));
            if (element.exists())
                return element;
            element = new UiObject(new UiSelector().text(locator));
            if (element.exists()) {
                System.out.println("Got");
                return element;
            }
            element = new UiObject(new UiSelector().description(locator));
            if (element.exists())
                return element;
            sleep(1000);
        }
        System.out.println("Can't find");
        return element;
    }

    public UiObject menuButton() {
        UiObject menuButton = findElement("com.yandex.browser:id/bro_omnibar_address_button_menu", 10);
        return menuButton;
    }
    UiObject tuorialCloseButton() {
        UiObject tuorialCloseButton = findElement("com.yandex.browser:id/activity_tutorial_close_button", 10);
        return tuorialCloseButton;
    }
    UiObject omnibox() {
        UiObject omnibox = findElement("com.yandex.browser:id/bro_sentry_bar_fake_text", 10);
        return omnibox;
    }
    UiObject omniboxInTab() {
        UiObject omniboxInTab = findElement("com.yandex.browser:id/bro_omnibar_address_title_text", 10);
        return omniboxInTab;
    }
    UiObject omniboxTextField() {
        UiObject omniboxTextField = findElement("com.yandex.browser:id/bro_sentry_bar_input_edittext", 10);
        return omniboxTextField;
    }
    UiObject menuNewTabButton() {
        UiObject menuNewTabButton = findElement("com.yandex.browser:id/bro_menu_item_new_tab", 10);
        return menuNewTabButton;
    }
    UiObject rootLayout() {
        UiObject rootLayout = findElement("com.yandex.browser:id/bro_root_layout", 10);
        return rootLayout;
    }
    UiObject videoContainer() {
        UiObject videoContainer = findElement("koya_elem_0_11", 10);
        return videoContainer;
    }
    UiObject webView() {
        UiObject webView = findElement("android.webkit.WebView", 30);
        return webView;
    }
    UiObject yandexBrowserHomescreenIcon() {
        UiObject yandexBrowserHomescreenIcon = findElement("Яндекс\n" + "Браузер", 10);
        return yandexBrowserHomescreenIcon;
    }
    UiObject firstSong() {
        UiObject firstSong = findElement("Scorpions – Humanity", 10);
        return firstSong;
    }
    UiObject loginView() {
        UiObject loginView = findByClassNameWithIndex("android.widget.ListView", 0);
        return loginView;
    }
    UiObject passwordView() {
        UiObject passwordView = findByClassNameWithIndex("android.widget.ListView", 1);
        return passwordView;
    }
    UiObject playButton() {
        UiObject playButton = findByClassNameWithDescription("android.widget.Button", "Смотреть");
        return playButton;
    }
    UiObject loginTextField() throws UiObjectNotFoundException {
        UiObject loginTextField = getChild(loginView(), "android.widget.EditText");
        return loginTextField;
    }
    UiObject passwordTextField() throws UiObjectNotFoundException {
        UiObject passwordTextField = getChild(passwordView(), "android.widget.EditText");
        return passwordTextField;
    }
}
