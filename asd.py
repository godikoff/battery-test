import os, csv, sys, time, shutil, subprocess, threading, Queue, datetime
from time import gmtime, strftime
from openpyxl import Workbook
import xlsxwriter

#Don't forget to:
#1. Create new <test class> and in case of new testclesses in .jar. And add it to <testList>
#2. Create new <browser class> in case of new browsers to test. And add it to <broList>

voltage = "4.2"

bro = []
test = []

wb = Workbook()
ws = wb.active
workbook = xlsxwriter.Workbook('Expenses02.xlsx')
worksheet = workbook.add_worksheet()
format = workbook.add_format()
format.set_align('right')

class YandexBrowser:
    package = "com.yandex.browser"
    browserName = "Yandex Browser"
    testBrowser = "yabro"

class Chrome:
    package = "com.android.chrome"
    browserName = "Chrome"
    testBrowser = "chrome"

class Opera:
    package = "com.opera.browser"
    browserName = "Opera"
    testBrowser = "opera"

class ColdStart:
    testClass = "ColdStart"
    measurementDuration = 30
    runs = 2
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
    runs = 2
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


broList = [YandexBrowser, Chrome, Opera]
testList = [ColdStart, Foreground, Background, UlrOpen, TenSitesForeground, TenSitesBackground, VideoPlay, Scroll, MusicPlay, HundredSitesForeground]

bNumber = 1
for browserToChoose in broList:
    print str(bNumber) + ". " + browserToChoose.browserName
    bNumber = bNumber + 1
browserType = input("Choose browser (0 for all): ")

print ""

tNumber = 1
for testToChoose in testList:
    worksheet.write(0, testList.index(testToChoose)+1, testToChoose.testClass, format)
    columnWidth= len(testToChoose.testClass)
    worksheet.set_column(testList.index(testToChoose)+1, testList.index(testToChoose)+1, columnWidth+2)
    print str(tNumber) + ". " + testToChoose.testClass
    tNumber = tNumber + 1
testType = input("Choose test (0 for all): ")

if browserType == 0:
    bro = broList
else:
    bro.append(broList[browserType-1])

if testType == 0:
    test = testList
else:
    test.append(testList[testType-1])

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

    process = subprocess.Popen(["adb", "shell", "dumpsys", "package", packageName, "|", "grep", "versionName"], stdout=subprocess.PIPE)
    stdout_queue = Queue.Queue()
    stdout_reader = AsynchronousFileReader(process.stdout, stdout_queue)
    stdout_reader.start()
    yaBroVersion = stdout_queue.get().split("=")[1].strip()
    return yaBroVersion

def RunTests(broList, browser, testList, test):
    for browserToRun in browser:
        broNameAndVersion = browserToRun.browserName + " " + getBroVersion(browserToRun.package)
        worksheet.set_column(0, 0, 30)
        worksheet.write(broList.index(browserToRun)+1, 0, broNameAndVersion, format)

        for testToRun in test:
            testNumber=1
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
            while testNumber<testToRun.runs+1:

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
                                print "\n" + strftime("%m-%d %H:%M:%S") + " " +  browserToRun.browserName + " " + testToRun.testClass+ " " + str(testNumber) + ": " + str(sum(currentList)/len(currentList)) + "\n"
                            except:
                                print "single result printing error"

                            try:
                                singleResult = open('battery_test_result.txt', 'a')
                                singleResult.write(strftime("%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass+ " " + str(testNumber) + ": " + str(sum(currentList)/len(currentList)) + "\n")
                                singleResult.close()
                            except:
                                print "single result writing in file error"
                    except:
                        print "battery_test.csv reading error\n"

                    testNumber=testNumber+1
                    allTestsCurrentAvg.append(sum(currentList)/len(currentList))
                else:
                    retryCount = retryCount+1
                    try:
                        with open('battery_test_result.txt', 'a') as failResult:
                            try:
                                print strftime("%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass+ " " + str(testNumber) + ": failed! Count = " + str(retryCount) + "\n" + findFailInLog.strip() + "\n"
                            except:
                                print "fail result printing error\n"
                            try:
                                failResult.write(strftime("%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass+ " " + str(testNumber) + ": failed! Count = " + str(retryCount) + "\n" + findFailInLog.strip() + "\n")
                                failResult.close()
                            except:
                                print "fail result writing in file error\n"
                        if retryCount == 2:
                            testNumber=testNumber+1
                            retryCount = 0
                            continue
                    except:
                        print "battery_test_result.txt reading error\n"

                time.sleep(0)

            if (testToRun.runs > 9 and testNumber > 5):
                allTestsCurrentAvg.remove(max(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(max(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(min(allTestsCurrentAvg))
                allTestsCurrentAvg.remove(min(allTestsCurrentAvg))

            if hasattr(testToRun, 'enableRotation'):
                print "Screen rotation disabled!"

            try:
                result = open('battery_test_result.txt', 'a')
                try:
                    result.write(strftime("%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass+ " Current Avg: " + str(sum(allTestsCurrentAvg)/len(allTestsCurrentAvg)) + "\n"),
                    worksheet.write(broList.index(browserToRun)+1, testList.index(testToRun)+1, sum(allTestsCurrentAvg)/len(allTestsCurrentAvg), format)
                except:
                    print "full test result writing error\n"
                try:
                    print strftime("%m-%d %H:%M:%S") + " " + browserToRun.browserName + " " + testToRun.testClass + " Current Avg: " + str(sum(allTestsCurrentAvg)/len(allTestsCurrentAvg))
                except:
                    print "full test result printing error\n"
                result.close()
            except:
                print "battery_test_result.txt reading error\n"
            time.sleep(0)
        print "all test finished"
    print "all bro finished"

RunTests(broList, bro, testList, test)
workbook.close()