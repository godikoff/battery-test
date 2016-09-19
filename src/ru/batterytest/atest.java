package ru.batterytest;


import android.os.Environment;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import ru.batterytest.steps.BrowserSteps;
import ru.batterytest.steps.ChromeObjects;
import ru.batterytest.steps.YabroObjects;

import java.io.File;

public class atest extends UiAutomatorTestCase{

    public void test() throws Exception {
        final String dumpXml = "dump.xml";
        final File dump = new File(new File(Environment.getDataDirectory(),
                "local/tmp"), dumpXml);
        dump.mkdirs();
        if (dump.exists()) {
            dump.delete();
        }
        UiDevice.getInstance().dumpWindowHierarchy(dumpXml);
    }
}
