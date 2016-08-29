import os
import csv
import time
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


def RunTests(broList, browser, testList, test):
    for browserToRun in browser:

        for testToRun in test:
            testNumber = 1
            allTestsCurrentAvg = []

            for browsersToClear in broList:
                print browsersToClear.browserName + " clearing..."
                os.system("adb shell pm clear " + browsersToClear.package)
                time.sleep(0)

            if hasattr(testToRun, 'notFirstStart'):
                print "First start..."
                print "...please wait..."
                time.sleep(0)

            if hasattr(testToRun, 'enableRotation'):
                print "Screen rotation enabled!"

            retryCount = 0
            while testNumber < testToRun.runs + 1:

                print browserToRun.browserName + " stopped!"

                if hasattr(testToRun, 'clearBrowser'):
                    print browserToRun.browserName + " clearing..."

                time.sleep(0)
                findFailInLog = "passed"
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
                                singleResult = open(txtFilename, 'a')
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
                        with open(txtFilename, 'a') as failResult:
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

                time.sleep(0)

            if testToRun.runs > 9 and testNumber > 5:
                allTestsCurrentAvg.remove(max(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(max(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(min(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(min(allTestsCurrentAvg))

            if hasattr(testToRun, 'enableRotation'):
                print "Screen rotation disabled!"

            try:
                result = open(txtFilename, 'a')
                try:
                    result.write(strftime(
                        "%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " Current Avg: " + str(
                        sum(allTestsCurrentAvg) / len(allTestsCurrentAvg)) + "\n"),
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
            time.sleep(0)
        print "all test finished"
    print "all bro finished"


bro = []
test = []


class YandexBrowser:
    def __init__(self):
        pass

    package = "com.yandex.browser"
    browserName = "Yandex Browser " + getBroVersion(package)
    testBrowser = "yabro"


class Chrome:
    def __init__(self):
        pass

    package = "com.android.chrome"
    browserName = "Chrome " + getBroVersion(package)
    testBrowser = "chrome"


class Opera:
    def __init__(self):
        pass

    package = "com.opera.browser"
    browserName = "Opera " + getBroVersion(package)
    testBrowser = "opera"


class ColdStart:
    def __init__(self):
        pass

    testClass = "ColdStart"
    measurementDuration = 30
    runs = 2
    notFirstStart = ""


class Foreground:
    def __init__(self):
        pass

    testClass = "Foreground"
    measurementDuration = 500
    runs = 1


class Background:
    def __init__(self):
        pass

    testClass = "Background"
    measurementDuration = 500
    runs = 1


class UlrOpen:
    def __init__(self):
        pass

    testClass = "UrlOpen"
    measurementDuration = 30
    runs = 2
    clearBrowser = ""


class TenSitesForeground:
    def __init__(self):
        pass

    testClass = "TenSitesForeground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""


class TenSitesBackground:
    def __init__(self):
        pass

    testClass = "TenSitesBackground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""


class VideoPlay:
    def __init__(self):
        pass

    testClass = "VideoPlay"
    measurementDuration = 500
    runs = 1
    enableRotation = ""


class Scroll:
    def __init__(self):
        pass

    testClass = "Scroll"
    measurementDuration = 550
    runs = 1
    clearBrowser = ""


class MusicPlay:
    def __init__(self):
        pass

    testClass = "MusicPlay"
    measurementDuration = 550
    runs = 1
    clearBrowser = ""


class HundredSitesForeground:
    def __init__(self):
        pass

    testClass = "HundredSitesForeground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""


homeDir =  os.getcwd()
xlsxFilename = homeDir + "/" + YandexBrowser.browserName + ".xlsx"
txtFilename = homeDir + "/" + YandexBrowser.browserName + ".txt"
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

RunTests(broList, bro, testList, test)
