import os, csv, sys, time, shutil, subprocess, threading, Queue, datetime


if os.path.isfile("build.xml"):
    os.remove("build.xml")
if os.path.isdir("bin"):
    shutil.rmtree("bin")
os.system("android.bat create uitest-project -n battery-test -t 2 -p C:/appium/battery-test")
os.system("ant build")
os.system("adb push bin/battery-test.jar /data/local/tmp/")
os.system("adb shell uiautomator runtest /data/local/tmp/battery-test.jar -c ru.batterytest.yabro.MusicPlay")
