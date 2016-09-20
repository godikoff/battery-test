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


def getNames(package):
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

    process = subprocess.Popen(["adb", "shell", "dumpsys", "package", package, "|", "grep", "versionName"],
                               stdout=subprocess.PIPE)
    stdout_queue = Queue.Queue()
    stdout_reader = AsynchronousFileReader(process.stdout, stdout_queue)
    stdout_reader.start()
    yaBroVersion = stdout_queue.get().split("=")[1].strip()
    return yaBroVersion

class YandexBrowser:
    package = "com.yandex.browser"
    browserName = "Yandex Browser " + getNames(package)
    testBrowser = "yabro"
    forArgs = "Y"


class Chrome:
    package = "com.android.chrome"
    browserName = "Chrome " + getNames(package)
    testBrowser = "chrome"
    forArgs = "C"


print YandexBrowser.browserName