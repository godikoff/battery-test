import plotly
import csv
import plotly.graph_objs as go
from datetime import datetime
import re


calculatedTime=5340


currentList = []
timeList = []
downloadList = []
downloadTime = []
logMessageList = []
dotsX = []
dotsY = []
shapes = []

with open("log.txt") as eventlog:
    events = [datetime.strptime(l.split()[1], "%H:%M:%S.%f") for l in eventlog.readlines()]
offsets = [(ev - events[0]).total_seconds()*1000 for ev in events]

with open("log.txt") as eventlog:
    logmessages = [re.search('DownloadTracking: (.*)', l).group(1) for l in eventlog.readlines()]

with open('battery_test.csv') as csvfile:
    testResults = csv.DictReader(csvfile)
    for row in testResults:
        current = row['Main Avg Power (mW)']
        currentList.append(current)

currentList = map(int, currentList)


for i in offsets:
    shape = {
        'type': 'line',
        'x0': i+calculatedTime,
        'y0': 0,
        'x1': i+calculatedTime,
        'y1': max(currentList),
        'line': {
            'color': 'rgb(0, 0, 0)',
            'width': 3,
        }
    }
    shapes.append(shape)
    dotX=i+calculatedTime
    dotsX.append(dotX)
    dotY=0
    dotsY.append(dotY)

with open('battery_test.csv') as csvfile:
    testResults = csv.DictReader(csvfile)
    for row in testResults:
        current = row['Main Avg Power (mW)']
        time = row['Time (ms)']
        time = int(time)
        timeList.append(time)
        if dotsX[4] < time and dotsX[5] > time:
            downloadList.append(current)
            downloadTime.append(time)

downloadList = map(int, downloadList)
averageCurrent = sum(downloadList)/len(downloadList)

trace1 = go.Scatter(
    x=timeList,
    y=currentList,
    name='mA')

trace2 = go.Scatter(
    x=[i for i in downloadTime],
    y=[averageCurrent for i in downloadTime],
    name='Download avg (' +  str(averageCurrent) + ')',
    marker = dict(
        size = 3,
        color = 'rgba(200, 0, 0, 1)'
    )
)

trace3 = go.Scatter(
    x=dotsX,
    y=dotsY,
    mode='markers+text',
    name='Logs',
    text=['Sync', 'PLS', 'PLS', 'PLF', 'DLS', 'DLF'],
    textposition='bottom',
    marker = dict(
        size = 4,
        color = 'rgba(0, 0, 0, 1)'
    )
)

trace4 = go.Scatter(
    x=[i for i in downloadTime],
    y=[averageCurrent for i in downloadTime],
    name='Download avg (' +  str(averageCurrent) + ')',
    marker = dict(
        size = 3,
        color = 'rgba(200, 0, 0, 1)'
    )
)

layout = {
    'shapes': shapes
}


plotly.offline.plot({
    "data": [trace1, trace2, trace3],
    "layout": layout
})