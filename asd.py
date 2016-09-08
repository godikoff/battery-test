import os
import sys
import csv
import time
import subprocess
import threading
import Queue
import string
from time import strftime
from openpyxl import Workbook, load_workbook
from openpyxl.styles import Alignment
from shutil import copyfile


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

                    testDir = allTestsDir + '/' + browserToRun.browserName + '/' + testToRun.testClass
                    if not os.path.isdir(testDir):
                        os.makedirs(testDir)
                    copyfile('battery_test.csv', allTestsDir + "/" + browserToRun.browserName + "/" + testToRun.testClass + "/" + testToRun.testClass + "_" + str(testNumber) + "_data.csv")
                    os.system('adb logcat -d > ' + '"' + allTestsDir + '/' + browserToRun.browserName + '/' + testToRun.testClass + '/' + testToRun.testClass + '_' + str(testNumber) + '_log.txt"')

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
    forArgs = "Y"


class Chrome:
    def __init__(self):
        pass

    package = "com.android.chrome"
    browserName = "Chrome " + getBroVersion(package)
    testBrowser = "chrome"
    forArgs = "B"


class Opera:
    def __init__(self):
        pass

    package = "com.opera.browser"
    browserName = "Opera " + getBroVersion(package)
    testBrowser = "opera"
    forArgs = "O"


class ColdStart:
    def __init__(self):
        pass

    testClass = "ColdStart"
    measurementDuration = 30
    runs = 2
    notFirstStart = ""
    forArgs = "Cs"


class Foreground:
    def __init__(self):
        pass

    testClass = "Foreground"
    measurementDuration = 500
    runs = 1
    forArgs = "Fg"


class Background:
    def __init__(self):
        pass

    testClass = "Background"
    measurementDuration = 500
    runs = 1
    forArgs = "Bg"


class UlrOpen:
    def __init__(self):
        pass

    testClass = "UrlOpen"
    measurementDuration = 30
    runs = 2
    clearBrowser = ""
    forArgs = "Uo"


class TenSitesForeground:
    def __init__(self):
        pass

    testClass = "TenSitesForeground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""
    forArgs = "Tsf"


class TenSitesBackground:
    def __init__(self):
        pass

    testClass = "TenSitesBackground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""
    forArgs = "Tsb"


class VideoPlay:
    def __init__(self):
        pass

    testClass = "VideoPlay"
    measurementDuration = 500
    runs = 1
    enableRotation = ""
    forArgs = "Vp"


class Scroll:
    def __init__(self):
        pass

    testClass = "Scroll"
    measurementDuration = 550
    runs = 1
    clearBrowser = ""
    forArgs = "Sc"


class MusicPlay:
    def __init__(self):
        pass

    testClass = "MusicPlay"
    measurementDuration = 550
    runs = 1
    clearBrowser = ""
    forArgs = "Mp"


class HundredSitesForeground:
    def __init__(self):
        pass

    testClass = "HundredSitesForeground"
    measurementDuration = 500
    runs = 1
    clearBrowser = ""
    forArgs = "Hsf"

broList = [YandexBrowser, Chrome, Opera]
testList = [ColdStart, Foreground, Background, UlrOpen, TenSitesForeground, TenSitesBackground, VideoPlay, Scroll,
            MusicPlay, HundredSitesForeground]

if (len(sys.argv)) == 3:
    for i in broList:
        if i.forArgs in sys.argv[1]:
            bro.append(i)
    for i in testList:
        if i.forArgs in sys.argv[2]:
            test.append(i)
else:
    print "Usage:\n  asd.py <browsers> <tests>\nFor example:\n  asd.py YCO CsFgTsb\n"
    print "Browser list:"
    for browser in broList:
        print browser.forArgs + " - " + browser.browserName
    print "\nTest list:"
    for n in testList:
        print n.forArgs + " - " + n.testClass
    sys.exit()

allTestsDir = strftime("%y.%m.%d %H-%M-%S")
os.mkdir(allTestsDir)
homeDir =  os.getcwd()
xlsxFilename = homeDir + "/" + allTestsDir + "/" + "result_table.xlsx"
txtFilename = homeDir + "/" + allTestsDir + "/" + YandexBrowser.browserName + ".txt"

if not os.path.isfile(xlsxFilename):
    workbookTmp = Workbook()
    workbookTmp.save(xlsxFilename)
workbook = load_workbook(xlsxFilename)
worksheet = workbook.active
alignment = Alignment(horizontal='right')

bNumber = 1
for browserToChoose in broList:
    worksheet["A" + str(broList.index(browserToChoose) + 2)] = browserToChoose.browserName
    worksheet.column_dimensions["A"].width = max(len(x.browserName) for x in broList)
    worksheet["A" + str(broList.index(browserToChoose) + 2)].alignment = alignment
    bNumber = bNumber + 1
workbook.save(xlsxFilename)

columnName = dict()

tNumber = 1
for testToChoose in testList:
    cellNumber = str(list(string.ascii_uppercase)[tNumber]) + "1"
    worksheet[cellNumber] = testToChoose.testClass
    worksheet[cellNumber].alignment = alignment
    worksheet.column_dimensions[str(list(string.ascii_uppercase)[tNumber])].width = len(testToChoose.testClass) + 2
    tNumber = tNumber + 1
workbook.save(xlsxFilename)

RunTests(broList, bro, testList, test)
