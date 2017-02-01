import re
from datetime import datetime


with open("log.txt") as eventlog:
    events = [datetime.strptime(l.split()[1], "%H:%M:%S.%f") for l in eventlog.readlines()]

with open("log.txt") as eventlog:
    logmessages = [re.search('DownloadTracking: (.*)', l).group(1) for l in eventlog.readlines()]
offsets = [(ev - events[0]).total_seconds()*1000 for ev in events]

print logmessages
print offsets
