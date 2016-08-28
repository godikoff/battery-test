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

voltage = "4.2"


def RunMonitor(measurementDuration):
    os.chdir("C:/Program Files (x86)/Monsoon Solutions Inc/Power Monitor")
    print "start measurement at " + strftime("%m-%d %H:%M:%S")
    os.system(
        "PowerToolCmd.exe -trigger=ETY100D" + measurementDuration + "A -vout=" + voltage + " -USB=auto -keeppower -savefile=battery_test.pt4 -noexitwait")
    print "stop measurement at " + strftime("%m-%d %H:%M:%S")


def getBroVersion(packageName):
    class AsynchronousFileReader(threading.Thread):
        def __init__(self, fd, queue):
            assert isinstance(queue, Queue.Queue)
            assert callable(fd.readline)
            threading.Thread.__init__(self)
            self._fd = fd
            self._queue = queue

        def run(self):
            for line in iter(self._fd.readline, ''):
                self._queue.put(line)

        def eof(self):
            return not self.is_alive() and self._queue.empty()

        def stop(self):
            self._stop.set()

    process = subprocess.Popen(["adb", "shell", "dumpsys", "package", packageName, "|", "grep", "versionName"],
                               stdout=subprocess.PIPE)
    stdout_queue = Queue.Queue()
    stdout_reader = AsynchronousFileReader(process.stdout, stdout_queue)
    stdout_reader.start()
    yaBroVersion = stdout_queue.get().split("=")[1].strip()
    return yaBroVersion


def LogReader(measurementDuration):
    class AsynchronousFileReader(threading.Thread):

        def __init__(self, fd, queue):
            assert isinstance(queue, Queue.Queue)
            assert callable(fd.readline)
            threading.Thread.__init__(self)
            self._fd = fd
            self._queue = queue

        def run(self):
            for line in iter(self._fd.readline, ''):
                self._queue.put(line)

        def eof(self):
            return not self.is_alive() and self._queue.empty()

        def stop(self):
            self._stop.set()

    process = subprocess.Popen(["adb", "logcat", "-v", "time"], stdout=subprocess.PIPE)

    stdout_queue = Queue.Queue()
    stdout_reader = AsynchronousFileReader(process.stdout, stdout_queue)
    stdout_reader.start()

    while not stdout_reader.eof():
        while not stdout_queue.empty():
            line = stdout_queue.get()
            if "start measurement" in line:
                RunMonitor(measurementDuration)


def LogFailFinder():
    class AsynchronousFileReader(threading.Thread):

        def __init__(self, fd, queue):
            assert isinstance(queue, Queue.Queue)
            assert callable(fd.readline)
            threading.Thread.__init__(self)
            self._fd = fd
            self._queue = queue

        def run(self):
            for line in iter(self._fd.readline, ''):
                self._queue.put(line)

        def eof(self):
            return not self.is_alive() and self._queue.empty()

        def stop(self):
            self._stop.set()

    process = subprocess.Popen(["adb", "logcat", "-v", "time", "-d"], stdout=subprocess.PIPE)

    stdout_queue = Queue.Queue()
    stdout_reader = AsynchronousFileReader(process.stdout, stdout_queue)
    stdout_reader.start()

    while not stdout_reader.eof():
        while not stdout_queue.empty():
            line = stdout_queue.get()
            if "battery test failed" in line:
                return line
            elif "battery test passed" in line:
                return "passed"


