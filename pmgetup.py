import os
from time import strftime

voltage = "3.7"

os.chdir("C:/Program Files (x86)/Monsoon Solutions Inc/Power Monitor")
os.system("PowerToolcmd.exe -trigger=ETY1000D2A -vout=" + voltage + " -USB=auto -keeppower -savefile=battery_test.pt4 -noexitwait")
