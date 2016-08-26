import os, csv, sys, time, shutil, subprocess, threading, Queue, datetime

os.system("adb shell pm clear com.yandex.browser")
os.system("adb shell pm clear com.android.chrome")
os.system("adb shell pm clear com.opera.browser")
time.sleep(5)

os.chdir("C:/Program Files (x86)/Monsoon Solutions Inc/Power Monitor")
os.system("PowerToolCmd.exe -trigger=ETY100D500A -vout=4.2 -USB=auto -keeppower -savefile=battery_test.pt4 -noexitwait")

with open('battery_test.csv') as csvfile:
    testResults = csv.DictReader(csvfile)
    currentList = []

    for row in testResults:
        current = row['Main Avg Power (mW)']
        currentList.append(current)

    currentList = map(int, currentList)
    print "\nControl: " + str(sum(currentList)/len(currentList)) + "\n"
    singleResult = open('battery_test_result.txt', 'a')
    singleResult.write("Control: " + str(sum(currentList)/len(currentList)) + "\n")
    singleResult.close()
