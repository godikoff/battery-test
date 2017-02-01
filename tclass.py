import os
import csv
import time
import shutil
import subprocess
import threading
import Queue
import string
from time import strftime
from openpyxl import Workbook, load_workbook
from openpyxl.styles import Alignment


# Don't forget to:
# 1. Create new <test class> and in case of new testclesses in .jar. And add it to <testList>
# 2. Create new <browser class> in case of new browsers to test. And add it to <broList>


if os.path.isfile("build.xml"):
    os.remove("build.xml")
os.system("android.bat create uitest-project -n battery-test -t 2 -p C:/appium/battery-test")

if os.path.isdir("bin"):
    shutil.rmtree("bin")
os.system("ant build")

#os.system("adb shell pm clear com.yandex.browser")
#os.system("adb shell pm clear com.android.chrome")
os.system("adb push bin/battery-test.jar /data/local/tmp/")
os.system("adb shell uiautomator runtest /data/local/tmp/battery-test.jar -c ru.batterytest.atest")