def RunTests(broList, browser, testList, test):
    for browserToRun in browser:

        for testToRun in test:
            testNumber = 1
            allTestsCurrentAvg = []

            for browsersToClear in broList:
                print browsersToClear.browserName + " clearing..."
                os.system("adb shell pm clear " + browsersToClear.package)
                time.sleep(1)

            if hasattr(testToRun, 'notFirstStart'):
                print "First start..."
                os.system(
                    "adb shell uiautomator runtest /data/local/tmp/battery-test.jar -c ru.batterytest." + browserToRun.testBrowser + ".ColdStart")
                print "...please wait..."
                time.sleep(80)

            if hasattr(testToRun, 'enableRotation'):
                os.system(
                    "adb shell content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:1")
                print "Screen rotation enabled!"

            retryCount = 0
            while testNumber < testToRun.runs + 1:

                os.system("adb shell am force-stop " + browserToRun.package)
                print browserToRun.browserName + " stopped!"

                if hasattr(testToRun, 'clearBrowser'):
                    print browserToRun.browserName + " clearing..."
                    os.system("adb shell pm clear " + browserToRun.package)

                # os.system("adb kill-server")
                os.system("adb devices")
                os.system("adb logcat -c")
                os.system(
                    "start adb shell uiautomator runtest /data/local/tmp/battery-test.jar -c ru.batterytest." + browserToRun.testBrowser + "." + testToRun.testClass + " --nohup")
                LogReader(str(testToRun.measurementDuration))
                time.sleep(3)
                findFailInLog = LogFailFinder()
                if findFailInLog == "passed":
                    try:
                        with open('battery_test.csv') as csvfile:
                            testResults = csv.DictReader(csvfile)
                            currentList = []

                            for row in testResults:
                                current = row['Main Avg Power (mW)']
                                currentList.append(current)

                            currentList = map(int, currentList)
                            try:
                                print "\n" + strftime(
                                    "%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " " + str(
                                    testNumber) + ": " + str(sum(currentList) / len(currentList)) + "\n"
                            except:
                                print "single result printing error"

                            try:
                                singleResult = open('battery_test_result.txt', 'a')
                                singleResult.write(strftime(
                                    "%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " " + str(
                                    testNumber) + ": " + str(sum(currentList) / len(currentList)) + "\n")
                                singleResult.close()
                            except:
                                print "single result writing in file error"
                    except:
                        print "battery_test.csv reading error\n"

                    testNumber = testNumber + 1
                    allTestsCurrentAvg.append(sum(currentList) / len(currentList))
                else:
                    retryCount = retryCount + 1
                    try:
                        with open('battery_test_result.txt', 'a') as failResult:
                            try:
                                print strftime(
                                    "%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " " + str(
                                    testNumber) + ": failed! Count = " + str(
                                    retryCount) + "\n" + findFailInLog.strip() + "\n"
                            except:
                                print "fail result printing error\n"
                            try:
                                failResult.write(strftime(
                                    "%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " " + str(
                                    testNumber) + ": failed! Count = " + str(
                                    retryCount) + "\n" + findFailInLog.strip() + "\n")
                                failResult.close()
                            except:
                                print "fail result writing in file error\n"
                        if retryCount == 2:
                            testNumber = testNumber + 1
                            retryCount = 0
                            continue
                    except:
                        print "battery_test_result.txt reading error\n"

                time.sleep(5)

            if testToRun.runs > 9 and testNumber > 5:
                allTestsCurrentAvg.remove(max(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(max(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(min(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(min(allTestsCurrentAvg))

            if hasattr(testToRun, 'enableRotation'):
                os.system(
                    "adb shell content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0")
                print "Screen rotation disabled!"

            try:
                result = open('battery_test_result.txt', 'a')
                try:
                    result.write(strftime(
                        "%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " Current Avg: " + str(
                        sum(allTestsCurrentAvg) / len(allTestsCurrentAvg)) + "\n")
                    worksheet[str(list(string.ascii_uppercase)[testList.index(testToRun) + 1]) + str(
                        broList.index(browserToRun) + 2)] = sum(allTestsCurrentAvg) / len(allTestsCurrentAvg)
                    workbook.save(xlsxFilename)
                except:
                    print "full test result writing error\n"
                try:
                    print strftime(
                        "%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " Current Avg: " + str(
                        sum(allTestsCurrentAvg) / len(allTestsCurrentAvg))
                except:
                    print "full test result printing error\n"
                result.close()
            except:
                print "battery_test_result.txt reading error\n"
            time.sleep(5)


bro = []
test = []


class YandexBrowser:
    package = "com.yandex.browser"
    browserName = "Yandex Browser " + getBroVersion(package)
    testBrowser = "yabro"


class Chrome:
    package = "com.android.chrome"
    browserName = "Chrome " + getBroVersion(package)
    testBrowser = "chrome"


class Opera:
    package = "com.opera.browser"
    browserName = "Opera " + getBroVersion(package)
    testBrowser = "opera"


class ColdStart:
    testClass = "ColdStart"
    measurementDuration = 30
    runs = 10
    notFirstStart = ""


class Foreground:
    testClass = "Foreground"
    measurementDuration = 500
    runs = 1


class Background:
    testClass = "Background"
    measurementDuration = 500
    runs = 1


class UlrOpen:
    testClass = "UrlOpen"
    measurementDuration = 30
    runs = 10
    clearBrowser = ""


class TenSitesForeground:
    testClass = "TenSitesForeground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""


class TenSitesBackground:
    testClass = "TenSitesBackground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""


class VideoPlay:
    testClass = "VideoPlay"
    measurementDuration = 500
    runs = 1
    enableRotation = ""


class Scroll:
    testClass = "Scroll"
    measurementDuration = 550
    runs = 1
    clearBrowser = ""


class MusicPlay:
    testClass = "MusicPlay"
    measurementDuration = 550
    runs = 1
    clearBrowser = ""


class HundredSitesForeground:
    testClass = "HundredSitesForeground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""


xlsxFilename = YandexBrowser.browserName + ".xlsx"
if not os.path.isfile(xlsxFilename):
    workbookTmp = Workbook()
    workbookTmp.save(xlsxFilename)
workbook = load_workbook(xlsxFilename)
worksheet = workbook.active
alignment = Alignment(horizontal='right')

broList = [YandexBrowser, Chrome, Opera]
testList = [ColdStart, Foreground, Background, UlrOpen, TenSitesForeground, TenSitesBackground, VideoPlay, Scroll,
            MusicPlay, HundredSitesForeground]

bNumber = 1
for browserToChoose in broList:
    worksheet["A" + str(broList.index(browserToChoose) + 2)] = browserToChoose.browserName
    worksheet.column_dimensions["A"].width = max(len(x.browserName) for x in broList)
    worksheet["A" + str(broList.index(browserToChoose) + 2)].alignment = alignment
    print str(bNumber) + ". " + browserToChoose.browserName
    bNumber = bNumber + 1
browserType = input("Choose browser (0 for all): ")
workbook.save(xlsxFilename)

print ""
columnName = dict()

tNumber = 1
for testToChoose in testList:
    cellNumber = str(list(string.ascii_uppercase)[tNumber]) + "1"
    worksheet[cellNumber] = testToChoose.testClass
    worksheet[cellNumber].alignment = alignment
    worksheet.column_dimensions[str(list(string.ascii_uppercase)[tNumber])].width = len(testToChoose.testClass) + 2
    print str(tNumber) + ". " + testToChoose.testClass
    tNumber = tNumber + 1
testType = input("Choose test (0 for all): ")
workbook.save(xlsxFilename)

if browserType == 0:
    bro = broList
else:
    bro.append(broList[browserType - 1])

if testType == 0:
    test = testList
else:
    test.append(testList[testType - 1])

# Uncomment block below to rebuild .jar
if os.path.isfile("build.xml"):
    os.remove("build.xml")
if os.path.isdir("bin"):
    shutil.rmtree("bin")
os.system("android.bat create uitest-project -n battery-test -t 2 -p C:/appium/battery-test")
os.system("ant build")

os.system("adb push bin/battery-test.jar /data/local/tmp/")

RunTests(broList, bro, testList, test)
