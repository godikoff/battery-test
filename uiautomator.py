import os, csv, sys, time, shutil, subprocess, threading, Queue, datetime

#Don't forget to:
#1. Create new <test class> and in case of new testclesses in .jar. And add it to <testList>
#2. Create new <browser class> in case of new browsers to test. And add it to <broList>

voltage = "4.2"

bro = []
test = []

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

class OpenTenSites:
    testClass = "OpenTenSites"
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

broList = [YandexBrowser, Chrome, Opera]
testList = [ColdStart, Foreground, Background, UlrOpen, OpenTenSites, TenSitesBackground, VideoPlay, Scroll]


bNumber = 1
for browserToChoose in broList:
    print str(bNumber) + ". " + browserToChoose.browserName
    bNumber = bNumber + 1
browserType = input("Choose browser (" + str(bNumber) + " for all): ")

print ""

tNumber = 1
for testToChoose in testList:
    print str(tNumber) + ". " + testToChoose.testClass
    tNumber = tNumber + 1
testType = input("Choose test (" + str(tNumber) + " for all): ")

if browserType == len(broList)+1:
    bro = broList
else:
    bro.append(broList[browserType-1])

if testType == len(testList)+1:
    test = testList
else:
    test.append(testList[testType-1])

#Uncomment block below to rebuild .jar
if os.path.isfile("build.xml"):
    os.remove("build.xml")
if os.path.isdir("bin"):
    shutil.rmtree("bin")
os.system("android.bat create uitest-project -n battery-test -t 2 -p C:/appium/battery-test")
os.system("ant build")

os.system("adb push bin/battery-test.jar /data/local/tmp/")


def RunMonitor(measurementDuration):
    os.chdir("C:/Program Files (x86)/Monsoon Solutions Inc/Power Monitor")
    print "start measurement at " + str(datetime.datetime.now())
    os.system("PowerToolCmd.exe -trigger=ETY100D" + measurementDuration + "A -vout=" + voltage + " -USB=auto -keeppower -savefile=battery_test.pt4 -noexitwait")
    print "stop measurement at " + str(datetime.datetime.now())


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

    process = subprocess.Popen(["adb", "logcat"], stdout=subprocess.PIPE)

    stdout_queue = Queue.Queue()
    stdout_reader = AsynchronousFileReader(process.stdout, stdout_queue)
    stdout_reader.start()

    while not stdout_reader.eof():
        while not stdout_queue.empty():
            line = stdout_queue.get()
            if "start measurement" in line:
                RunMonitor(measurementDuration)


def RunTests(broList, browser, test):
    for browserToRun in browser:

        for testToRun in test:
            testNumber=1
            allTestsCurrentAvg = []

            for browsersToClear in broList:
                print browsersToClear.browserName + " clearing..."
                os.system("adb shell pm clear " + browsersToClear.package)
                time.sleep(1)

            if hasattr(testToRun, 'notFirstStart'):
                print "First start..."
                os.system("adb shell uiautomator runtest /data/local/tmp/battery-test.jar -c ru.yandex.batterytest." + browserToRun.testBrowser + ".ColdStart")
                print "...please wait..."
                time.sleep(80)

            if hasattr(testToRun, 'enableRotation'):
                os.system("adb shell content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:1")
                print "Screen rotation enabled!"

            while testNumber<testToRun.runs+1:

                os.system("adb shell am force-stop " + browserToRun.package)
                print browserToRun.browserName + " stopped!"

                if hasattr(testToRun, 'clearBrowser'):
                    print browserToRun.browserName + " clearing..."
                    os.system("adb shell pm clear " + browserToRun.package)

                os.system("adb kill-server")
                os.system("adb devices")
                os.system("adb logcat -c")
                os.system("start adb shell uiautomator runtest /data/local/tmp/battery-test.jar -c ru.yandex.batterytest." + browserToRun.testBrowser + "." + testToRun.testClass + " --nohup")
                LogReader(str(testToRun.measurementDuration))

                with open('battery_test.csv') as csvfile:
                    testResults = csv.DictReader(csvfile)
                    currentList = []

                    for row in testResults:
                        current = row['Main Avg Power (mW)']
                        currentList.append(current)

                    currentList = map(int, currentList)
                    print "\n" + browserToRun.browserName + " " + testToRun.testClass+ " " + str(testNumber) + ": " + str(sum(currentList)/len(currentList)) + "\n"
                    singleResult = open('battery_test_result.txt', 'a')
                    singleResult.write(browserToRun.browserName + " " + testToRun.testClass+ " " + str(testNumber) + ": " + str(sum(currentList)/len(currentList)) + "\n")
                    singleResult.close()

                testNumber=testNumber+1
                allTestsCurrentAvg.append(sum(currentList)/len(currentList))
                time.sleep(5)

            if hasattr(testToRun, 'enableRotation'):
                os.system("adb shell content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:0")
                print "Screen rotation disabled!"

            result = open('battery_test_result.txt', 'a')
            result.write(browserToRun.browserName + " " + testToRun.testClass+ " Current Avg: " + str(sum(allTestsCurrentAvg)/len(allTestsCurrentAvg)) + "\n")
            print browserToRun.browserName + " " + testToRun.testClass + " Current Avg: " + str(sum(allTestsCurrentAvg)/len(allTestsCurrentAvg))
            time.sleep(5)
            result.close()

RunTests(broList, bro, test)